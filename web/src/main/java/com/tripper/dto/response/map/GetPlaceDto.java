package com.tripper.dto.response.map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "장소 response DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPlaceDto {

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "장소명")
    private String name;
    
    @ApiModelProperty(value = "전화번호")
    private String tel;

    @ApiModelProperty(value = "경도")
    private String x;

    @ApiModelProperty(value = "위도")
    private String y;

    @ApiModelProperty(value = "도로명 주소")
    private String roadAddress;

}
