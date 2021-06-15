package com.victor.taller.project.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	
	private static final char[] CONSTS_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static String encryptMD5(String password) {
		try {
			MessageDigest msgd = MessageDigest.getInstance("MD5");
			byte[] bytes = msgd.digest(password.getBytes());
			StringBuilder strMD5 = new StringBuilder(2 * bytes.length);
			for(int i=0;i<bytes.length;i++){  
				int _down = (int) (bytes[i] & 0x0f);
				int _up = (int) ((bytes[i] & 0xf0) >> 4);
				strMD5.append(CONSTS_HEX[_up]);
				strMD5.append(CONSTS_HEX[_down]);
			}
			//System.out.println(strMD5.toString());
			return strMD5.toString();
			
		} catch(NoSuchAlgorithmException err) {
			return null;
		}
	}
	
}
