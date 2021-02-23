package com.hzs.device.infrature.tunnel.netty;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.nio.CharBuffer;
import java.util.concurrent.*;


@Slf4j
public class NettySentMsgToDevice {



    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
            .setNameFormat("send-msg-to-device").build();


    private static final Executor executor = new ThreadPoolExecutor(
            5,
            10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(64),
            namedThreadFactory,
            new ThreadPoolExecutor.DiscardOldestPolicy()
            );



    public static boolean sentToClient(final Channel channel, byte[] msg) {

        try {
            final ByteBuf buf = Unpooled.copiedBuffer(msg);
            log.info("NettySentMsgToDevice sentToClient, ctx:{} buf:{}", channel.isActive(), buf);
            if (!channel.isActive()){
                log.warn("NettySentMsgToDevice sentToClient, channel if not active.");
                return false;
            }
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    channel.writeAndFlush(buf.duplicate()).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            if (future.isSuccess()) {
                                log.info("sentToClient complete.");
                            }
                        }
                    });
                }
            });
            log.info("NettySentMsgToDevice sentToClient succeed, ctx:{} buf:{}", channel.isActive(), buf);
            return true;
        }catch (Throwable t){

            log.error("sentToClient error. msg:{}, channel:{}",
                    msg, channel, t);
            return false;
        }
    }
    public void sentToClient(final ChannelHandlerContext ctx, byte[] msg) {


        final ByteBuf buf = Unpooled.copiedBuffer(msg);
        log.info("NettySentMsgToDevice sentToClient, ctx:{} buf:{}", ctx.channel().isActive(), buf);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                ctx.channel().writeAndFlush(buf.duplicate());
            }
        });
    }

}
