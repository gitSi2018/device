package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/1/29
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */

@Slf4j
@Service
public class ConnectMsgDeal extends MsgDealServiceAbstract{




    @Override
    public String getPoint() {

        return "0100";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("ConnectMsgDeal, msg:{}, context:{}", msg, context);

        ConnectMsg msgIn = convert(msg, context);
        connectionManager.addConnectMsgInByConnectId(msgIn.getChannelIdStr(), msgIn);
        return true;
    }

    //标志位 为 第一个字节。
    // 消息头为 12个字节（如果有分包处理，消息头消息属性的第13位分包标志位为0时，表示不分包，如果为1时，消息头为16个字节）
    //消息体
    //校验码 为1 byte
    //标志位
    private ConnectMsg convert(List<Integer> msg, ChannelHandlerContext context){

        ConnectMsg msgIn = new ConnectMsg();
        SocketChannel channel  = getChannel(context);
        msgIn.setChannel(channel);
        msgIn.setChannelIdStr(getChannelId(channel));

        msgIn.setDeviceId(generateDeviceId(msg));
        // 车牌号
        msgIn.setDeviceNo("");

        msgIn.setDeviceType("");
        msgIn.setColor("");
        return msgIn;
    }

    //
    private String generateDeviceId(List<Integer> msg){

        List<Integer> deviceIds = msg.subList(30, 37);
        StringBuilder deviceId = new StringBuilder();
        for(int i = 0; i < 7; i++){

            String tempStr = deviceIds.get(i) + "";
            int code = Integer.parseInt(tempStr, 16);
            log.info("device index:{}, code:{}", i, code);
            deviceId.append((char)code);
        }
        return deviceId.toString();
    }

}
