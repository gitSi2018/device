package com.hzs.device.infrature.tunnel.netty;

import com.hzs.device.common.constant.ResultCodeConstant;
import com.hzs.device.common.enums.MsgSendIDEnum;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import com.hzs.device.infrature.tunnel.netty.msgout.MsgOutServiceI;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author: HongZhenSi
 * @date: 2021/2/20
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */

@Slf4j
@Component
public class MsgOutDeal {


    @Resource
    private ConnectionManager connectionManager;

    private static final Map<String, MsgOutServiceI> serviceIMap = new HashMap<String, MsgOutServiceI>();

    @Autowired
    public MsgOutDeal(List<MsgOutServiceI> msgOutServiceIS){

        for (MsgOutServiceI msgOutServiceI : msgOutServiceIS){
            serviceIMap.put(msgOutServiceI.point(), msgOutServiceI);
        }
    }

    public int sendMsg(String deviceId, MsgSendIDEnum sendIDEnum, Integer... data){

        ConnectMsg connectMsg = connectionManager.getConnectMsgInByDeviceId(deviceId);
        if (connectMsg == null || connectMsg.getChannel() == null){
            log.warn("MsgOutDeal sendMsg, not found device channel. deviceId:{}",
                    deviceId);
            return ResultCodeConstant.CANNOT_FIND_CHANNEL;
        }

        MsgOutServiceI msgOutServiceI = serviceIMap.get(sendIDEnum.getMsgKey());
        if (msgOutServiceI == null){

            log.warn("MsgOutDeal sendMsg, no service found. deviceId:{}, sendIDEnum:{}, data:{}",
                    deviceId, sendIDEnum, data);
            return ResultCodeConstant.COMMON_ERROR;

        }
        List<Integer> sendData = msgOutServiceI.getMsgData(deviceId, data);
        log.warn("MsgOutDeal sendMsg. deviceId:{}, sendIDEnum:{}, data:{}, sendData:{}",
                deviceId, sendIDEnum, data, sendData);
        return sendToDevice(sendData, connectMsg.getChannel());

    }

    private int sendToDevice(List<Integer> newMsgSend, Channel channel){

        // 拼接头尾 求校验和

//        List<Integer> newMsgSend = new ArrayList<>(msgSend.size() + 3);
//        newMsgSend.add(126);
//        newMsgSend.addAll(msgSend);
//        int checkSum = calculateCheckSum(msgSend);
//        newMsgSend.addAll(Arrays.asList(checkSum, 126));

        //转成byte的数组
        byte[] msg = new byte[newMsgSend.size()];
        for (int i = 0; i < newMsgSend.size(); i++){
            msg[i] = newMsgSend.get(i).byteValue();
        }

        log.info("MsgOutDeal sendToDevice, msg:{}\nchannel:{}",
                msg, channel);
        return NettySentMsgToDevice.sentToClient(channel, msg) ?
                ResultCodeConstant.SEND_ORDER_SUCCEED : ResultCodeConstant.CHANNEL_IS_INACTIVE;
    }

}
