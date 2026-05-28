package com.my.total_jpa_back.common.exception;

import lombok.Builder;
import lombok.Getter;

public class UserNotFoundException extends BusinessException{

    public UserNotFoundException() {
        super("없어요");
    }
}
