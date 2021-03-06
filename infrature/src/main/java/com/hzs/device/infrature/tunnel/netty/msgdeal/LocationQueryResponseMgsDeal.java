package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.msgout.CommonResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private CommonResponse commonResponse;


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
        ConnectMsg connectMsg = convert(msg, context);
        log.info("LocationQueryResponseMgsDeal channelId:{}, msg:{}, connectMsg:{}",
                channelId, msg, connectMsg);
        if (connectMsg != null){
            sendToDevice(commonResponse.getMsgData(connectMsg.getDeviceId(), msg.get(11), msg.get(12), 0x02, 0x01, 0)
                    , connectMsg.getChannel());
        }
        return super.execute(msg, context);
    }
}
