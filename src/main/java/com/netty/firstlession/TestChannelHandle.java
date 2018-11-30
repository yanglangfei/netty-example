package com.netty.firstlession;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * XXX company
 * Copyright (c) 2018, xxx All Rights Reserved.
 *
 * @author yanglf
 * @date 2018/11/30/030
 * @descriptipon
 */
public class TestChannelHandle extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
       if(o instanceof HttpRequest){
           HttpRequest request= (HttpRequest) o;
           System.out.println("Request Method:"+request.method().name());
           String uri = request.uri();
           if(uri.endsWith("favicon.ico")){
               System.out.println("request url:"+uri);
               return;
           }
           ByteBuf byteBuf= Unpooled.copiedBuffer("Hello,Netty Server", CharsetUtil.UTF_8);
           FullHttpResponse response=new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,byteBuf);
           response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
           response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
           channelHandlerContext.writeAndFlush(response);
       }
    }
}
