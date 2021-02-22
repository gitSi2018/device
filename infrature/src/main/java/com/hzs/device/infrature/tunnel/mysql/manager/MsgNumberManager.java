package com.hzs.device.infrature.tunnel.mysql.manager;

import com.hzs.device.common.enums.MsgSendIDEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
public class MsgNumberManager {


    public List<Integer> getMsgNum(MsgSendIDEnum msgSendIDEnum){

        Integer[] msgNum = new Integer[2];
        int num = (int)(System.currentTimeMillis() & Integer.valueOf("1111111111111111", 2));
        msgNum[1] = (num & Integer.valueOf("11111111", 2));
        msgNum[0] =(num >> 8);
        log.info("MsgNumberManager msgSendIDEnum:{}, msgNum:{}", msgSendIDEnum, msgNum);
        return Arrays.asList(msgNum);
    }
}
