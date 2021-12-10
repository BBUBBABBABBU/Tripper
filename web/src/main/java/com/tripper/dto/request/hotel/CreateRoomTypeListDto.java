package com.tripper.dto.request.hotel;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "객실 종류 등록 폼 리스트 Request DTO")
@Getter @Setter
public class CreateRoomTypeListDto {
    List<CreateRoomTypeDto> roomTypeListDtos = new ArrayList<>();
}