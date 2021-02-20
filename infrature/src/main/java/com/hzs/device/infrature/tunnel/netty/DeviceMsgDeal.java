package com.hzs.device.infrature.tunnel.netty;


import com.hzs.device.infrature.common.CommonConstant;
import com.hzs.device.infrature.common.utils.NumberFormatDeal;
import com.hzs.device.infrature.tunnel.netty.msgdeal.MsgDealService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class DeviceMsgDeal {


    private static final Map<String, MsgDealService> serviceMap = new ConcurrentHashMap<>();

    @Autowired
    public DeviceMsgDeal(List<MsgDealService> services){

        for (int i = 0; i < services.size(); i++){
            MsgDealService s = services.get(i);
            serviceMap.put(s.getPoint(), s);
        }
    }


    public boolean isLegalMsg(List<Integer> msg){

        if (msg == null){
            log.warn("SmartMsgManager isLegalMsg false. msg is empty. msg:{}", msg);
            return false;
        }
        if (CommonConstant.FIRST_HEAD != msg.get(0)){
            log.warn("SmartMsgManager isLegalMsg false. msg head illegal. msg:{}", msg);
            return false;
        }
//        // 求校验和
        return true;
    }

    public void dealMsg(List<Integer> msg, ChannelHandlerContext context){

        MsgDealService service = getService(msg);
        if(service == null){

            log.warn("SmartMsgDeal dealMsg, no service found. msg:{}", msg);
            return;
        }
        service.execute(msg, context);

    }

    private MsgDealService getService(List<Integer> msg){

        String[] sixteenStrs = NumberFormatDeal.sixteenFormat(msg);
        String point = sixteenStrs[1] + sixteenStrs[2];
        log.info("point:{}", point);
        return serviceMap.get(point);
    }
}
