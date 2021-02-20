package com.hzs.device.infrature.tunnel.netty.msgout;

import com.hzs.device.common.enums.MsgSendIDEnum;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author: HongZhenSi
 * @date: 2021/2/20
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */

@Slf4j
@Component
public class LocationQuery extends MsgOutServiceAbstract{

    @Resource
    private ConnectionManager connectionManager;




    public static void main(String[] args) {

        List<Integer> msg = Arrays.asList(0X82,0X01,  0X00,0X08,  0X99,0X99,0X99,0X99,0X91,0X18, 0X00, 0X0c);
        int sum = calculateCheckSum(msg);
        log.info("sum:{}, sumTest:{}", sum, Integer.valueOf("0e", 16));

    }

    @Override
    public String point() {

        return MsgSendIDEnum.QUERY_LOCATION.getMsgKey();
    }

    @Override
    public List<Integer> getMsgId() {

        return MsgSendIDEnum.QUERY_LOCATION.getMsgId();
    }

    @Override
    public List<Integer> getMsgData(String deviceId, Integer... sendData) {

        List<Integer> msgSend = new ArrayList<>();
        //id
        msgSend.addAll(MsgSendIDEnum.QUERY_LOCATION.getMsgId());
        //消息属性
        msgSend.addAll(Arrays.asList(0x00, 0x00));
        //手机号
        msgSend.addAll(phoneToArrays(deviceId));
        //消息流水号
        msgSend.addAll(getMsgNum(MsgSendIDEnum.QUERY_LOCATION));

        return generateLastMsg(msgSend);
    }
}
