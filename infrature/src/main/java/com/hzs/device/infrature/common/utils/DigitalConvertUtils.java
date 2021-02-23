package com.hzs.device.infrature.common.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DigitalConvertUtils {

    /**
     *  将指定的数组转换为二进制（每个数字转换成二进制之后，不足8位高位补0）之后拼接成新的二进制并转换成一个十进制的数字.
     *  注意每个数字的范围为 0 ~ 255
     * @param radix 这些数组是的进制
     * @param digital 需要转换的数组
     * @return
     */
    public static long convert(int radix, Integer... digital){

        if (radix != 10){
            for (int i = 0; i < digital.length; i++){

                digital[i] = Integer.parseInt(digital[i] + "", radix);
            }
        }
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < digital.length; i++){

            temp.append(String.format("%08d", Integer.parseInt(Integer.toBinaryString(digital[i]))));
        }
        System.out.println(temp.toString());
        return Long.parseLong(temp.toString(), 2);
    }

    public static void main(String[] args) {


        Integer[] test =
             //   {0x07, 0x03, 0x50, 0x00, 0x42, 0x82};
                {0, 42};

        System.out.println(convert(10, test) + "");

        Integer num = Integer.valueOf("56", 10);
        String numStr = Integer.toBinaryString(num);
        log.info("numStr:{}", numStr);
        num = num & Integer.valueOf("1111111111", 2);
        int num1 = num & Integer.valueOf("1111", 2);
        num = num >> 4;
        int num2 = num & Integer.valueOf("1111", 2);
        log.info("num1:{}, num2:{}", num1, num2);

    }

//
//    1000111100001101001001111011000
//    1200002008 71:134:147:216
//
//    1000000008 59:154:202:8
//
//    1000000006 59:154:202:6

//    public static void main(String[] args) {
//
//        System.out.println(Integer.toBinaryString(1200002008));
//        System.out.println(convert(10, 71, 134, 147, 216));
//
//        System.out.println(Integer.toBinaryString(1200002008));
//        System.out.println(convert(10, 59, 154, 202, 8));
//
//        System.out.println(Integer.toBinaryString(1200002006));
//        System.out.println(convert(10, 59, 154, 202, 6));
//    }
}
