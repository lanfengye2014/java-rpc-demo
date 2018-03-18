package com.dongnao.client;

import com.dongnao.common.entity.RemoteRequest;
import com.dongnao.common.entity.RemoteRespone;
import com.dongnao.common.message.NettyDecoder;
import com.dongnao.common.message.NettyEncode;
import com.google.common.util.concurrent.SettableFuture;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class RemoteClient {

    private ProviderInfo providerInfo;
    public RemoteClient(ProviderInfo providerInfo){
        this.providerInfo=providerInfo;
    }

    public RemoteRespone send(RemoteRequest remoteRequest) throws Exception{
        final SettableFuture<RemoteRespone> settableFuture = SettableFuture.create();
        NioEventLoopGroup workGroup=new NioEventLoopGroup();
        Bootstrap bootstrap=new Bootstrap();
        bootstrap.group(workGroup).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //pipeline 处理请求
                        socketChannel.pipeline()
                                .addLast(new NettyDecoder())
                                .addLast(new NettyEncode())
                                .addLast(new NettyClientHandler(settableFuture));
                    }
                });
        ChannelFuture f=bootstrap.connect(providerInfo.getAddress(),providerInfo.getPort())
                .sync();//连接服务器
        f.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.isSuccess()){
                            System.out.println("客户端连接服务端成功...");
                        }
                    }
                });

        ChannelFuture resultF= f.channel().writeAndFlush(remoteRequest);
        resultF.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(channelFuture.isSuccess()){
                    System.out.println("发送数据成功...");
                }
            }
        });
        return settableFuture.get();

    }



}
