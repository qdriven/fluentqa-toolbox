package io.qmeta.wps.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils extends cn.hutool.core.io.FileUtil {
    public static String getFileSHA1(File file){
        FileInputStream fis = null;
        String sha1 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            sha1 = Codec.byteArrayToHexString(md.digest());
        } catch (IOException ex) {
        } catch (NoSuchAlgorithmException e) {
        } finally {
            silentlyClose(fis);
        }
        return sha1;
    }

    public static String getFileMD5(File file) {
        FileInputStream fis = null;
        String md5 = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            fis = new FileInputStream(file);
            byte[] buffer = new byte[4 * 1024];
            int length = -1;
            while ((length = fis.read(buffer)) != -1) {
                md.update(buffer, 0, length);
            }
            md5 = Codec.byteArrayToHexString(md.digest());
        } catch (IOException ex) {
        } catch (NoSuchAlgorithmException ex) {
        } finally {
            silentlyClose(fis);
        }
        return md5;
    }

    public static void silentlyClose(Closeable c) {
        if (null != c) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }

    public static String readFile(String filePath)
    {
        if (null == filePath)
            return "";
        FileInputStream inputstream = null;
        InputStreamReader reader = null;
        String result = "";
        try
        {
            inputstream = new FileInputStream(filePath);
            StringBuilder builder = new StringBuilder();
            reader = new InputStreamReader(inputstream, "utf-8");
            final char buffer[] = new char[4096];
            int count;
            while ((count = reader.read(buffer, 0, buffer.length)) > 0)
            {
                builder.append(buffer, 0, count);
            }
            result = builder.toString();
        }
        catch (IOException e)
        {
        }
        finally
        {
            silentlyClose(inputstream);
            silentlyClose(reader);
        }
        return result;
    }
}
