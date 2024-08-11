package com.sparta.msa_exam.order;

import com.sparta.msa_exam.order.dto.OrderOneRequestDto;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    @Value("${server.port}")
    private final String port;

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        orderService.createOrder(orderRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", port);

        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity addProductInOrder(@PathVariable("orderId") Long orderId,
                                            @RequestBody OrderOneRequestDto orderOneRequestDto) {
        Boolean addCheck = orderService.addProductInOrder(orderId, orderOneRequestDto.getProductId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", port);

        return new ResponseEntity(addCheck, headers, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity getOneOrder(@PathVariable("orderId") Long orderId) {
        OrderResponseDto orderResponseDto = orderService.getOneOrder(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Server-Port", port);

        return new ResponseEntity(orderResponseDto, headers, HttpStatus.OK);
    }
}
