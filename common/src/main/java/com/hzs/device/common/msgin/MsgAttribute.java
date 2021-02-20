package com.hzs.device.common.msgin;

import com.hzs.device.common.enums.DataEncryptEnum;
import lombok.Data;

/**
 * @author: HongZhenSi
 * @date: 2021/2/1
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Data
public class MsgAttribute {

    private boolean fenBao;

    private DataEncryptEnum encryptEnum;

    private int msgLength;
}
