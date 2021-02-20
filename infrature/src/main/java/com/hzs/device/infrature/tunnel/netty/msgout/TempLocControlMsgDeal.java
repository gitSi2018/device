package com.hzs.device.infrature.tunnel.netty.msgout;

import com.hzs.device.common.enums.MsgSendIDEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/2/19
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Service
public class TempLocControlMsgDeal extends MsgOutServiceAbstract{


    @Override
    public String point() {

        return MsgSendIDEnum.TEMP_LOCATION_CONTROL.getMsgKey();
    }

    @Override
    public List<Integer> getMsgId() {

        return MsgSendIDEnum.TEMP_LOCATION_CONTROL.getMsgId();
    }

    @Override
    public List<Integer> getMsgData(String deviceId, Integer... sendData) {


        List<Integer> msgSend = new ArrayList<>();
        //id
        msgSend.addAll(MsgSendIDEnum.QUERY_LOCATION.getMsgId());
        //消息属性
        msgSend.addAll(Arrays.asList(0x00, 0x05));
        //手机号
        msgSend.addAll(phoneToArrays(deviceId));
        //消息流水号
        msgSend.addAll(getMsgNum(MsgSendIDEnum.QUERY_LOCATION));

        //消息内容
        msgSend.addAll(Arrays.asList(sendData));

        return generateLastMsg(msgSend);
    }
}
