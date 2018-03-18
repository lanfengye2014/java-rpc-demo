package com.dongnao.server.handler;

import com.dongnao.common.entity.RemoteRequest;
import com.dongnao.common.entity.RemoteRespone;
import com.dongnao.server.RemoteServer;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RemoteRequest> {

    public NettyServerHandler(){

    }
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RemoteRequest remoteRequest) throws Exception {
        Object serviceImpl= RemoteServer.getOnlineServerImpl(remoteRequest.getServiceName());
        if(serviceImpl!=null){
            Class<?> interfaceType= serviceImpl.getClass();
            Method method=interfaceType.getMethod(remoteRequest.getMethodName(),remoteRequest.getParameterTypes());
            Object result=method.invoke(serviceImpl,remoteRequest.getArguments());
            Thread.sleep(1000);

            RemoteRespone respone=new RemoteRespone();
            respone.setRequestId(remoteRequest.getRequestId());
            respone.setResponeValue(result);
            channelHandlerContext.writeAndFlush(respone).addListener(ChannelFutureListener.CLOSE)
                    .addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isSuccess()){
                                System.out.println("服务响应完毕...");
                            }
                        }
                    });


        }else{
            RemoteRespone respone=new RemoteRespone();
            respone.setRequestId(remoteRequest.getRequestId());
            respone.setResponeValue("调用个失败");
            channelHandlerContext.writeAndFlush(respone).addListener(ChannelFutureListener.CLOSE)
                    .addListener(new ChannelFutureListener() {
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (channelFuture.isSuccess()){
                                System.out.println("服务响应完毕...");
                            }
                        }
                    });
        }
        channelHandlerContext.close();
    }
}
