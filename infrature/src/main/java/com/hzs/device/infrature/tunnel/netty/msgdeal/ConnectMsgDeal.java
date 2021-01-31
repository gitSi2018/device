package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsgIn;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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



    private static final Map<String, ConnectMsgIn> deviceIdMap = new HashMap<>();
    private static final Map<String, ConnectMsgDeal> connMap = new HashMap<>();

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

        return true;
    }
}
