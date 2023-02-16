package io.qmeta.wps.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jimtoner on 03/05/2017.
 * 一些编码解码转换等工具类操作
 */
public class Codec {

    public static String encrypt(String src, String key) {
        return encodeToBase64WithURLSafe(encryptToAES_ECB_PKCS5Padding(src, key));
    }

    public static byte[] encryptToAES_ECB_PKCS5Padding(String content, String secretKey) {
        byte[] result = null;
        try {
            //TODO 敏感词加密
            SecretKeySpec key256 = new SecretKeySpec(getMd5(secretKey.getBytes("utf-8")).getBytes("utf-8"), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key256);
            byte[] byteContent = content.getBytes("utf-8");
            result = cipher.doFinal(byteContent);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
        }
        return result;
    }

    public static String encodeToBase64WithURLSafe(byte[] in) {
        try {
            byte[] data = Base64.encode(in, Base64.URL_SAFE);
            return new String(data, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    public static String getMd5(String text) {
        String md5 = null;
        try {
            md5 = getMd5(text.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
        }
        return md5;
    }

    public static String getMd5(byte[] data) {
        String md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            md5 = byteArrayToHexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
        }
        return md5;
    }

    public static String getSHA1(String text){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("utf-8"));
            byte[] sha1hash = md.digest();
            return byteArrayToHexString(sha1hash);
        } catch (UnsupportedEncodingException e) {
        } catch (NoSuchAlgorithmException e) {}
        return null;
    }

    public static String byteArrayToHexString(byte[] array) {
        if (null == array) {
            return null;
        }
        StringBuilder sb = new StringBuilder(array.length * 2);
        for (int i = 0; i < array.length; ++i) {
            int v = array[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }

        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String urlEncode(String path) {
        String result = null;
        try {
            result = URLEncoder.encode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
        }
        return result;
    }
}
