package com.sparta.msa_exam.order;

import com.sparta.msa_exam.order.client.ProductClient;
import com.sparta.msa_exam.order.client.ProductResponseDto;
import com.sparta.msa_exam.order.client.ProductResponseListDto;
import com.sparta.msa_exam.order.domain.MappedOrder;
import com.sparta.msa_exam.order.domain.Order;
import com.sparta.msa_exam.order.dto.OrderRequestDto;
import com.sparta.msa_exam.order.dto.OrderResponseDto;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public void createOrder(OrderRequestDto orderRequestDto) {
        Order order = new Order();
        List<MappedOrder> mappedOrders = new ArrayList<>();

        ProductResponseListDto products = productClient.getProducts();
        List<ProductResponseDto> productResponseDtos = products.getProducts();

        for (Long productId : orderRequestDto.getOrderItemIds()) {
            boolean check = false;
            for (ProductResponseDto productResponseDto : productResponseDtos) {
                if (productResponseDto.getId() == productId) {
                    check = true;
                    break;
                }
            }
            if (check) {
                MappedOrder mappedOrder = getMappedOrder(order, productId);
                mappedOrders.add(mappedOrder);
            }
        }

        order.setName("order");
        order.setMappedOrders(mappedOrders);
        orderRepository.save(order);
    }

    @Transactional
    public boolean addProductInOrder(Long orderId, Long productId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        ProductResponseListDto products = productClient.getProducts();

        List<ProductResponseDto> productResponseDtos = products.getProducts();

        boolean check = false;
        for (ProductResponseDto productResponseDto : productResponseDtos) {
            if (productResponseDto.getId() == productId) {
                check = true;
                break;
            }
        }
        if (check) {
            MappedOrder mappedOrder = getMappedOrder(order, productId);
            order.getMappedOrders().add(mappedOrder);
        }

        return check;
    }

    private MappedOrder getMappedOrder(Order order, Long productId) {
        MappedOrder mappedOrder = new MappedOrder();
        mappedOrder.setOrder(order);
        mappedOrder.setProductId(productId);
        return mappedOrder;
    }

    public OrderResponseDto getOneOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setOrderId(order.getId());

        List<MappedOrder> mappedOrders = order.getMappedOrders();

        List<Long> productIds = new ArrayList<>();

        for (MappedOrder mappedOrder : mappedOrders) {
            productIds.add(mappedOrder.getProductId());
        }

        orderResponseDto.setProductId(productIds);

        return orderResponseDto;
    }
}
