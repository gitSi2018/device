package com.hzs.device.infrature.tunnel.netty.msgdeal;

import com.hzs.device.common.constant.ResultCodeConstant;
import com.hzs.device.common.msgin.BaseMsgIn;
import com.hzs.device.common.msgin.msg.ConnectMsg;
import com.hzs.device.infrature.tunnel.netty.MsgOutDeal;
import com.hzs.device.infrature.tunnel.netty.NettySentMsgToDevice;
import com.hzs.device.infrature.tunnel.netty.manage.ConnectionManager;
import com.hzs.device.infrature.tunnel.netty.msgout.ConnectResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class ConnectMsgDeal extends MsgDealServiceAbstract{



    @Resource
    private ConnectResponse connectResponse;

    @Resource
    private MsgOutDeal msgOutDeal;


    @Resource
    private ConnectionManager connectionManager;

    @Override
    public String getPoint() {

        return "0100";
    }

    @Override
    public BaseMsgIn getData(List<Integer> msg) {
        return null;
    }

    @Override
    public boolean execute(List<Integer> msg, ChannelHandlerContext context) {

        log.info("ConnectMsgDeal, msg:{}, context:{}", msg, context);

        ConnectMsg msgIn = convert(msg, context);
        connectionManager.addConnectMsgInByConnectId(msgIn.getChannelIdStr(), msgIn);
        Integer[] orderNum = msgIn.getMsgOrderNum();
        List<Integer>  responseData = connectResponse.getMsgData(msgIn.getDeviceId(),
                orderNum[0], orderNum[1], 0, 65);
        msgOutDeal.sendToDevice(responseData, msgIn.getChannel());
        return true;
    }

    //标志位 为 第一个字节。
    // 消息头为 12个字节（如果有分包处理，消息头消息属性的第13位分包标志位为0时，表示不分包，如果为1时，消息头为16个字节）
    //消息体
    //校验码 为1 byte
    //标志位
    ConnectMsg convert(List<Integer> msg, ChannelHandlerContext context){

        ConnectMsg msgIn = new ConnectMsg();
        SocketChannel channel  = getChannel(context);
        msgIn.setChannel(channel);
        msgIn.setChannelIdStr(getChannelId(channel));

        msgIn.setDeviceId(generateDeviceId(msg));

        msgIn.setMsgOrderNum(connectOrderNum(msg));

        return msgIn;
    }

    private Integer[] connectOrderNum(List<Integer> msg){

        Integer[] orderNum = new Integer[2];
        orderNum[0] = msg.get(11);
        orderNum[1] = msg.get(12);
        return orderNum;
    }

    public ConnectMsg getConnectMsgInByConnectId(String connectId){

        return connectionManager.getConnectMsgInByConnectId(connectId);
    }


    public int sendToDevice(List<Integer> newMsgSend, Channel channel){

        // 拼接头尾 求校验和

//        List<Integer> newMsgSend = new ArrayList<>(msgSend.size() + 3);
//        newMsgSend.add(126);
//        newMsgSend.addAll(msgSend);
//        int checkSum = calculateCheckSum(msgSend);
//        newMsgSend.addAll(Arrays.asList(checkSum, 126));

        //转成byte的数组
        byte[] msg = new byte[newMsgSend.size()];
        for (int i = 0; i < newMsgSend.size(); i++){
            msg[i] = newMsgSend.get(i).byteValue();
        }

        log.info("MsgOutDeal sendToDevice, msg:{}\nchannel:{}",
                msg, channel);
        return NettySentMsgToDevice.sentToClient(channel, msg) ?
                ResultCodeConstant.SEND_ORDER_SUCCEED : ResultCodeConstant.CHANNEL_IS_INACTIVE;
    }


    //
    private String generateDeviceId(List<Integer> msg){

        List<Integer> deviceIds = msg.subList(5, 11);
        StringBuilder deviceIdStr = new StringBuilder();
        for(int i = 0; i < 6; i++){

            String code = Integer.toString(deviceIds.get(i), 16);
            if (code.length() < 2){
                code = "0" + code;
            }
            log.info("device index:{}, code:{}", i, code);
            deviceIdStr.append(code);
        }
        return deviceIdStr.toString();
    }

    public static void main(String[] args) {


        int[] a = {126,1,0,0,45,7,3,80,0,66,130,0,0,0,42,8,82,49,50,51,52,53,51,53,51,51,55,48,51,53,0,0,0,0,0,0,0,0,0,0,0,0,48,48,48,52,50,56,50,255,212,193,66,56,56,56,56,56,82,126};
        log.info("char 0:{}", (char)0 + "");

        int[]  b  = {7, 3, 80, 0, 66, 130};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < b.length; i++){

            log.info("index{}:{}", i, Integer.toString(b[i], 16));
//            builder.append(String.format("%08d", Integer.parseInt(Integer.toBinaryString(b[i]))));
        }
        log.info("phone:{}", builder.toString());

        int[] c = {0x39, 0x39, 0x39 , 0x39 , 0x31 , 0x31 , 0x38};
        for (int i = 0 ; i < c.length; i++){

            log.info("c:{}", c);
        }
    }

}
