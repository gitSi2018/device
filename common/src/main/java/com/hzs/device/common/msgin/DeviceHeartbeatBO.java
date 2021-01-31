package com.hzs.device.common.msgin;

import java.nio.channels.SocketChannel;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
public class DeviceHeartbeatBO {





    /**
     * chairIdOne chairIdTwo chairIdThree chairIdFour 拼接的字符串
     */
    private String chairId;


    /**
     * 该椅子与后台连接的通道
     */
    private SocketChannel channel;

    /**
     * 该椅子与后台连接的通道的id
     */
    private String channelIdStr;
}
