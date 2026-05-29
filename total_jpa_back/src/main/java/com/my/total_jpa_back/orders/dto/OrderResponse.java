package com.my.total_jpa_back.orders.dto;

import com.my.total_jpa_back.common.entitiy.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String productName;
    private Integer price;
    private OrderStatus status;
    private String userName;
}
