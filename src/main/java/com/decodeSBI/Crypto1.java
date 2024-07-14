package com.decodeSBI;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;

public class Crypto1 {
    public static String decode1(String str, String str2) {
        if (str2.length() <= 16) {
            str2 = str2 + "$$$$$$$$$$$$$$$$".substring(0, 16 - str2.length());
        }
        if (str2.length() > 16) {
            str2 = str2.substring(0, 16);
        }
        return b("AES/CBC/PKCS5padding", str2, str, d(str2), b(str2));
    }

    public static String b(String str, String str2, String str3, String str4, byte[] bArr) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(c(str4, str2), "AES");
        try {
            Cipher cipher = Cipher.getInstance(str);
            cipher.init(2, secretKeySpec, new IvParameterSpec(bArr));
            byte[] prepare = Base64.getDecoder().decode(str3);
            byte[] doFinal = cipher.doFinal(prepare);
            if (doFinal == null) {
                return null;
            }
            return new String(doFinal);
        } catch (Exception e) {
            return e.toString();
        }
    }
    public static byte[] c(String str, String str2) {
        SecretKey secretKey;
        try {
            secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(str2.toCharArray(), str.getBytes("UTF-8"), 2, 128));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeySpecException unused) {
            secretKey = null;
        }
        return secretKey.getEncoded();
    }

    public static byte[] b(String str) {
        byte[] bArr;
        byte[] bArr2 = new byte[16];
        Arrays.fill(bArr2, (byte) 0);
        try {
            bArr = new StringBuffer(str).reverse().toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            bArr = null;
        }
        byte[] bArr3 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr3[i] = (byte) (bArr[i] >> 1);
        }
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            bArr2[i2] = bArr3[i2];
        }
        return bArr2;
    }

    private static String d(String str) {
        char[] charArray = str.toCharArray();
        char c2 = charArray[0];
        charArray[0] = charArray[1];
        charArray[1] = c2;
        char c3 = charArray[charArray.length - 1];
        charArray[charArray.length - 1] = charArray[charArray.length - 2];
        charArray[charArray.length - 2] = c3;
        return new String(charArray);
    }

}
