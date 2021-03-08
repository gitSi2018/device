package com.hzs.device.infrature.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
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
            String hex = Integer.toHexString(data & 0xFFFFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            msgs[i] = hex;
        }
        log.info("sixteenFormat msgs:{}", msgs);
        return msgs;
    }

    public static void printToSixteenFormat(List<Integer> datas){

        StringBuilder msgs = new StringBuilder();
        for (int i = 0; i < datas.size(); i++){

            int data =  datas.get(i);
            String hex = Integer.toHexString(data & 0xFFFFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            msgs.append(hex).append(" ");
        }
        log.info("printToSixteenFormat sixteenFormat msgs:{}", msgs);
    }

    public static String[] toBinaryString(List<Integer> datas, int radix){

        String[] strings = new String[datas.size()];
        for (int i = 0; i < datas.size(); i++){
            strings[i] = toBinaryString(datas.get(i), 16);
        }
        return strings;
    }

    public static String toBinaryString(Integer data, int radix){

        int temp = Integer.parseInt(data + "", radix );
        String string = String.format(Integer.toBinaryString(temp));
        return string;
    }

}
