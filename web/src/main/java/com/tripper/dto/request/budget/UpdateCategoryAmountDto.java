package com.tripper.dto.request.budget;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Getter @Setter
@AllArgsConstructor
public class UpdateCategoryAmountDto {
    @Positive(message = "예산은 양수만 가능합니다.")
    private int amount;
}
