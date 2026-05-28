package com.my.total_jpa_back.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) @ToString
@Getter @MappedSuperclass // 이거는 테이블로 만들지 마라.
public class BaseEntity {
    @CreatedDate @Column(name = "created_at" , updatable = false,nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate @Column(name = "updated_at" , nullable = false)
    private LocalDateTime updatedAt;
}
