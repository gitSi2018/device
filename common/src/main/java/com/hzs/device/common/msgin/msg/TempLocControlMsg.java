package com.hzs.device.common.msgin.msg;

import com.hzs.device.common.msgin.BaseMsgIn;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HongZhenSi
 * @date: 2021/2/19
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
@ToString(callSuper = true)
public class TempLocControlMsg extends BaseMsgIn {

    /**
     * 时间间隔 单位 s
     *
     *  数据类型 WORD
     */
    private int timeInterval;

    /**
     * 有效的时间 单位 s
     *  数据类型 DWORD
     */
    private int validTime;
}
