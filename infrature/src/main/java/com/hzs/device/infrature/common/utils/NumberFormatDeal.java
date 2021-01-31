package com.hzs.device.infrature.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
public class NumberFormatDeal {

    public static String[] sixteenFormat(List<Integer> datas){

        String[] msgs = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++){

            int data =  datas.get(i);
            String hex = Integer.toHexString(data & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            msgs[i] = hex;
        }
        log.info("sixteenFormat msgs:{}", msgs);
        return msgs;
    }
}
