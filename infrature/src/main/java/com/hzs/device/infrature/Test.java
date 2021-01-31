package com.hzs.device.infrature;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
public class Test {


    public static void main(String[] args) {

        Integer[] tests = {126, 1, 0, 0, 45, 7, 3, 80, 0, 66, 130, 0, 0, 0, 42, 8,
                82, 49, 50, 51, 52, 53, 51, 53, 51, 51, 55, 48, 51,
                53, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 48, 48, 48, 52,
                50, 56, 50, 255, 212, 193, 66, 56, 56, 56, 56, 56,
                82, 126};

        Integer[] test2 = {126, 0, 2, 0, 0, 7, 3, 80, 0, 66, 130, 0, 1, 151, 126};

        ArrayList<Integer[]> list = new ArrayList<>();
        list.add(tests);
        list.add(test2);

        for (Integer[] integers : list){
            msgConvert(integers);
        }

        String test = "352396";
        log.info("test:{}", Integer.valueOf(test, 16));
        test();
    }

    public static void msgConvert(Integer[] tests){

        StringBuilder msg = new StringBuilder();
        for (int test : tests){

            String hex = Integer.toHexString(test & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            msg.append(hex).append(" ");
        }
        log.info("msg:{}", msg);
    }

    public static String getMobileNo(int[] mobileArray)
    {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++)
        {
            int bb= mobileArray[i];
            sb.append((((byte)bb >> 4)));
            sb.append((((byte)bb << 4) >> 4));
        }
        return sb.toString();
    }

    public static void test(){

        int[] test = {0x07, 0x03, 0x50, 0x00, 0x42, 0x82};
        log.info("phone:{}", getMobileNo(test));
    }
}
