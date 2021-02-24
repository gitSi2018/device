package com.hzs.device.infrature.tunnel.netty.msgout;

import com.hzs.device.common.enums.MsgSendIDEnum;
import com.hzs.device.infrature.tunnel.mysql.manager.MsgNumberManager;
import lombok.extern.slf4j.Slf4j;

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
public abstract class MsgOutServiceAbstract implements MsgOutServiceI{



    @Resource
    private MsgNumberManager msgNumberManager;


    public static List<Integer> phoneToArrays(String deviceId){

        char[] chars = deviceId.toCharArray();
        Integer[] phoneDatas = new Integer[6];
        for (int i = 0; i < chars.length; i++){

            int temp1 = (chars[i] - '0') << 4;
            int temp2 = chars[i++ + 1] - '0';
            phoneDatas[i / 2] = temp1 | temp2;

        }
        return Arrays.asList(phoneDatas);
    }

    public static void main(String[] args) {

        List<Integer> list = phoneToArrays("070350004282");

        log.info("MsgOutServiceAbstract list:{}", list);
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

        log.info("MsgOutServiceAbstract generateLastMsg msgSend:{},  newMsgSend:{}",
                msgSend, newMsgSend);
        return newMsgSend;
    }
}
