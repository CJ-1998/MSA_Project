package com.sparta.msa_exam.order.client;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseListDto {
    private List<ProductResponseDto> products;
}
