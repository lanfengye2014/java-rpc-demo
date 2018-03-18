package com.dongnao.common.message;

import com.dongnao.common.util.Serialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

/**
 * Created by Lanfengye on 2017-3-19.
 */
public class NettyDecoder extends ByteToMessageDecoder {

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if(byteBuf.readableBytes()<4){
            return ;
        }
        byteBuf.markReaderIndex();
        int messageLength=byteBuf.readInt();
        if(messageLength<0){
            channelHandlerContext.close();
        }
        if (byteBuf.readableBytes() < messageLength) {
            byteBuf.resetReaderIndex();
            return;
        } else {
            byte[] messageBody = new byte[messageLength];
            byteBuf.readBytes(messageBody);

            try {
                Object obj = Serialize.deserialize(messageBody);
                list.add(obj);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
