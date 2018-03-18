package com.dongnao.client;

import com.dongnao.common.entity.RemoteRespone;
import com.google.common.util.concurrent.SettableFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RemoteRespone> {

    private SettableFuture<RemoteRespone> sf;
    public  NettyClientHandler(SettableFuture<RemoteRespone> sf){
        this.sf=sf;
    }
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RemoteRespone remoteRespone) throws Exception {
        sf.set(remoteRespone);
    }
}
