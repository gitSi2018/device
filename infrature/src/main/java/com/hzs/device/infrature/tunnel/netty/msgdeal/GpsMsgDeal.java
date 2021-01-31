package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import io.netty.channel.ChannelHandlerContext;
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
public class GpsMsgDeal extends MsgDealServiceAbstract{


    @Override
    public String getPoint() {
        return "0200";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {

        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("GpsMsgDeal, msg:{}, context:{}", msg, context);
        return true;
    }
}
