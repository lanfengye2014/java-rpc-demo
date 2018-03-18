package com.dongnao.server;

import com.dongnao.common.message.NettyDecoder;
import com.dongnao.common.message.NettyEncode;
import com.dongnao.server.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public  class RemoteServer {
    public static  volatile  boolean start =false;
    private static ConcurrentHashMap<String,Object> serverImplMap=new ConcurrentHashMap<String, Object>();

    /***
     * 注册服务
     * @param serviceName
     * @param serviceImpl
     */
    public static void  addServices(String serviceName,Object serviceImpl){
        serverImplMap.put(serviceName,serviceImpl);
    }

    /**
     * 启动服务
     */
    static {
        bootstrap();
    }

    public static void bootstrap() {

        if (!start){
            synchronized (RemoteServer.class){
                if(!start){
                    start();
                }
            }
        }
    }

    //rpc server

    /***
     * 服务启动实现
     */
    public static void start(){
        //IO 线程组
        NioEventLoopGroup boosGroup =new NioEventLoopGroup(1);
        //业务线程组
        NioEventLoopGroup workGroup =new NioEventLoopGroup();

        ServerBootstrap bootstrap=new ServerBootstrap();
        bootstrap.group(boosGroup,workGroup).channel(NioServerSocketChannel.class).
                childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel channel) throws Exception {
                        //pipeline 处理请求
                        channel.pipeline().addLast(new NettyDecoder())
                                .addLast(new NettyEncode())
                                .addLast(new NettyServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG,1024)
                .option(ChannelOption.SO_KEEPALIVE,false);

        ChannelFuture future;
        try {
            future=bootstrap.bind(8081).sync();
            future.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if (channelFuture.isSuccess()){
                        start=true;
                        System.out.println("服务启动成功...");
                    }
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Object getOnlineServerImpl(String serviceName) {
        return serverImplMap.get(serviceName);
    }
}
