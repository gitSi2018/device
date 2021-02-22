package com.hzs.device.infrature.tunnel.netty;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hzs.device.common.utils.SpringBeanUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */

@Slf4j
//@Component
//@ChannelHandler.Sharable
public class MsgInTunnelHandler extends ChannelInboundHandlerAdapter {


//    private static final Logger log = LoggerFactory.getLogger(MsgOutTunnel.class);

    private static final ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("deal-msg-from-device").build();

    private static final Executor executor = new ThreadPoolExecutor(
            10,
            30,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(64),
            namedThreadFactory,
            new ThreadPoolExecutor.DiscardOldestPolicy()
    );

    public MsgInTunnelHandler(){

    }

    private static DeviceMsgDeal smartMsgDeal;

//    @PostConstruct
    static {
        smartMsgDeal = SpringBeanUtils.getBean(DeviceMsgDeal.class);
    }



    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {

        log.info("ctx:{}, msg:{}", ctx, msg);
        ByteBuf in = (ByteBuf) msg;
        final List<Integer> code = new ArrayList<>(10);

        try {

            while (in.isReadable()) { // (1)
                byte data = in.readByte();
                int unsigned = data & 0xFF;
                code.add(unsigned);
            }

            final ChannelHandlerContext context = ctx;

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    if (smartMsgDeal.isLegalMsg(code)){
                        // 更新对应的设备状态

                        smartMsgDeal.dealMsg(code, context);
                    }else {
                        try {

                            log.warn("MsgInTunnelHandler channelRead, illegal connection. will be close. code:{}",code);
                            ctx.close();
                        }catch (Throwable t){

                            log.error("MsgInTunnelHandler channelRead close ctx failed.", t);
                        }
                    }
                }
            });

        } finally {
            ReferenceCountUtil.release(msg); // (2)
            log.info("\nreceive code:{}", JSON.toJSON(code));
        }

    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("ctx:{} \n", ctx, cause);
        ctx.close();
//        super.exceptionCaught(ctx, cause);
    }
}
