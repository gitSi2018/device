package com.hzs.device.common.constant;

/**
 * 返回码
 */
public class ResultCodeConstant {

    /**
     * 命令发送成功
     */
    public static final int SEND_ORDER_SUCCEED = 0;

    /**
     * 找不到对应的channel
     */
    public static final int CANNOT_FIND_CHANNEL = 1;

    /**
     * channel处于inactive状态
     */
    public static final int CHANNEL_IS_INACTIVE =  2;

    /**
     * 参数错误
     */
    public static final int PARAMETER_ERROR = 3;

    public static final int COMMON_ERROR = 4;
}
