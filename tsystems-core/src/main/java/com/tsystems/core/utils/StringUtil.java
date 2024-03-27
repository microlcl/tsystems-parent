package com.tsystems.core.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;



public class StringUtil extends StringUtils {
	public static final String UTF8 = "UTF-8";
	
	public static String getFullStackTrace(Throwable t) {
		String stackTrace = null;
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			pw.flush();
			stackTrace = sw.toString();
		}
		finally {
			try {
				sw.close();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			pw.close();
		}
		return stackTrace;
	}


	public static String nullAsEmpty(String str) {
		return str == null ? "" : str;
	}


	public static String getMsgOrClzName(Throwable e) {
		return e.getClass().getName() + ": " + (e.getMessage() == null ? "" : e
						.getMessage());
	}

	public static boolean isEmptyWithTrim(String str) {
		return str == null || str.length() == 0 || str.trim().isEmpty();
	}

	public static boolean isAllEmptyWithTrim(String... strs) {
		if (strs == null || strs.length == 0) {
			return true;
		}
		for (String s : strs) {
			if (!isEmptyWithTrim(s)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isAnyEmptyWithTrim(String... strs) {
		if (strs == null || strs.length == 0) {
			return true;
		}
		for (String s : strs) {
			if (isEmptyWithTrim(s)) {
				return true;
			}
		}
		return false;
	}

	public static String safeTrim(String str) {
		return str == null ? null : str.trim();
	}

	public static boolean safeTrimEqual(String str1, String str2) {
		if (str1 == null && str2 == null) {
			return true;
		}
		if (str1 == null || str2 == null) {
			return false;
		}
		return str1.trim().equals(str2.trim());
	}

	public static boolean safeEqual(Object o1, Object o2) {
		if (o1 == null && o2 == null) {
			return true;
		}
		if (o1 == null || o2 == null) {
			return false;
		}
		return o1.equals(o2);
	}

	private static final byte[] DEFAULT_DES_IV = new byte[] {
			0x00, 86, 0x18, 0x25, 0x11, 0x10, 0x54, 0x5 };

	public static byte[] desEnc(String content, String key) {
		return desEnc(content, key, UTF8, "DES", null, null);
	}

	public static byte[] desEnc(String content, String key, String encoding,
			String transformation) {
		return desEnc(content, key, encoding, transformation, DEFAULT_DES_IV, null);
	}

	public static byte[] desEnc(String content, String key, String encoding,
			String transformation, byte[] iv) {
		return desEnc(content, key, encoding, transformation, iv, null);
	}

	public static byte[] desEnc(String content, String key, String encoding,
			String transformation, byte[] myiv, String provider) {
		try {
			DESKeySpec desKey = new DESKeySpec(key.getBytes(encoding));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher =
					(provider == null || (provider = provider.trim()).isEmpty()) ? Cipher
							.getInstance(transformation) : Cipher.getInstance(transformation,
									provider); //NOSONAR
							String ut = transformation.toUpperCase();
							if (!"DES".equals(ut) && !ut.startsWith("/DES/ECB/")) {
								IvParameterSpec iv = new IvParameterSpec(myiv);//NOSONAR
								cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
							}
							else {
								SecureRandom random = new SecureRandom();
								cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
							}
							return cipher.doFinal(content.getBytes(encoding));

		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static String desDec(byte[] content, String key) {
		return desDec(content, key, UTF8, "DES", null, null);
	}

	public static String desDec(byte[] content, String key, String encoding,
			String transformation) {
		return desDec(content, key, encoding, transformation, DEFAULT_DES_IV, null);
	}

	public static String desDec(byte[] content, String key, String encoding,
			String transformation, byte[] iv) {
		return desDec(content, key, encoding, transformation, iv, null);
	}

	public static String desDec(byte[] content, String key, String encoding,
			String transformation, byte[] myiv, String provider) {
		try {
			DESKeySpec desKey = new DESKeySpec(key.getBytes(encoding));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher =
					(provider == null || (provider = provider.trim()).isEmpty()) ? Cipher
							.getInstance(transformation) : Cipher.getInstance(transformation,
									provider);//NOSONAR
							String ut = transformation.toUpperCase();
							if (!"DES".equals(ut) && !ut.startsWith("/DES/ECB/")) {
								IvParameterSpec iv = new IvParameterSpec(myiv);//NOSONAR
								cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
							}
							else {
								SecureRandom random = new SecureRandom();
								cipher.init(Cipher.DECRYPT_MODE, securekey, random);
							}
							byte[] result = cipher.doFinal(content);
							return new String(result, encoding);
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static String escapeJSON(String value) {
		if (value == null || value.trim().isEmpty()) {
			return value;
		}
		return value.replace("\\", "\\\\").replace("'", "\\'")
				.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r")
				.replace("\t", "\\t");
	}

	private static final Pattern EMAIL_PATTERN =
			Pattern
			.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"); //NOSONAR

	public static boolean isValidEmail(final String email) {
		Matcher matcher = EMAIL_PATTERN.matcher(email);
		return matcher.matches();
	}

	public static String
	safeReplace(String str, String target, String replacement) {
		if (str == null || str.isEmpty()) {
			return str;
		}

		return str.replace(target, replacement);
	}

	private static final byte[] DEFAULT_AES_IV = new byte[] {
			0x00, 86, 0x18, 0x25, 0x11, 0x10, 0x54, 0x5, 0x00, 86, 0x18, 0x11, 0x26,
			0x06, 97, 0x5 };

	public static byte[] aesEnc(String content, String key) {
		return aesEnc(content, key, UTF8, "AES", null, null);
	}

	public static byte[] aesEnc(String content, String key, String encoding,
			String transformation) {
		return aesEnc(content, key, encoding, transformation, DEFAULT_AES_IV, null);
	}

	public static byte[] aesEnc(String content, String key, String encoding,
			String transformation, byte[] iv) {
		return aesEnc(content, key, encoding, transformation, iv, null);
	}

	public static byte[] aesEnc(String content, String key, String encoding,
			String transformation, byte[] iv, String provider) {
		try {
			return aesEnc(content, key.getBytes(encoding), encoding, transformation,
					iv, provider);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] aesEnc(String content, byte[] key, String encoding,
			String transformation, byte[] myiv, String provider) {
		try {
			SecretKey securekey = new SecretKeySpec(key, "AES");
			Cipher cipher =
					(provider == null || (provider = provider.trim()).isEmpty()) ? Cipher
							.getInstance(transformation) : Cipher.getInstance(transformation,
									provider); //NOSONAR
							String ut = transformation.toUpperCase();
							if (!"AES".equals(ut) && !ut.startsWith("/AES/ECB/")) {
								IvParameterSpec iv = new IvParameterSpec(myiv);//NOSONAR
								cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
							}
							else {
								SecureRandom random = new SecureRandom();
								cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
							}
							byte[] result = cipher.doFinal(content.getBytes(encoding));
							return result;
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	public static String aesDec(byte[] content, String key) {
		return aesDec(content, key, UTF8, "AES", null, null);
	}

	public static String aesDec(byte[] content, String key, String encoding,
			String transformation) {
		return aesDec(content, key, encoding, transformation, DEFAULT_AES_IV, null);
	}

	public static String aesDec(byte[] content, String key, String encoding,
			String transformation, byte[] iv) {
		return aesDec(content, key, encoding, transformation, iv, null);
	}

	public static String aesDec(byte[] content, String key, String encoding,
			String transformation, byte[] iv, String provider) {
		try {
			return aesDec(content, key.getBytes(encoding), encoding, transformation,
					iv, provider);
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static String aesDec(byte[] content, byte[] key, String encoding,
			String transformation, byte[] IV, String provider) {
		try {
			SecretKey securekey = new SecretKeySpec(key, "AES");
			Cipher cipher =
					(provider == null || (provider = provider.trim()).isEmpty()) ? Cipher
							.getInstance(transformation) : Cipher.getInstance(transformation,
									provider); //NOSONAR
							String ut = transformation.toUpperCase();
							if (!"AES".equals(ut) && !ut.startsWith("/AES/ECB/")) {
								IvParameterSpec iv = new IvParameterSpec(IV);//NOSONAR
								cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
							}
							else {
								SecureRandom random = new SecureRandom();
								cipher.init(Cipher.DECRYPT_MODE, securekey, random);
							}
							byte[] result = cipher.doFinal(content);
							return new String(result, encoding);
		}
		catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}



	public static String stripEmbeddedComment(String src) {
		if (src == null || src.trim().isEmpty()) {
			return src;
		}
		int f = 0, t = src.length(), b = -1, e = -1;
		StringBuilder sb = new StringBuilder(src.length());
		while (true) {
			b = src.indexOf("/*", f);
			if (b < 0) {
				sb.append(src.substring(f, t));
				break;
			}

			e = src.indexOf("*/", b + 2);
			if (e < 0) {
				sb.append(src.substring(f, t));
				break;
			}

			sb.append(src.subSequence(f, b));
			f = e + 2;
		}

		return sb.toString();
	}


	public static String shorten(Object o){
		String s = o==null?null:o.toString();
		if (s==null || s.length()<100) { return s; }
		return s.substring(0, 30)+"......"+s.substring(s.length()-30);
	}

	public static String generateRandomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static String getStackTrace(Exception e) {
		StringBuilder sb = new StringBuilder();
		if (e != null) {
			for (StackTraceElement element : e.getStackTrace()) {
				sb.append("\r\n\t").append(element);
			}
		}
		return sb.length() == 0 ? null : sb.toString();
	}

	public static String getStackTrace(Throwable e) {
		StringBuilder sb = new StringBuilder();
		if (e != null) {
			for (StackTraceElement element : e.getStackTrace()) {
				sb.append("\r\n\t").append(element);
			}
		}
		return sb.length() == 0 ? null : sb.toString();
	}

	public static Integer toInteger(String text) {
		return toInteger(text, null);
	}

	public static Integer toInteger(String text, Integer defaultValue) {
		if (StringUtil.isEmptyWithTrim(text)) { return defaultValue; }
		try {
			return Integer.valueOf(text.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

	public static Long toLong(String text) {
		return toLong(text, null);
	}

	public static Long toLong(String text, Long defaultValue) {
		if (StringUtil.isEmptyWithTrim(text)) { return defaultValue; }
		try {
			return Long.valueOf(text.trim());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultValue;
	}

}
