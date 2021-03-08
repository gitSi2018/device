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


    private boolean vibrationAlarm;

    /**
     * 电量百分比
     */
    private Integer powerPercent;

    private BigDecimal latBigDec;

    private BigDecimal lngBigDec;


    private Long gpsTime;

    private Date gpsDateTime;

    private Date created;

    private Date updated;

}
