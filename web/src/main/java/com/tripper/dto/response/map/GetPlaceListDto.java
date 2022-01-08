package com.tripper.dto.response.map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@ApiModel(value = "장소 리스트 Response DTO")
@Getter
@AllArgsConstructor
public class GetPlaceListDto {

    @ApiModelProperty(value = "장소 리스트")
    List<GetPlaceDto> getPlaceDtos;

    @ApiModelProperty(value = "장소 총 개수")
    int placeTotal;

}
