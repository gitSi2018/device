package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.enums.DataEncryptEnum;
import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.MsgAttribute;
import com.hzs.device.common.msgin.MsgHead;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.common.utils.NumberFormatDeal;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

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
public abstract class MsgDealServiceAbstract implements MsgDealService{


    @Resource
    protected ConnectionManager connectionManager;

    public SocketChannel getChannel(ChannelHandlerContext context){

        return (SocketChannel) context.channel();
    }

    public String getChannelId(SocketChannel channel){

        String channelId = channel.id().asLongText();
        log.info("MsgDealServiceAbstract getChannelId, channelId:{}", channelId);
        return channelId;
    }

    public String getChannelId(ChannelHandlerContext context){

        return getChannelId(getChannel(context));
    }

    public ConnectMsg getConnectMsg(ChannelHandlerContext context){

        return connectionManager.getConnectMsgInByDeviceId(getChannelId(context));
    }

    public BaseMsgIn dealHead(List<Integer> msg, BaseMsgIn msgIn){

        String[] sixteenStrs = NumberFormatDeal.sixteenFormat(msg);
        String msgId = sixteenStrs[1] + sixteenStrs[2];
        MsgHead msgHead = new MsgHead();
        msgHead.setMsgId(msgId);
        String msgNo = sixteenStrs[11] + sixteenStrs[12];
        msgHead.setMsgNo(msgNo);
        msgHead.setMsgAttribute(generateMsgAttribute(msg));

        msgIn.setMsgHead(msgHead);
        return msgIn;
    }

    private MsgAttribute generateMsgAttribute(List<Integer> msg){

        MsgAttribute attribute = new MsgAttribute();
        Integer attrNumber1 = msg.get(3);
        Integer attrNumber2 = msg.get(4);

        String attrStr1 = NumberFormatDeal.toBinaryString(attrNumber1, 16);
        String attrStr2 = NumberFormatDeal.toBinaryString(attrNumber2, 16);

        long data = Long.valueOf(attrStr1 + attrStr2, 2);
        boolean fenBao = (data & Long.valueOf("0010000000000000", 2)) == 0;
        attribute.setFenBao(fenBao);
        long length = data & Long.valueOf("0000001111111111", 2);
        attribute.setMsgLength((int) length);
        attribute.setEncryptEnum(DataEncryptEnum.NO_ENCRYPT);
        return attribute;
    }
}
