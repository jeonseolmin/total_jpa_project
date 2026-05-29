package com.my.total_jpa_back.orders.controller;

import com.my.total_jpa_back.orders.dto.OrderResponse;
import com.my.total_jpa_back.orders.service.UserOrderService;
import com.my.total_jpa_back.users.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequiredArgsConstructor
@RequestMapping("/api")
public class UserOrderController {
    private final UserOrderService userOrderService;
    @GetMapping("/status")
    public List<OrderResponse> findCompleteStatus(){
        return userOrderService.findCompleteStatus();
    }

    @GetMapping("/userName")
    public List<OrderResponse> findCompleteKim(){
        return userOrderService.findOrderKimPrice100000OverDesc();
    }

    @GetMapping("/users/{id}/orders")
    public List<OrderResponse> findByUserId(@PathVariable Long id){
        return userOrderService.findByUserId(id);
    }

}
