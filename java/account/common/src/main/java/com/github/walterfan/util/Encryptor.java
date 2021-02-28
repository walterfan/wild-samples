package com.github.walterfan.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Properties;


public class Encryptor {
    
    public static final String ENC_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";

	public static final String ENC_CBC_NOPADDING = "AES/CBC/NoPadding";
    
    private static final String ENC_ALGORITHM = "AES";

    private String algorithm = ENC_ALGORITHM;
   
    
    private AlgorithmParameterSpec ivParamSpec = null;
    
    private SecretKeySpec keySpec = null;


	public static byte[] makeKeyBySHA1(String key, int len) {
		byte[] seed = EncodeUtils.sha1(key);
		//assert (seed.length == SHA1_LEN);
		byte[] raw = new byte[len];
		System.arraycopy(seed, 0, raw, 0, len);
		return raw;
	}


	public static byte[] makeKeyBySHA2(String key, int len) {
		byte[] seed = EncodeUtils.sha2(key);
		byte[] raw = new byte[len];
		System.arraycopy(seed, 0, raw, 0, len);
		return raw;
	}


	public Encryptor(String algorithm, byte[] keyBytes,  byte[] ivBytes) {
		this.algorithm = algorithm;
		this.keySpec =  new SecretKeySpec(keyBytes, algorithm);
		this.ivParamSpec = new IvParameterSpec(ivBytes);

	}

	public Encryptor( String strKey,  String strIv) {
		byte[] keyBytes = makeKeyBySHA2(strKey, 16);
		byte[] ivBytes = makeKeyBySHA2(strIv, 16);
		this.algorithm = ENC_CBC_PKCS5PADDING;
		this.keySpec =  new SecretKeySpec(keyBytes, ENC_ALGORITHM);
		this.ivParamSpec = new IvParameterSpec(ivBytes);

	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public SecretKeySpec getKeySpec() {
		return keySpec;
	}

	public void setKeySpec(SecretKeySpec keySpec) {
		this.keySpec = keySpec;
	}

	public AlgorithmParameterSpec getIvParamSpec() {
		return ivParamSpec;
	}

	public void setIvParamSpec(AlgorithmParameterSpec ivParamSpec) {
		this.ivParamSpec = ivParamSpec;
	}

	public byte[] encrypt(byte[] inputBytes) throws Exception {

		Cipher cipher = Cipher.getInstance(algorithm);

		if(null == ivParamSpec)
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
		else 
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

		//byte[] cipherText = new byte[cipher.getOutputSize(inputBytes.length)];
		//int ctLength = cipher.update(inputBytes, 0, inputBytes.length, cipherText, 0);
		return cipher.doFinal(inputBytes);

	}

	/*
	*     Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, AES_ALGORITHM));
            return cipher.doFinal(bytes);
	* */
	public byte[] decrypt(byte[] cipherText)  throws Exception {
		Cipher cipher = Cipher.getInstance(algorithm);

		if(null == ivParamSpec)
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
		else
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParamSpec);
		//int ctLength = cipherText.length;
	    //byte[] plainText = new byte[cipher.getOutputSize(ctLength)];
	    //int ptLength = cipher.update(cipherText, 0, ctLength, plainText, 0);
	    return cipher.doFinal(cipherText);
	    //return plainText;
	}

   public SecretKeySpec generateKey() throws NoSuchAlgorithmException {
    	KeyGenerator keygen = KeyGenerator.getInstance(algorithm);    
    	SecretKey skey = keygen.generateKey();
    	byte[] raw = skey.getEncoded();
    	return new SecretKeySpec(raw, algorithm);
    }
    /**
     * simple test codes
     * 
     * @param args
     *            no
     */
    public static void main(String[] args) throws Exception {
		Properties cfgProp = new Properties();
		String pathName = "/workspace/walter/wfan/conf/security.properties";
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(pathName);
			cfgProp.load(fis);
		} finally {
			if(null != fis) fis.close();
		}

        String key = cfgProp.getProperty("account.key");
        String iv = cfgProp.getProperty("account.iv");

        String pwd = "pass1234";
		String strEncryptPwd = "";
        if(args.length > 0) {
			if("-i".equals(args[0])) {
				System.out.println("Please input your password: ");
				pwd = ConsoleUtils.getStringFromConsole();
			} else if("-d".equals(args[0])) {
				System.out.println("Please input your encrypted password: ");
				strEncryptPwd = ConsoleUtils.getStringFromConsole();
			}
			else {
				pwd = args[0];
			}
		}
        Encryptor encoder = new Encryptor(key, iv);
		//encoder.setAlgorithm(ENC_CBC_NOPADDING);
		//System.out.println("key = " + EncodeUtils.byte2Hex(encoder.getKeySpec().getEncoded()));

		if("".equals(strEncryptPwd)) {
			byte[] encryptPwd = encoder.encrypt(pwd.getBytes());
			System.out.println(pwd + "--> encrypted pwd: " + new String(EncodeUtils.byte2Hex(encryptPwd)));
			byte[] decryptPwd = encoder.decrypt(encryptPwd);
			System.out.println(pwd + "--> decrypted pwd: " + new String(decryptPwd));
		} else {
			byte[] encryptPwd = strEncryptPwd.getBytes();
			byte[] decryptPwd = encoder.decrypt(encryptPwd);
			System.out.println( "decrypted pwd: " + new String(decryptPwd));
		}

        
    }

 
}
