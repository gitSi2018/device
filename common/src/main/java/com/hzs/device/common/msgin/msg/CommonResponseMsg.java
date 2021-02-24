package com.hzs.device.common.msgin.msg;

import com.hzs.device.common.msgin.BaseMsgIn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: HongZhenSi
 * @date: 2021/2/24
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResponseMsg extends BaseMsgIn {

    /**
     * 应答的流水号
     */
    private String responseOrderNum;

    /**
     * 应答的消息id
     */
    private String responseId;

    /**
     * 结果
     */
    private int resultCode;
}
