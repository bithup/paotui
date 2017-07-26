package com.xgh.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


/**
 * base64转码
 * <p/>
 * * @author:段晓刚
 *
 * @time:2011-10-24 下午08:56:53
 * @Email:
 */
public class Base64Util {


	//private static Charset utf_8 = Charset.forName("UTF-8");

	private static String utf_8 = "UTF-8";

	/**
	 * 编码
	 * 有byte[]通过Base64转码为String
	 *
	 * @param bytes
	 * @return
	 */
	public static String encodeBytes(byte[] bytes) {

		String encoded = Base64.encodeBytes(bytes);

		return encoded;
	}

	/**
	 * 编码
	 * 有byte[]通过Base64转码为String
	 *
	 * @param str
	 * @return
	 */
	public static byte[] encode(String str) {

		String encoded = null;

		try {
			encoded = Base64.encodeBytes(str.getBytes(utf_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return encoded.getBytes();
	}

	public static String encodeToString(String encode) {
		if (encode != null && !"".equals(encode)) {
			try {
				return Base64.encodeBytes(encode.getBytes(utf_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return encode;
	}


	/**
	 * 解码
	 *
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static byte[] decode(String str) throws IOException {

		byte[] decoded = Base64.decode(str);

		return decoded;
	}

	/**
	 * 解码
	 *
	 * @param encoded
	 * @return
	 * @throws IOException
	 */
	public static String decodeBytes(byte[] encoded) throws IOException {

		byte[] decoded = null;

		decoded = Base64.decode(new String(encoded));

		return new String(decoded);
	}

	public static String decodeToString(String encoded) throws IOException {
		if (null != encoded && !"".equals(encoded)) {
			return new String(Base64.decode(encoded),utf_8);
		} else
			return encoded;
	}
}