package com.tripper.dto.response.map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@ApiModel(value = "최단 경로 구할 때 사용할 객체")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceShortInfo implements Cloneable {

    @ApiModelProperty(value = "장소명")
    private String name;

    @ApiModelProperty(value = "경도")
    private String x;

    @ApiModelProperty(value = "위도")
    private String y;

    @ApiModelProperty(value = "선택 여부")
    private Boolean alreadyUsed;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
