package com.hzs.device.common.msgin.msg;

import com.hzs.device.common.msgin.BaseMsgIn;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
@ToString(callSuper = true)
public class LocationMsg extends BaseMsgIn {


    private String deviceId;

//    private Integer lat;
//
//    private Integer lng;

    private BigDecimal latBigDec;

    private BigDecimal lngBigDec;

    /**
     * 海拔高度 单位m
     */
    private Integer high;

    /**
     * 速度 单位 0.1 /km*h
     */
    private Integer speed;

    /**
     * 0-359,正北为0，顺时针
     */
    private Integer direct;

    private Long gpsTime;

    private Date created;

    private Date updated;

}
