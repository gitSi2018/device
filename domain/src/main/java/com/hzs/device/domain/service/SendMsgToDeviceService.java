package com.hzs.device.domain.service;

import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.common.response.ErrorEnum;
import com.hzs.device.common.response.Result;
import com.hzs.device.infrature.tunnel.netty.MsgOutTunnel;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class SendMsgToDeviceService {



    @Resource
    private ConnectionManager connectionManager;

    public Result sendQueryGpsLocation(String deviceId){

        log.info("SendMsgToDeviceService sendQueryGpsLocation. deviceId:{}", deviceId);
        ConnectMsg connectMsg = connectionManager.getConnectMsgInByDeviceId(deviceId);
        if (connectMsg == null){

            log.warn("SendMsgToDeviceService sendQueryGpsLocation, cannot found device. deviceId:{}", deviceId);
            return Result.failed(ErrorEnum.NO_DEVICE_FOUND);
        }
        SocketChannel channel = connectMsg.getChannel();
        byte[] msg = new byte[10];
        return MsgOutTunnel.sentToClient(channel, msg)  ?  Result.succeed() : Result.failed(ErrorEnum.SEND_MSG_TO_DEVICE_FAILED);
    }
}
