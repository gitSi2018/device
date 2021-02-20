package com.hzs.device.common.enums;

import lombok.Getter;

/**
 * @author: HongZhenSi
 * @date: 2021/2/1
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Getter
public enum  DataEncryptEnum {

    NO_ENCRYPT(0, "不加密"),

    RAS(1, "RAS")

    ;
    private final int code;

    private final String desc;

    DataEncryptEnum(int code, String desc){

        this.code = code;
        this.desc = desc;
    }
}
