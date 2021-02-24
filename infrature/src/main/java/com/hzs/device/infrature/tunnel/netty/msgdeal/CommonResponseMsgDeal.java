package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.CommonResponseMsg;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.common.utils.NumberFormatDeal;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author: HongZhenSi
 * @date: 2021/2/24
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Component
@Slf4j
public class CommonResponseMsgDeal extends MsgDealServiceAbstract{


    @Override
    public String getPoint() {
        return "0001";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("CommonResponseMsgDeal execute, msg:{}", msg);
        ConnectMsg connectMsg = getConnectMsg(context);
        if (connectMsg == null || StringUtils.isEmpty(connectMsg.getDeviceId())){

            log.warn("CommonResponseMsgDeal cannot get device by connect id. msg:{}", msg);
//            return false;
        }
        CommonResponseMsg responseMsg = convert(msg);

        log.info("CommonResponseMsgDeal execute, responseMsg:{}", responseMsg);
        return true;
    }

    private static CommonResponseMsg convert(List<Integer> msg){

        CommonResponseMsg responseMsg = new CommonResponseMsg();
        List<Integer> orderNum = msg.subList(13, 15);
        String[] orderNumStrs = NumberFormatDeal.sixteenFormat(orderNum);
        String orderNumStr = orderNumStrs[0] + orderNumStrs[1];
        responseMsg.setResponseOrderNum(orderNumStr);

        List<Integer> responseId = msg.subList(15, 17);
        String[] responseIdStrs = NumberFormatDeal.sixteenFormat(responseId);
        String point = responseIdStrs[0] + responseIdStrs[1];
        responseMsg.setResponseId(point);

        responseMsg.setResultCode(msg.get(17));
        return responseMsg;
    }

    public static void main(String[] args) {


        List<Integer> list = Arrays.asList(0x7e,
                0x00, 0x01  , 0x00, 0x05  ,
                0x99 , 0x99 , 0x99 , 0x99 , 0x91 , 0x18  , 0x00, 0x07   ,
                0x7f , 0xbc   , 0x81 , 0x03   , 0x00
                , 0xcb
                , 0x7e);
        CommonResponseMsg responseMsg = convert(list);
        log.info("responseMsg:{}", responseMsg);
    }
}
