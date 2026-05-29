package com.my.total_jpa_back.repository;

import com.my.total_jpa_back.common.entitiy.OrderStatus;
import com.my.total_jpa_back.orders.dto.OrderResponse;
import com.my.total_jpa_back.orders.entity.UserOrder;
import com.my.total_jpa_back.orders.repository.UserOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class UserOrderRepositoryTest {
    @Autowired
    UserOrderRepository userOrderRepository;


    @Test @Transactional
    @DisplayName("DTO TEST")
    void dtoResultTest(){
        List<OrderResponse> result = userOrderRepository.findOrderResponse();
        result.stream()
                .limit(100)
                .forEach(x ->log.info("주문 번호 : {} 제품명 : {} 고객명 : {}",
                        x.getOrderId(),x.getProductName(),x.getUserName()));
    }

    @Test
    @DisplayName("전체주문조회")
    void findAll(){
        // given
        // when
        List<UserOrder> orders = userOrderRepository.findAll();
        // then
    }

    @Test
    @DisplayName("findByStatus")
    void findByStatus(){
        List<UserOrder> orders = userOrderRepository
                .findByStatus(OrderStatus.COMPLETE);
        for(UserOrder order : orders){
            log.info("orderId = {}, status = {}", order.getId(), order.getStatus());
        }
    }

    @Test
    @DisplayName("findByProductNameContaining")
    void findByProductNameContaining(){
        List<UserOrder> orders = userOrderRepository
                .findByProductNameContaining("Dunk");
        for(UserOrder order : orders){
            log.info("orderId = {}, productName = {}",
                    order.getId(), order.getProductName());
        }
    }
    @Test
    @DisplayName("findByPriceGreaterThanEqual")
    void findByPriceGreaterThanEqual(){
        List<UserOrder> orders = userOrderRepository
                .findByPriceGreaterThanEqual(300000);
        for(UserOrder order : orders){
            log.info("orderId = {}, price = {}",
                    order.getId(), order.getPrice());
        }
    }

//    @Test
//    @DisplayName("findByUserId")
//    void findByUserId(){
//        List<UserOrder> orders = userOrderRepository
//                .findByUserId(1L);
//        for(UserOrder order : orders){
//            log.info("userId = {}, productName = {}",
//                    order.getUserId(), order.getProductName());
//        }
//    }
//
//    @Test
//    @DisplayName("findByUserIdAndStatus")
//    void findByUserIdAndStatus(){
//        List<UserOrder> orders = userOrderRepository
//                .findByUserIdAndStatus(10L, OrderStatus.COMPLETE);
//        for(UserOrder order : orders){
//            log.info("userId = {}, status = {}",
//                    order.getUserId(), order.getStatus());
//        }
//    }

    @Test
    @DisplayName("findByPriceBetween")
    void findByPriceBetween(){
        List<UserOrder> orders = userOrderRepository
                .findByPriceBetween(290000, 300000);
        for(UserOrder order : orders){
            log.info("productName = {}, price = {}",
                    order.getProductName(), order.getPrice());
        }
    }

    @Test
    @DisplayName("findAllByOrderByPriceDesc")
    void findAllByOrderByPriceDesc(){
        List<UserOrder> orders = userOrderRepository
                .findAllByOrderByPriceDesc();
        for(int i = 0; i < 5; i ++){
            log.info("productName = {}, price = {}",
                    orders.get(i).getProductName(),
                    orders.get(i).getPrice());
        }
    }

    @Test
    @DisplayName("findTop5ByOrderByCreatedAtDesc")
    void findTop5ByOrderByCreatedAtDesc(){
        List<UserOrder> orders = userOrderRepository
                .findTop5ByOrderByCreatedAtDesc();
        for(UserOrder order : orders){
            log.info("productName = {}, createdAt = {}",
                    order.getProductName(), order.getCreatedAt());
        }
    }

    @Test
    @DisplayName("findByStatusIn")
    void findByStatusIn(){
        List<OrderStatus> orderStatusList = new ArrayList<>(
                List.of(OrderStatus.READY, OrderStatus.SHIPPING)
        );
//        orderStatusList.add(OrderStatus.READY);
//        orderStatusList.add(OrderStatus.SHIPPING);
        List<UserOrder> orders = userOrderRepository
                .findByStatusIn(orderStatusList);
        for(UserOrder order : orders){
            log.info("orderId = {}, status = {}",
                    order.getId(), order.getStatus());
        }
    }

    @Test @Transactional
    @DisplayName("주문 조회 / 조회 후 회원 정보 확인")
    void findOrderAndUserTest(){
        UserOrder userOrder = userOrderRepository.findById(1L).orElseThrow();
        log.info("orderId = {} 제품명 = {} 가격 = {} 배송상태 = {}"
                , userOrder.getId(), userOrder.getProductName(), userOrder.getPrice(),userOrder.getStatus());
        log.info("주문 고객 = {}  이메일 = {}"
                ,userOrder.getUser().getName(),userOrder.getUser().getEmail());
    }
}