package kr.or.kpf.lms.common.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import gnu.crypto.hash.HashFactory;
import gnu.crypto.hash.IMessageDigest;
import gnu.crypto.util.Util;

public class SecurityUtil {

    private static EncryptUtil ENCRYPT_UTIL = null;

    public SecurityUtil(String encryptKey) {
        SecurityUtil.ENCRYPT_UTIL = new EncryptUtil(encryptKey);
    }

    public static String encrypt(String text) {
        return ENCRYPT_UTIL.encrypt(text);
    }

    public static String decrypt(String cipherText) {
        return ENCRYPT_UTIL.decrypt(cipherText);
    }

    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-512 인코딩 방식 적용)
     *
     * @param password 암호화될 패스워드
     * @param id       salt로 사용될 사용자 ID 지정
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String hashPassword(String password, String id) {
        if (id == null) {
            return ""; // KISA 보안약점 조치 (2018-12-11, 신용호)
        }

        return hashPassword(password, id.getBytes());
    }

    /**
     * 비밀번호를 암호화하는 기능(복호화가 되면 안되므로 SHA-512 인코딩 방식 적용)
     *
     * @param data 암호화할 비밀번호
     * @param salt Salt
     * @return 암호화된 비밀번호
     * @throws Exception
     */
    public static String hashPassword(String data, byte[] salt) {

        if (data == null) {
            return "";
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        md.reset();
        md.update(salt);

        return java.util.Base64.getEncoder().encodeToString(md.digest(data.getBytes()));
    }

    public static String asisPasswd(String input) {
        IMessageDigest md = HashFactory.getInstance("SHA-256");
        md.update(input.getBytes(), 0, input.length());
        return Util.toString(md.digest()).toLowerCase();
    }
}
