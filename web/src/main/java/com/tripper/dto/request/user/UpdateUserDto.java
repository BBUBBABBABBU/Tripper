package com.tripper.dto.request.user;

import com.tripper.domain.user.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@ApiModel(value = "회원 수정 폼 Request DTO")
@Getter @Setter
@RequiredArgsConstructor
public class UpdateUserDto {

    @ApiModelProperty(value = "비밀번호")
    private String password;

    @ApiModelProperty(value = "사용자 전화번호")
    private String phone;

    @ApiModelProperty(value = "사용자 이메일")
    private String email;

    @ApiModelProperty(value = "사용자 닉네임")
    private String nickname;

    @ApiModelProperty(value = "사용자 권한")
    private Role auth;

}
