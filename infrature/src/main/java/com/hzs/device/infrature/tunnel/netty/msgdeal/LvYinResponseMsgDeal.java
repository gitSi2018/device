package com.hzs.device.infrature.tunnel.netty.msgdeal;
/**
 * @author: HongZhenSi
 * @date: 2021/3/4
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.msgout.CommonResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Component
public class LvYinResponseMsgDeal extends ConnectMsgDeal {



    @Resource
    private CommonResponse commonResponse;

    @Override
    public String getPoint() {
        return "0116";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("LvYinResponseMsgDeal execute, msg:{}", msg);
        ConnectMsg connectMsg = convert(msg, context);


        sendToDevice(commonResponse.getMsgData(connectMsg.getDeviceId(), msg.get(11), msg.get(12), 0x01, 0x16, 0)
                , connectMsg.getChannel());
        return true;
    }
}
