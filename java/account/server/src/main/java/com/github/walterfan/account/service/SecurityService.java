package com.github.walterfan.account.service;

public interface SecurityService {
	String getMasterKey();
	
	String getMasterSalt();
	
	String getSessionEncrKey();
	
	String getSessionSaltKey();
	
	String getSessionAuthKey();
}
