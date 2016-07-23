package com.github.walterfan.tpproxy;

public interface TransportServer {

	int start() throws TransportException;
	
	int stop() throws TransportException;
	
	boolean isStarted();
	
	String getName();

}
