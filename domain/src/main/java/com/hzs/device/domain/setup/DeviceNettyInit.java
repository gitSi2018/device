package com.hzs.device.domain.setup;

import com.hzs.device.infrature.tunnel.netty.MsgInTunnelHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author: HongZhenSi
 * @date: 2021/1/28
 * @modifiedBy:
 * @description:
 * @version: 1.0
 */
@Slf4j
@Component
public class DeviceNettyInit {


    private static DeviceNettyInit deviceNettyInit;


    @PostConstruct
    public void init(){
        deviceNettyInit = this;
    }

//    @Resource
//    private MsgInTunnelHandler msgInTunnelHandler;

    private static final Executor executor = new ThreadPoolExecutor(
            1,
            1,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(1),
            new ThreadPoolExecutor.AbortPolicy());


    private static EventLoopGroup bossGroup;
    private static EventLoopGroup workerGroup;
    private static Channel channel;

    private static final int DEFAULT_PORT = 8888;

    public void start(int port) {

        //监听线程组，监听客户请求
        bossGroup = new NioEventLoopGroup();
        //处理客户端相关操作线程组，负责处理与客户端的数据通讯
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置监听组，线程组，初始化器
            serverBootstrap.
                    group(bossGroup, workerGroup).
                    channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new MsgInTunnelHandler());
                            log.info("Client :" + channel.remoteAddress() + "已经连接上");
                        }
                    });

            port = port == 0 ? DEFAULT_PORT : port;
            log.info("Netty Server start port:{}", port);
            //绑定端口号
            ChannelFuture f = serverBootstrap.bind(port).sync();
            //这里绑定端口启动后，会阻塞线程，也就是为什么要用线程池启动的原因
            channel = f.channel().closeFuture().sync().channel();

        } catch (Exception e) {

            log.error("NettyServerInit start,exception\n", e);

        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            stop();
            log.info("Netty Server close");
        }
    }


//    @Override
//    public void start() {
//
//        start(8080);
//    }

    public void stop() {
        if (channel != null) {
            log.info("Netty Server close");
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

//    @Override
//    public boolean isRunning() {
//        return false;
//    }
//
//    @Override
//    public int getPhase() {
//        return 2147483647;
//    }

    //    //  注意这里，组件启动时会执行run，这个注解是让线程异步执行，这样不影响主线程
//    public void init(final int port) {
//        executor.execute(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        start(port);
//                    }
//                }
//        );
//    }
//
//    public void init() {
//        executor.execute(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        start(DEFAULT_PORT);
//                    }
//                }
//        );
//    }
}
