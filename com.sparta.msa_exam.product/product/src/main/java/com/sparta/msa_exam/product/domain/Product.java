package com.sparta.msa_exam.product.domain;

import com.sparta.msa_exam.product.dto.ProductAddRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer price;

    public static Product createProduct(ProductAddRequestDto productAddRequestDto) {
        return Product.builder()
                .name(productAddRequestDto.getName())
                .price(productAddRequestDto.getPrice())
                .build();
    }
}
