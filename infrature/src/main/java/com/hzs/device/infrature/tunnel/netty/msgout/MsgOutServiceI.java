package com.hzs.device.infrature.tunnel.netty.msgout;

import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/2/20
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
public interface MsgOutServiceI {

    String point();

    List<Integer> getMsgId();

    List<Integer> getMsgData(String deviceId, Integer... sendData);


}
