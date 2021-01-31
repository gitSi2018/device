package com.hzs.device.common.msgin;

import lombok.Data;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
public class BaseMsgIn extends Base{

    // msg format 标识位	消息头	消息体	校验码	标识位

    /**
     * Ox7e
     */
    private Integer flag = 0x7e;

    /**
     * 12 个字节
     */
    private MsgHead msgHead;

    /**
     * 一个字节
     */
    private Integer checkCode;

}
