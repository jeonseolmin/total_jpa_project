package com.my.total_jpa_back.orders.entity;

import com.my.total_jpa_back.common.entitiy.BaseEntity;
import com.my.total_jpa_back.common.entitiy.OrderStatus;
import com.my.total_jpa_back.users.entity.Users;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter @Table(name ="user_order")
public class UserOrder  extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "user_id") //Users TABLE의 외래키 역할
//    private Long userId;

    // Users 객체 자체를 포함시킨다 (객체 시점의 연관 관계)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name = "product_name")
    private String productName;

    private Integer price; // INTEGER인 이유는 QueryDSL 조건 때문에

    @Enumerated(EnumType.STRING)
    private OrderStatus status;


}
