package com.my.total_jpa_back.common.entitiy;

import lombok.Getter;

@Getter
public enum OrderStatus {
    READY,
    SHIPPING,
    COMPLETE,
    CANCEL
}
