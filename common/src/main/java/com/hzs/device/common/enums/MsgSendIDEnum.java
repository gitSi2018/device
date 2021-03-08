package com.hzs.device.common.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/2/20
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Getter
public enum MsgSendIDEnum {

    QUERY_LOCATION(Arrays.asList(0x82, 0x01), "QUERY_LOCATION"),


    TEMP_LOCATION_CONTROL(Arrays.asList(0x82, 0x02), "TEMP_LOCATION_CONTROL"),

    CONNECTION_RESPONSE(Arrays.asList(0x81, 0x00), "CONNECTION_RESPONSE"),

    DEVICE_CONTROL(Arrays.asList(0x81, 0x05), "DEVICE_CONTROL"),

    COMMON_RESPONSE(Arrays.asList(0x80, 0x01), "COMMON_RESPONSE"),

    QUERY_DEVICE(Arrays.asList(0x81, 0x04), "QUERY_DEVICE"),

    SET_DEVICE_PARAMETER(Arrays.asList(0x81, 0x03),"SET_DEVICE_PARAMETER"),

    SLEEP_RESPONSE(Arrays.asList(0x81, 0x25), "SLEEP_RESPONSE"),

    UN_SLEEP_RESPONSE(Arrays.asList(0x81, 0x08), "UN_SLEEP_RESPONSE"),

    ;


    private List<Integer> msgId;

    private String msgKey;


    MsgSendIDEnum(List<Integer> msgId, String msgKey){

        this.msgId = msgId;
        this.msgKey = msgKey;
    }
}
