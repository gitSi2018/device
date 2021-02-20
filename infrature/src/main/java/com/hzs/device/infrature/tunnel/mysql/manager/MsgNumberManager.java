package com.hzs.device.infrature.tunnel.mysql.manager;

import com.hzs.device.common.enums.MsgSendIDEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
/**
 * @author: HongZhenSi
 * @date: 2021/2/20
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class MsgNumberManager {


    public List<Integer> getMsgNum(MsgSendIDEnum msgSendIDEnum){

        return new ArrayList<>(2);
    }
}
