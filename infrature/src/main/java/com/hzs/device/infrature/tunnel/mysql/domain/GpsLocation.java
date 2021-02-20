package com.hzs.device.infrature.tunnel.mysql.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author: HongZhenSi
 * @date: 2021/2/19
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
@Table(name = "hz_gps_location")
public class GpsLocation {


    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "lat_big_dec")
    private BigDecimal latBigDec;

    @Column(name = "lng_big_dec")
    private BigDecimal lngBigDec;

    /**
     * 海拔高度 单位m
     */
    @Column(name = "high")
    private Integer high;

    /**
     * 速度 单位 0.1 /km*h
     */
    @Column(name = "speed")
    private Integer speed;

    /**
     * 0-359,正北为0，顺时针
     */
    @Column(name = "direct")
    private Integer direct;


    /**
     * gps的接收时间
     */
    @Column(name = "gps_time")
    private Long gpsTime;


    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "is_delete")
    private Boolean isDelete;

}
