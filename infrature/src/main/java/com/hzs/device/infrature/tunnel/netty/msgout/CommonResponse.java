package com.hzs.device.infrature.tunnel.netty.msgout;

import com.hzs.device.common.enums.MsgSendIDEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/2/24
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class CommonResponse extends MsgOutServiceAbstract{


    @Override
    public String point() {
        return MsgSendIDEnum.COMMON_RESPONSE.getMsgKey();
    }

    @Override
    public List<Integer> getMsgId() {
        return MsgSendIDEnum.COMMON_RESPONSE.getMsgId();
    }

    @Override
    public List<Integer> getMsgData(String deviceId, Integer... sendData) {


        List<Integer> msgSend = new ArrayList<>();
        //id
        msgSend.addAll(MsgSendIDEnum.COMMON_RESPONSE.getMsgId());
        //消息属性
        msgSend.addAll(Arrays.asList(0x00, sendData.length));
        //手机号
        msgSend.addAll(phoneToArrays(deviceId));
        //消息流水号
        msgSend.addAll(getMsgNum(MsgSendIDEnum.COMMON_RESPONSE));

        //消息内容
        msgSend.addAll(Arrays.asList(sendData));

        return generateLastMsg(msgSend);
    }
}
