package com.hzs.device.common.msgin.msg;

import com.hzs.device.common.msgin.BaseMsgIn;
import lombok.Data;
import lombok.ToString;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */

@Data
@ToString(callSuper = true)
public class LocationResponseMsg extends BaseMsgIn {

    /**
     * 应答流水号
     * WORD
     */
    private String responseId;


    /**
     * 位置信息汇报
     */
    private LocationResponseMsg responseMsg;
}
