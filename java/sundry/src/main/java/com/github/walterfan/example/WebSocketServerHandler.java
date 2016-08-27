package com.github.walterfan.example;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by walter on 8/27/16.
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {




    private static Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);
    private int listenPort;

    public WebSocketServerHandler(int port) {
        listenPort = port;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if(msg instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) msg);
        }


    }

    /**
     * A thread-safe Set  Using ChannelGroup, you can categorize Channels into a meaningful group.
     * A closed Channel is automatically removed from the collection,
     */
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {  // (2)
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");

        channels.add(ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {  // (3)
        Channel incoming = ctx.channel();

        // Broadcast a message to multiple Channels
        channels.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 离开\n");

        // A closed Channel is automatically removed from ChannelGroup,
        // so there is no need to do "channels.remove(ctx.channel());"
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (5)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+" online");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception { // (6)
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+" offline");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Channel incoming = ctx.channel();
        System.out.println("SimpleChatClient:"+incoming.remoteAddress()+" exception");

        cause.printStackTrace();
        ctx.close();
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx,FullHttpRequest req, FullHttpResponse resp ) {
        if(resp.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(resp.getStatus().toString(), CharsetUtil.UTF_8);
            resp.content().writeBytes(buf);
            buf.release();
            resp.headers().set("Content-Length", resp.content().readableBytes());
        }

        boolean isKeepAlive = HttpHeaders.isKeepAlive(req);

        ChannelFuture f = ctx.channel().writeAndFlush(resp);
        if(resp.getStatus().code() != 200 || !isKeepAlive) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    private void handleHttpUpgrade(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:" + this.listenPort + "/websocket", null, false);
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(req);
        if(handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {

        if(req.getDecoderResult().isSuccess() && "Upgrade".equals(req.headers().get("Upgrade"))) {
            logger.info("Get HTTP Upgrade Reqeust");
            handleHttpUpgrade(ctx, req);
        } else {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
        }


    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        if(frame instanceof CloseWebSocketFrame) {
            ctx.close();
            return;
        }

        if(frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        if(frame instanceof TextWebSocketFrame) {
            String msg = ((TextWebSocketFrame)frame).text();
            Channel incoming = ctx.channel();
            for (Channel channel : channels) {
                if (channel != incoming){
                    channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + msg + "\n");
                } else {
                    channel.writeAndFlush("[you]" + msg + "\n");
                }
            }
        }

    }
}
