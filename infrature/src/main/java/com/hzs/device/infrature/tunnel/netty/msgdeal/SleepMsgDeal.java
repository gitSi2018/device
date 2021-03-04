package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import com.hzs.device.infrature.tunnel.netty.msgout.CommonResponse;
import com.hzs.device.infrature.tunnel.netty.msgout.SleepMsgResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/3/4
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class SleepMsgDeal extends ConnectMsgDeal{



    @Resource
    private SleepMsgResponse sleepMsgResponse;

    @Resource
    private ConnectionManager connectionManager;

    @Override
    public String getPoint() {

        return "0105";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("SleepMsgDeal execute, msg:{}, context:{}", msg, context);

        ConnectMsg connectMsg = convert(msg, context);
        sendToDevice(sleepMsgResponse.getMsgData(connectMsg.getDeviceId(), null), connectMsg.getChannel());
        return true;
    }
}
