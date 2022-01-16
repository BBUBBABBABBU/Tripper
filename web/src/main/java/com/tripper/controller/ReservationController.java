package com.tripper.controller;

import com.tripper.dto.request.hotel.CreateReservationDto;
import com.tripper.dto.response.hotel.GetReservationListDto;
import com.tripper.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Api(tags = "호텔 예약 API")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;

    @ApiOperation(
            value = "호텔 예약"
            , notes = "호텔 예약 폼에서 입력한 정보로 호텔 예약을 실행한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/reservation/room/{roomId}")
    public ResponseEntity createReservation(Principal principal,
                                  @ApiParam(value = "예약할 객실의 고유 id") @PathVariable("roomId") Long roomId,
                                  @ApiParam(value = "호텔 예약 폼에 입력한 정보를 담고있는 객체") CreateReservationDto createReservationDto) throws Exception {
        reservationService.createReservation(principal.getName(), roomId, createReservationDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "나의 예약 조회"
            , notes = "현재 로그인한 사용자의 예약을 조회한다.")
    @GetMapping("/my-reservations")
    public ResponseEntity<GetReservationListDto> findReservationsByMemId(Principal principal) {

        GetReservationListDto getReservationListDto = reservationService.findReservationsByMemId(principal.getName());

        return ResponseEntity.ok().body(getReservationListDto);

    }

    @ApiOperation(
            value = "예약 취소"
            , notes = "예약 취소를 실행한다.")
    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity cancelReservation(@ApiParam(value = "취소할 예약의 고유 id") @PathVariable("reservationId") Long reservationId) {

        reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok().build();

    }

}
