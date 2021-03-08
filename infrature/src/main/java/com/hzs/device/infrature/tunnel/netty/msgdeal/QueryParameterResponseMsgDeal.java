package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.msgout.CommonResponse;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/3/8
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class QueryParameterResponseMsgDeal extends ConnectMsgDeal{


    @Resource
    private CommonResponse commonResponse;

    @Override
    public String getPoint() {
        return "0104";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("QueryParameterResponseMsgDeal, msg:{}, context:{}", msg, context);

        ConnectMsg connectMsg = convert(msg, context);
        if (connectMsg == null || StringUtils.isEmpty(connectMsg.getDeviceId())){

            log.warn("QueryParameterResponseMsgDeal cannot get device by connect id. msg:{}", msg);
            return false;
        }

        sendToDevice(commonResponse.getMsgData(connectMsg.getDeviceId(), msg.get(11), msg.get(12), 0x01, 0x04, 0)
                , connectMsg.getChannel());
        return true;
    }
}
