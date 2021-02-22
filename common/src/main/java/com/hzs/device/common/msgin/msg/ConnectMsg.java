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
     * 省域ID
     * WORD
     * 标示终端安装车辆所在的省域，0保留，由平台取默认值。省域ID采用GB/T 2260中规定的行政区划代码六位中前两位。
     */
    private String provinceId;

    /**
     * WORD
     * 市县域ID
     * 标示终端安装车辆所在的市域和县域，0保留，由平台取默认值。市县域ID采用GB/T 2260中规定的行政区划代码六位后四位
     */
    private String cityId;

    /**
     * 制造商id
     * BYTE[5]
     * 五个字节，终端制造商编码
     */
    private String producerId;


    /**
     * 终端型号
     * BYTE[8]
     * 八个字节，此终端型号由制造商自行定义，位数不是八位的，补空格。
     */
    private String deviceType;

    /**
     *
     * 终端ID
     * BYTE[7]
     * IMEI  后七位（asc码）
     */
    private String deviceId;


    /**
     * BYTE
     * 车牌颜色，按照JT/T 415-2006的5.4.12
     */
    private String color;


    /**
     * 车牌
     * STRING
     *
     */
    private String deviceNo;


    /**
     * 该设备与后台连接的通道
     */
    private SocketChannel channel;

    /**
     * 该设备与后台连接的通道的id
     */
    private String channelIdStr;

    /**
     * 0 - 在线
     * 10 - 掉线
     */
    private int status;
}
