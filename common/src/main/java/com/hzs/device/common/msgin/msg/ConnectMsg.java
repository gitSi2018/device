package com.hzs.device.common.msgin.msg;

import com.hzs.device.common.msgin.BaseMsgIn;
import lombok.Data;
import lombok.ToString;

import io.netty.channel.socket.SocketChannel;

/**
 * @author: HongZhenSi
 * @date: 2021/1/30
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
@ToString(callSuper = true)
public class ConnectMsg extends BaseMsgIn {


    /**
     *
     * 终端ID
     * BYTE[7]
     * IMEI  后七位（asc码）
     */
    private String deviceId;



    /**
     * 该设备与后台连接的通道
     */
    private SocketChannel channel;

    /**
     * 该设备与后台连接的通道的id
     */
    private String channelIdStr;


    /**
     * 注册消息流水号
     */
    private Integer[] msgOrderNum;

    /**
     * 0 - 在线
     * 10 - 掉线
     */
    private int status;
}
