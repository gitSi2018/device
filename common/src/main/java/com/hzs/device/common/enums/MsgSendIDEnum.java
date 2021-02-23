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
    ;


    private List<Integer> msgId;

    private String msgKey;


    MsgSendIDEnum(List<Integer> msgId, String msgKey){

        this.msgId = msgId;
        this.msgKey = msgKey;
    }
}
