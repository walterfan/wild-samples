package com.github.walterfan.tpproxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.SocketChannel;

class TcpProxyChannelHandler extends ChannelInitializer<SocketChannel> {
	private String remoteHost;
	private int remotePort;
	
	public TcpProxyChannelHandler( String remoteHost, int remotePort) {
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
	}
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {

		ch.pipeline().addLast(new TcpProxyHandler(remoteHost, remotePort));
		
	}
	
}

public class DefaultTcpServer implements TransportServer {
	private int listenPort;
	private String remoteHost;
	private int remotePort;
	
	public DefaultTcpServer(int localPort, String remoteHost, int remotePort) {
		this.listenPort = localPort;
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
	}
	
	public int start() throws TransportException {
		EventLoopGroup connGroup = new NioEventLoopGroup();
		EventLoopGroup readGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap booter = new ServerBootstrap();
			booter.group(connGroup, readGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childHandler(new TcpProxyChannelHandler(remoteHost, remotePort));
			
			ChannelFuture future = booter.bind(listenPort).sync();
			future.channel().closeFuture().sync();
		} catch(Exception e) {
			
		} finally {
			connGroup.shutdownGracefully();
			readGroup.shutdownGracefully();
		}
		
		return 0;
	}

	public int stop() throws TransportException {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
