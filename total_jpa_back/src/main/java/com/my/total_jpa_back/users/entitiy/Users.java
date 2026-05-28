package com.my.total_jpa_back.users.entitiy;

import com.my.total_jpa_back.common.entity.BaseEntity;
import com.my.total_jpa_back.common.entity.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity @Getter @Setter @Table(name = "users") @ToString(callSuper = true)
public class Users extends BaseEntity {
    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String email;
    @Column(name = "like_color")
    private String likeColor;
}
