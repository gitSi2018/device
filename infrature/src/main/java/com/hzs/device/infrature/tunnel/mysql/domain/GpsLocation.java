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

    @Column(name = "gps_date_time")
    private Date gpsDateTime;


    /**
     * gps的接收时间
     */
    @Column(name = "gps_time")
    private Long gpsTime;


    /**
     * 电量百分比
     */
    @Column(name = "power_percent")
    private Integer powerPercent;

    @Column(name = "vibration_alarm")
    private boolean vibrationAlarm;




    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    @Column(name = "is_delete")
    private Boolean isDelete;

}
