package com.hzs.device.infrature.tunnel.netty.msgout;

import com.hzs.device.common.enums.MsgSendIDEnum;
import com.hzs.device.infrature.tunnel.mysql.manager.MsgNumberManager;

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
public abstract class MsgOutServiceAbstract implements MsgOutServiceI{



    @Resource
    private MsgNumberManager msgNumberManager;


    public List<Integer> phoneToArrays(String deviceId){

        return Arrays.asList(0x99, 0x99, 0x99, 0x99, 0x91, 0x18);
    }

   public List<Integer>  getMsgNum(MsgSendIDEnum msgSendIDEnum){

        return msgNumberManager.getMsgNum(msgSendIDEnum);
   }

    public static int calculateCheckSum(List<Integer> msg){

        int checkNum = msg.get(0);
        for (int i = 1; i < msg.size(); i++){

            checkNum = checkNum ^ msg.get(i);
        }
        return checkNum;
    }

    public List<Integer> generateLastMsg(List<Integer> msgSend){

        // 拼接头尾 求校验和

        List<Integer> newMsgSend = new ArrayList<>(msgSend.size() + 3);
        newMsgSend.add(126);
        newMsgSend.addAll(msgSend);
        int checkSum = calculateCheckSum(msgSend);
        newMsgSend.addAll(Arrays.asList(checkSum, 126));
        return newMsgSend;
    }
}
