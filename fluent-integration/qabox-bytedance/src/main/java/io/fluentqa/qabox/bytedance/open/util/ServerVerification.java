package io.fluentqa.qabox.bytedance.open.util;

import java.util.Arrays;
import java.security.MessageDigest;
import lombok.extern.slf4j.Slf4j;

/**
 * 验签工具类
 * 代码由字节跳动提供
 * https://sf3-ttcdn-tos.pstatp.com/obj/developer/thirdparty/demo.zip
 */
@Slf4j
public class ServerVerification {

    public static String getMsgSignature(String tpToken, String timestamp, String nonce, String encrypt) throws Exception {
        String[] values = new String[] {tpToken, timestamp, nonce, encrypt};
        Arrays.sort(values);

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            sb.append(value);
        }

        String str = sb.toString();

        try {
            //指定sha1算法
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(str.getBytes());

            //获取字节数组
            byte[] messageDigestByte = messageDigest.digest();

            StringBuilder hexStr = new StringBuilder();
            // 字节数组转换为十六进制数
            for (byte b : messageDigestByte) {
                String shaHex = Integer.toHexString(b & 0xFF);
                if (shaHex.length() < 2) {
                    hexStr.append(0);
                }
                hexStr.append(shaHex);
            }

            return hexStr.toString();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new Exception("不能生成签名");
        }
    }
}
