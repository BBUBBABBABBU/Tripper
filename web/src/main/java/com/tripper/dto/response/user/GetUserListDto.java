package com.tripper.dto.response.user;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "회원 리스트 Respose DTO")
@Getter @Setter
@AllArgsConstructor
public class GetUserListDto {
    List<GetUserDto> users = new ArrayList<>();
}
