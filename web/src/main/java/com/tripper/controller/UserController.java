package com.tripper.controller;

import com.tripper.config.JwtTokenUtil;
import com.tripper.domain.user.Role;
import com.tripper.domain.user.User;
import com.tripper.dto.request.user.*;
import com.tripper.dto.response.user.GetLoginUserDto;
import com.tripper.service.EmailService;
import com.tripper.service.JwtUserDetailsService;
import com.tripper.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "회원 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private JwtUserDetailsService userDetailsService;

    @ApiOperation(
            value = "회원가입"
            , notes = "회원가입 폼에 입력한 정보로 회원가입을 실행한다.")
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signup(@ApiParam(value = "회원가입 폼에 입력한 정보를 담고있는 객체") @RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "아이디 중복 체크"
            , notes = "회원가입 폼에서 아이디 중복 체크를 실행한다.")
    @GetMapping(value = "/id-check")
    public ResponseEntity checkMemIdExists(@ApiParam(value = "중복체크를 실행할 아이디") @RequestParam("id") String checkId) {

        ResponseEntity responseEntity;
        Boolean isExists = userService.checkMemIdExists(checkId);

        if (isExists) {
            /* 아이디 중복인 경우 */
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).build();
        } else {
            /* 아이디 중복 아닌 경우 */
            responseEntity = ResponseEntity.ok().build();
        }
        return responseEntity;

    }

    @ApiOperation(
            value = "로그인"
            , notes = "로그인 폼에 입력한 정보로 로그인을 실행한 후 토큰을 생성한다.")
    @PostMapping(value = "/login")
    public ResponseEntity<GetLoginUserDto> createAuthenticationToken(@RequestBody CreateJwtDto dto) throws Exception {

        authenticate(dto.getMemId(), dto.getPassword());

        final User user = userDetailsService.loadUserByUsername(dto.getMemId());

        /* 토큰 생성 */
        final String token = jwtTokenUtil.generateToken(user);
        Long user_id = user.getId();
        String memId = user.getMemId();
        Role auth = user.getAuth();

        GetLoginUserDto getLoginUserDto = new GetLoginUserDto(token, user_id, memId, auth);

        return ResponseEntity.ok().body(getLoginUserDto);

    }

    private void authenticate(String memId, String password) throws Exception {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(memId, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }

    @ApiOperation(
            value = "로그아웃"
            , notes = "로그아웃을 실행한다.")
    @PostMapping("/user/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "이메일 인증"
            , notes = "사용자 db에 저장돼있는 이메일로 인증 코드를 전송한다.")
    @PostMapping("/email-auth")
    public ResponseEntity sendEmail(Authentication authentication) {

        /* 현재 로그인한 사용자 정보 가져오기 */
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findUserByMemId(userDetails.getUsername());

        /* 해당 사용자의 db에 저장돼있는 이메일로 메일 전송 */
        CreateEmailDto dto = emailService.createMailAndChangeAuth(user.getEmail(), user.getMemId());
        emailService.mailSend(dto);

        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "이메일 인증 코드 일치 확인"
            , notes = "입력한 이메일 인증 코드의 일치 여부를 확인한다.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ok: 이메일 인증 완료.")})
    @PutMapping(value = "/email-auth")
    public ResponseEntity checkEmailAuthCode(Authentication authentication,
                                     @ApiParam(value = "입력한 인증 코드") @RequestParam("authCode") String authCode) {

        /* 현재 로그인한 사용자 정보 가져오기 */
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        User user = userService.findUserByMemId(userDetails.getUsername());

        /* 일치 여부 확인 */
        Boolean rslt = emailService.checkEmailAuthCode(authCode, user.getMemId());

        /* 일치하면 ROLE_USER로 업데이트 */
        if(rslt) {
            userService.updateAuth(user.getId(), Role.ROLE_USER);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "회원 정보 수정"
            , notes = "회원 정보 수정을 실행한다.")
    @PutMapping("/user/{userId}")
    public ResponseEntity updateUser(@ApiParam(value = "수정할 회원의 고유 id") @PathVariable("userId") Long userId,
                                     @ApiParam(value = "회원 정보 수정 폼에 입력한 정보를 갖고있는 객체") @RequestBody UpdateUserDto updateUserDto) {

        userService.updateUserInfo(userId, updateUserDto);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "회원 탈퇴"
            , notes = "회원 정보 삭제를 실행한다.")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity deleteUser(@ApiParam(value = "삭제할 회원의 고유 id") @PathVariable("userId") Long userId) {

        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();

    }

}