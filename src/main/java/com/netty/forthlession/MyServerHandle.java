package com.netty.forthlession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * XXX company
 * Copyright (c) 2018, xxx All Rights Reserved.
 *
 * @author yanglf
 * @date 2018/11/30/030
 * @descriptipon
 */
public class MyServerHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            String eventType = null;
            switch (idleStateEvent.state()) {
                case READER_IDLE:
                    // 读空闲操作
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    // 写空闲操作
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    //  读写空闲操作
                    eventType = "读写空闲";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + " 超时事件:" + eventType + "\n");
            ctx.channel().close();
        }
    }
}
