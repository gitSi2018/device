package com.hzs.device.common.msgin;

import lombok.Data;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
public class MsgCeil {

    /**
     * 包分包后的总包数
     */
    private int msgPackages;

    /**
     * 包的序号
     */
    private int msgOrder;
}
