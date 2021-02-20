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
public class MsgHead {

    /**
     * WORD
     */
    private String msgId;

    /**
     * 消息体属性格式结构图见图2 WORD
     */
    private MsgAttribute msgAttribute;

    /**
     * 终端手机号 BCD[6]  根据安装后终端自身的手机号转换。手机号不足12位，则在前补充数字，大陆手机
     * 号补充数字0港澳台则根据其区号进行位数补充
     */
    private String phone;


    /**
     * 消息流水号 WORD 按发送顺序从0开始循环累加
     */
    private String msgNo;

    /**
     * 消息包封装项. 如果消息体属性中相关标识位确定消息分包处理，则该项有内容，否则无该项【注：无分包无此项】
     */
    private MsgCeil msgCeil;

}
