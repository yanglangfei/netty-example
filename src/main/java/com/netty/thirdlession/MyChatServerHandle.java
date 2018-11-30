package com.netty.thirdlession;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * XXX company
 * Copyright (c) 2018, xxx All Rights Reserved.
 *
 * @author yanglf
 * @date 2018/11/30/030
 * @descriptipon
 */
public class MyChatServerHandle extends SimpleChannelInboundHandler<String> {


    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 先广播所有客户端  在加入新客户端   避免向新客户端广播
        channelGroup.writeAndFlush("Server----"+channel.remoteAddress()+"加入\n");
        channelGroup.add(channel);

    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("Server----"+channel.remoteAddress()+"离开\n");
        // 客户端断开连接时  channelGroup 会自动移除当前channel
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【"+channel.remoteAddress()+"】上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println("【"+channel.remoteAddress()+"】下线");
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
              if(ch!=channel){
                  ch.writeAndFlush(ch.remoteAddress()+"发送消息:"+msg+"\n");
              }else{
                  ch.writeAndFlush("【自己】:"+msg+"\n");
              }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        cause.printStackTrace();
        ctx.close();
    }
}
