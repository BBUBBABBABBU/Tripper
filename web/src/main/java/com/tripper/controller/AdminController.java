package com.tripper.controller;

import com.tripper.domain.user.Role;
import com.tripper.dto.request.hotel.CreateHotelDto;
import com.tripper.dto.request.hotel.CreateRoomDto;
import com.tripper.dto.request.hotel.UpdateHotelDto;
import com.tripper.dto.request.hotel.UpdateRoomDto;
import com.tripper.dto.response.hotel.GetHotelListDto;
import com.tripper.dto.response.hotel.GetReservationListDto;
import com.tripper.dto.response.user.GetUserListDto;
import com.tripper.service.HotelService;
import com.tripper.service.PhotoService;
import com.tripper.service.ReservationService;
import com.tripper.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(tags = "관리자 API")
@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final HotelService hotelService;
    private final ReservationService reservationService;
    private final PhotoService photoService;

    /*---------------------------------    회원 관리    -------------------------------*/
    @ApiOperation(
            value = "회원 목록 조회"
            , notes = "회원 전체 목록을 조회한다.")
    @GetMapping("/users")
    public ResponseEntity<GetUserListDto> getUserList() {

        GetUserListDto users = userService.findAllUsers();
        return ResponseEntity.ok().body(users);

    }

    @ApiOperation(
            value = "매니저 권한 부여"
            , notes = "user 회원에게 manager 권한을 부여한다.")
    @PutMapping("/auth/user/{userId}")
    public ResponseEntity updateAuth(@ApiParam(value = "권한 부여할 회원의 고유 id") @PathVariable("userId") Long userId) {

        userService.updateAuth(userId, Role.ROLE_MANAGER);
        return ResponseEntity.ok().build();

    }

    /*---------------------------------    호텔 관리    -------------------------------*/
    @ApiOperation(
            value = "호텔 등록"
            , notes = "호텔 등록 폼에서 입력한 정보로 호텔 등록을 실행한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hotel")
    public ResponseEntity createHotel(Principal principal,
                            @ApiParam(value = "호텔 등록 폼에 입력한 정보를 담고있는 객체") CreateHotelDto createHotelDto) throws Exception {
        hotelService.createHotel(createHotelDto, principal.getName());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "호텔 정보 수정"
            , notes = "호텔 정보 수정을 실행한다.")
    @PutMapping("/hotel/{hotelId}")
    public ResponseEntity updateHotel(@ApiParam(value = "수정할 호텔의 고유 id") @PathVariable("hotelId") Long hotelId,
                                      @ApiParam(value = "호텔 수정 폼에 입력한 정보를 갖고있는 객체") UpdateHotelDto updateHotelDto) throws Exception {

        hotelService.updateHotel(hotelId, updateHotelDto);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "호텔 삭제"
            , notes = "호텔 삭제를 실행한다.")
    @DeleteMapping("/hotel/{hotelId}")
    public ResponseEntity deletePost(@ApiParam(value = "삭제할 호텔의 고유 id") @PathVariable("hotelId") Long hotelId) {

        hotelService.deleteHotelById(hotelId);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "매니저가 등록한 호텔 조회"
            , notes = "매니저가 등록한 호텔 목록을 조회한다.")
    @GetMapping("/hotels")
    public ResponseEntity<GetHotelListDto> getManagerHotelList(Principal principal) {

        GetHotelListDto hotels = hotelService.findManagersHotel(principal.getName());
        return ResponseEntity.ok().body(hotels);

    }

    @ApiOperation(
            value = "객실 등록"
            , notes = "객실 등록 폼에서 입력한 정보로 객실 등록을 실행한다.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/roomtype")
    public ResponseEntity createRoom(@ApiParam(value = "객실을 등록할 호텔의 고유 id") @RequestParam("hotelId") Long hotelId,
                                     @ApiParam(value = "객실 등록 폼에 입력한 정보를 담고있는 객체") CreateRoomDto createRoomDto) throws Exception {

        hotelService.createRoom(hotelId, createRoomDto);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "객실 정보 수정"
            , notes = "객실 정보 수정을 실행한다.")
    @PutMapping("/roomtype/{roomtypeId}")
    public ResponseEntity updateHotel(@ApiParam(value = "수정할 객실의 고유 id") @PathVariable("roomtypeId") Long roomtypeId,
                                      @ApiParam(value = "객실 수정 폼에 입력한 정보를 갖고있는 객체") UpdateRoomDto updateRoomDto) throws Exception {

        hotelService.updateRoom(roomtypeId, updateRoomDto);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "객실 삭제"
            , notes = "객실 삭제를 실행한다.")
    @DeleteMapping("/roomtype/{roomtypeId}")
    public ResponseEntity deleteRoomType(@ApiParam(value = "삭제할 객실의 고유 id") @PathVariable("roomtypeId") Long roomtypeId) {

        hotelService.deleteRoomTypeById(roomtypeId);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "방 호수 삭제"
            , notes = "특정 객실에 배정돼있던 방 호수 삭제를 실행한다.")
    @DeleteMapping("/room/{roomId}")
    public ResponseEntity deleteRoom(@ApiParam(value = "삭제할 객실의 고유 id") @PathVariable("roomId") Long roomId) {

        hotelService.deleteRoomById(roomId);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "사진 삭제"
            , notes = "기존에 등록돼있던 사진 삭제를 실행한다.")
    @DeleteMapping("/photo/{photoId}")
    public ResponseEntity deletePhoto(@ApiParam(value = "삭제할 사진의 고유 id") @PathVariable("photoId") Long photoId) {

        photoService.deletePhotoById(photoId);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(
            value = "특정 호텔에 대한 예약 조회"
            , notes = "특정 호텔의 모든 예약을 조회한다.")
    @GetMapping("/reservations/hotel/{hotelId}")
    public ResponseEntity<GetReservationListDto> findReservationsByHotelId(@ApiParam(value = "조회할 호텔의 고유 id") @PathVariable("hotelId") Long hotelId) {

        GetReservationListDto getReservationListDto = reservationService.findReservationsByHotelId(hotelId);
        return ResponseEntity.ok().body(getReservationListDto);

    }

    @ApiOperation(
            value = "특정 객실에 대한 예약 조회"
            , notes = "특정 객실의 예약을 조회한다.")
    @GetMapping("/reservations/room/{roomId}")
    public ResponseEntity<GetReservationListDto> findReservationsByRoomId(@ApiParam(value = "조회할 객실의 고유 id") @PathVariable("roomId") Long roomId) {

        GetReservationListDto getReservationListDto = reservationService.findReservationsByRoomId(roomId);
        return ResponseEntity.ok().body(getReservationListDto);

    }

    @ApiOperation(
            value = "전체 예약 목록 조회"
            , notes = "모든 예약을 조회한다.")
    @GetMapping("/reservations")
    public ResponseEntity<GetReservationListDto> findAllReservations() {

        GetReservationListDto getReservationListDto;

        try {
            getReservationListDto = reservationService.findAllReservations();
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok().body(getReservationListDto);

    }

}
