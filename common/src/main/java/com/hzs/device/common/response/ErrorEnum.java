package com.hzs.device.common.response;

import lombok.Getter;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Getter
public enum ErrorEnum {

    PARAMETER_ERROR(1000, "参数错误"),

    COMMON_SERVER_ERROR(1001, "服务器开小差了，请稍后重试"),

    NO_DEVICE_FOUND(1002, "找不到连接的设备"),

    SEND_MSG_TO_DEVICE_FAILED(1003, "给设备发送信息失败"),
    ;
    private final int code;

    private final String msg;

    ErrorEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
