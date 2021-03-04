package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.CommonResponseMsg;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.MsgOutDeal;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import com.hzs.device.infrature.tunnel.netty.msgout.CommonResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
public class DeviceOfflineMsgDeal extends MsgDealServiceAbstract{


    @Resource
    private ConnectionManager connectionManager;

    @Resource
    private MsgOutDeal msgOutDeal;

    @Resource
    private CommonResponse commonResponse;


    @Override
    public String getPoint() {
        return "0003";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("DeviceOfflineMsgDeal execute, msg:{}", msg);
        String channelId = getChannelId(context);
        ConnectMsg connectMsg = connectionManager.getConnectMsgInByConnectId(channelId);
        if (connectMsg == null){

            log.warn("HeatBeatDeal execute, channelId:{}, context:{}", channelId, context);
        }else {
            msgOutDeal.sendToDevice(commonResponse.getMsgData(connectMsg.getDeviceId(), msg.get(11), msg.get(12), 0x00, 0x03, 0)
                    , connectMsg.getChannel());
        }
        log.info("DeviceOfflineMsgDeal connectMsg:{}", connectMsg);


        return false;
    }
}
