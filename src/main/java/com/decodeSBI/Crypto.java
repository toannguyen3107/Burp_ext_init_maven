package com.decodeSBI;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    public static String uuid = "7d6df8bhehehehe9f";
    public static RSAPublicKey createPublickey(String str) {
        try {
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.getDecoder().decode(str.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("\n",""))));
        } catch (Exception e) {
            System.out.println("createPublickey");
            return null;
        }
    }

    public static PublicKey publicKey() {
        try{
            StringBuilder sb = new StringBuilder();
            sb.append("-----BEGIN PUBLIC KEY-----");
            sb.append("MIIheheheheG9w0BAQEFheheheheCAQEAzwWLE1xZi3SYAjeDsB3F");
            sb.append("FAQNhCDZYNfc+JZl6Sys9SnNiP0BDBXrwuDJY5PQ+pFwGygFvOYGYH5HIXDoT0qg");
            sb.append("PrbvoSIfUCpOBpzKBkhgjhghjghjhehehehehehehessH67Ls491fbqTII7S3NXASuw");
            sb.append("OO4AQNlj19ftrXaOhehehehepyOeaTtD5aJZJO5");
            sb.append("aoEJAxmCt35CrmQbHd1hehehehe+PPahyXz/Re5JBbh73ND67YEegtA3U");
            sb.append("LT33BogBq1gEr1q53W1hehehehe1b8YpF/oyDZttagtn");
            sb.append("nQIDAQAB");
            sb.append("-----END PUBLIC KEY-----");
            PublicKey pbkey = createPublickey(sb.toString());
            return pbkey;
        }catch(Exception e) {
            System.out.println("public key");
            System.out.println(e);
            return null;
        }
    }
    

    public static String createS4(String str) {
        char[] charArray = str.toCharArray();
        char c2 = charArray[0];
        charArray[0] = charArray[1];
        charArray[1] = c2;
        char c3 = charArray[charArray.length - 1];
        charArray[charArray.length - 1] = charArray[charArray.length - 2];
        charArray[charArray.length - 2] = c3;
        return new String(charArray);
    }
    

    public static byte[] createbArrays(String str) {
        byte[] bArr;
        byte[] bArr2 = new byte[16];
        Arrays.fill(bArr2, (byte) 0);
        try {
            bArr = new StringBuffer(str).reverse().toString().getBytes("UTF-8");
        } catch (Exception unused) {
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

    public static byte[] createKeySRKS(String str, String str2) {
        SecretKey secretKey;
        try {
            secretKey = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                    .generateSecret(new PBEKeySpec(str2.toCharArray(), str.getBytes("UTF-8"), 2, 128));
        } catch (Exception e) {
            secretKey = null;
        }
        return secretKey.getEncoded();
    }

    public static String decode1(String str) {
        String str2 = uuid;
        if (str2.length() <= 16) {
            str2 = str2 + "$$$$$$$$$$$$$$$$".substring(0, 16 - str2.length());
        }
        if (str2.length() > 16) {
            str2 = str2.substring(0, 16);
        }
        String s2 = str2;
        String s3 = str;
        String s4 = createS4(str2);
        byte[] bArr = createbArrays(str2);

        SecretKeySpec secretKeySpec = new SecretKeySpec(createKeySRKS(s4, s2), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5padding");
            cipher.init(2, secretKeySpec, new IvParameterSpec(bArr));
            byte[] decodedBytes = cipher.doFinal(Base64.getDecoder().decode(s3));
            return new String(decodedBytes, "UTF-8");
        } catch (Exception e) {
            System.out.println("decode 1");
            System.out.println(e);
            return "have problem...";
        }
    }
    public static String decode2(String str) {
        try {
            PublicKey pbkey = publicKey();
            
            byte[] decodeStr = Base64.getDecoder().decode(str);
            
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(2, pbkey);
            byte[] doFinal = cipher.doFinal(decodeStr);
            return new String(doFinal);
        } catch (Exception e) {
            return e.toString();
        }
    }
}
