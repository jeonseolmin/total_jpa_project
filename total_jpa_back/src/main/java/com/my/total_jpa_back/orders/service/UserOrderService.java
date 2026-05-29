package com.my.total_jpa_back.orders.service;

import com.my.total_jpa_back.common.entitiy.OrderStatus;
import com.my.total_jpa_back.orders.dto.OrderResponse;
import com.my.total_jpa_back.orders.repository.UserOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserOrderService {
    private final UserOrderRepository userOrderRepository;

    public List<OrderResponse> findCompleteStatus(){
        return userOrderRepository.findCompletedStatus(OrderStatus.COMPLETE);
    }

    public List<OrderResponse>findOrderKimPrice100000OverDesc(){
        return userOrderRepository.findOrderKimPrice100000OverDesc(OrderStatus.COMPLETE,"Kim");
    }

    public  List<OrderResponse> findByUserId(Long userId){
        return  userOrderRepository.findOrderByUserId(userId);
    }

//    public List<OrderResponse> findCompleteStatus() {
//        List<OrderResponse> completedOrders = userOrderRepository.findOrderResponse()
//                .stream()
//                .filter(x-> x.getStatus().equals(OrderStatus.COMPLETE))
//                .toList();
//        return completedOrders;
//    }
}
