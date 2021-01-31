package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import io.netty.channel.ChannelHandlerContext;

import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
public interface MsgDealService {

    String getPoint();

    BaseMsgIn getData(List<Integer> msg);

    boolean execute(List<Integer> msg, ChannelHandlerContext context);
}
