package com.netty.secondlession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.UUID;

/**
 * XXX company
 * Copyright (c) 2018, xxx All Rights Reserved.
 *
 * @author yanglf
 * @date 2018/11/30/030
 * @descriptipon
 */
public class MyServerHandle extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接收到客户端【"+ctx.channel().remoteAddress()+"】消息:【"+msg+"】");
        ctx.writeAndFlush("SEND Message:"+ UUID.randomUUID().toString());
    }

}
