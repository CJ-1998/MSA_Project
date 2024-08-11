package com.sparta.msa_exam.product;

import com.sparta.msa_exam.product.dto.ProductAddRequestDto;
import com.sparta.msa_exam.product.dto.ProductAddResponseDto;
import com.sparta.msa_exam.product.dto.ProductListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Value("${server.port}")
    private Integer port;

    private final ProductService productService;

    @PostMapping
    public ResponseEntity createProduct(@RequestBody ProductAddRequestDto productAddRequestDto) {
        ProductAddResponseDto productAddResponseDto = productService.createProduct(productAddRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", String.valueOf(port));

        return new ResponseEntity(productAddResponseDto, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getProducts() {
        ProductListResponseDto productListResponseDto = productService.getProducts();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", String.valueOf(port));

        return new ResponseEntity(productListResponseDto, headers, HttpStatus.OK);
    }
}
