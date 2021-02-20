package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/1/31
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Service
public class LocationQueryResponseMgsDeal extends GpsMsgDeal{


    @Override
    public String getPoint() {

        return "0201";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {

        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("LocationQueryResponseMgsDeal, msg:{}, context:{}", msg, context);
        SocketChannel channel  = getChannel(context);
        String channelId = getChannelId(channel);
        log.info("LocationQueryResponseMgsDeal channelId:{}, msg:{}", channelId, msg);

        return super.execute(msg, context);
    }
}
