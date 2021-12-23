package com.tripper.dto.response.hotel;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "객실 리스트 Respose DTO")
@Getter @Setter
@AllArgsConstructor
public class GetRoomListDto {
    List<GetRoomDto> rooms = new ArrayList<>();
}
