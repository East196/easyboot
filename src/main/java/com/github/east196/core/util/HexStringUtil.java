package com.github.east196.core.util;

import java.util.List;

public class HexStringUtil {

	/**
	 * 将每一个字节的16进制数作为字符串格式拼接在一起（半个字节补零）,然后再转换成数值
	 * 
	 * @param b
	 * @param start
	 * @param end
	 * @return
	 */
	public static int intOfSplicHex(List<Byte> b, int start, int end, int radix) {
		return Integer.valueOf(strOfSplicHex(b, start, end), radix);
	}


	public static int getBitWarn(byte b,int index){
		return (b>>(7-index))&0x1;
	}

	public static String strOfSplicHex(List<Byte> b, int start, int end) {
		String str = "";
		for (int i = start; i <= end; i++) {
			str = str + toHexString(b.get(i));
		}
		return str;
	}
	
	public static int intOfSplicHex(byte[] b, int start, int end, int radix) {
		return Integer.valueOf(strOfSplicHex(b, start, end), radix);
	}
	
	public static int intOfSplicHex(byte[] b, int start, int end) {
		return Integer.valueOf(strOfSplicHex(b, start, end), 16);
	}
	
	public static String strOfSplicHex(byte[] b, int start, int end) {
		String str = "";
		for (int i = start; i <= end; i++) {
			str = str + toHexString(b[i]);
		}
		return str;
	}
	
	public static String strOfSplicHex(byte[] b) {
		String str = "";
		for (int i = 0; i < b.length; i++) {
			str = str + toHexString(b[i]);
		}
		return str;
	}
	

	public static  String strOfSplicHexAddSpace(List<Byte> b, int start, int end) {
		String str = "";
		for (int i = start; i <= end; i++) {
			str = str + " " + toHexString(b.get(i));
		}
		return str;
	}

	/**
	 * 每个字节上的16进制数转换成字符串附带补0
	 * 
	 * @param b
	 * @return 补0的16进制数值的字符串
	 */
	public static String toHexString(byte b) {
		String hexString;
		if ((b & 0xff) <= 0x0F) {
			hexString = "0" + Integer.toHexString(b & 0xff);
		} else {
			hexString = Integer.toHexString(b & 0xff);
		}
		return hexString;
	}

	public static byte getByteByHexString(String hexString) throws Exception {
		if (hexString.length() == 2) {
			return Integer.valueOf(hexString, 16).byteValue();
		} else {
			throw new Exception("String length must be 2");
		}
	}

	public static byte[] getBytesByHexString(String hexString) throws Exception {

		if (hexString.length() % 2 == 0) {
			byte[] bytes = new byte[hexString.length() / 2];
			for (int i = 0; i < hexString.length(); i = i + 2) {
				bytes[i / 2] = getByteByHexString(hexString.substring(i, i + 2));
			}
			return bytes;
		} else {
			throw new Exception("hexString length must be multiple of 2 ");
		}
	}
}
