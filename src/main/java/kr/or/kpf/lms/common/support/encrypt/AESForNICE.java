package kr.or.kpf.lms.common.support.encrypt;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESForNICE {

	public static final String byteToBase64(byte[] stringBytes) {
		return Base64.getEncoder().encodeToString(stringBytes);
	}

	public static final byte[] base64ToByte(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}

	public static final String getDecryptingEncData(String encData, String key, String iv) {
		String parsedData = null;
		try {
			SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));
			byte[] decrypted = AESForNICE.base64ToByte(encData);
			parsedData = new String(c.doFinal(decrypted), "euc-kr");
		} catch (Exception e) {
			throw new RuntimeException("본인인증 처리에 실패하였습니다.");
		}
		return parsedData;
	}

	public static final String getEncryptingReqData(String reqData, String key, String iv) {
		String base64String = null;
		try {
			SecretKeySpec secureKey = new SecretKeySpec(key.getBytes(), "AES");
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(iv.getBytes()));
			byte[] encrypted = c.doFinal(reqData.trim().getBytes());
			base64String = AESForNICE.byteToBase64(encrypted);
		} catch (Exception e) {
			throw new RuntimeException("본인인증 처리에 실패하였습니다.");
		}
		return base64String;
	}

	public static final String getIntigretyValue(String hmacKey, String encReqData) {
		byte[] hmac256 = null;
		String base64String = null;
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secureKey = new SecretKeySpec(hmacKey.getBytes(), "HmacSHA256");
			mac.init(secureKey);
			hmac256 = mac.doFinal(encReqData.getBytes());
			base64String = AESForNICE.byteToBase64(hmac256);
		} catch (Exception e) {
			throw new RuntimeException("본인인증 처리에 실패하였습니다.");
		}
		return base64String;
	}

	public static final byte[] getEncryptedBase64(String reqString) {
		String base64String = null;
		byte[] base64Byte = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(reqString.getBytes());
			byte[] arrayHashValue = md.digest();
			base64String = AESForNICE.byteToBase64(arrayHashValue);
			base64Byte = base64String.getBytes("utf-8");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("본인인증 처리에 실패하였습니다.");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("본인인증 처리에 실패하였습니다.");
		} catch (Exception e) {
			throw new RuntimeException("본인인증 처리에 실패하였습니다.");
		}
		return base64Byte;
	}
}
