package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Service
public class HeatBeatDeal extends MsgDealServiceAbstract{


    @Resource
    private ConnectionManager connectionManager;


    @Override
    public String getPoint() {
        return "0002";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("heart beat, no msg need.");
        String channelId = getChannelId(context);
        ConnectMsg connectMsg = connectionManager.getConnectMsgInByConnectId(channelId);
        if (connectMsg == null){

            log.warn("HeatBeatDeal execute, channelId:{}, context:{}", channelId, context);
            return false;
        }

        return connectionManager.addHeatBeatMap(channelId, System.currentTimeMillis());
    }

}
