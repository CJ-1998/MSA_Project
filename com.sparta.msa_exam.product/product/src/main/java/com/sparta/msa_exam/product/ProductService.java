package com.sparta.msa_exam.product;

import com.sparta.msa_exam.product.domain.Product;
import com.sparta.msa_exam.product.dto.ProductAddRequestDto;
import com.sparta.msa_exam.product.dto.ProductAddResponseDto;
import com.sparta.msa_exam.product.dto.ProductListResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductAddResponseDto createProduct(ProductAddRequestDto productAddRequestDto) {
        Product product = Product.createProduct(productAddRequestDto);
        Product savedProduct = productRepository.save(product);

        return getProductAddResponseDto(savedProduct);
    }

    private ProductAddResponseDto getProductAddResponseDto(Product savedProduct) {
        ProductAddResponseDto productAddResponseDto = new ProductAddResponseDto();
        productAddResponseDto.setId(savedProduct.getId());
        productAddResponseDto.setName(savedProduct.getName());
        productAddResponseDto.setPrice(savedProduct.getPrice());

        return productAddResponseDto;
    }

    public ProductListResponseDto getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductAddResponseDto> productsDto = new ArrayList<>();

        for (Product product : products) {
            productsDto.add(getProductAddResponseDto(product));
        }

        return new ProductListResponseDto(productsDto);
    }
}
