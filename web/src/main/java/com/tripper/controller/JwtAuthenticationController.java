package com.tripper.controller;

import com.tripper.config.JwtTokenUtil;
import com.tripper.domain.user.User;
import com.tripper.service.JwtUserDetailsService;
import com.tripper.dto.request.CreateJwtDto;
import com.tripper.dto.response.GetJwtDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Jwt 인증 관련 컨트롤러 클래스
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private JwtUserDetailsService userDetailsService;

    @ApiOperation(
            value = "로그인"
            , notes = "로그인 폼에 입력한 정보로 로그인을 실행한 후 토큰을 생성한다.")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody CreateJwtDto dto) throws Exception {

        authenticate(dto.getMemId(), dto.getPassword());

        final User user = userDetailsService.loadUserByUsername(dto.getMemId());
        final String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok(new GetJwtDto(token));

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
}