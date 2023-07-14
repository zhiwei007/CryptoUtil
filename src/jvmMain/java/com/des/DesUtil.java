package com.des;

import com.util.Util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class DesUtil {
    private static final String ALG_DES = "DES";
    private static final String ALG_TRIPLE_DES = "DESede";
//    private static final String DES_MODE_CBC = "CBC";
    private static final String DES_MODE_ECB = "ECB";
    private static final String DES_NO_PADDING = "NoPadding";

    public static byte[] tdesEnc(byte[] data, byte[] key) throws Exception {
        Cipher cipher = null;
        Key k = null;
        if (key.length == 8) {
            k = new SecretKeySpec(key, ALG_DES);
        } else {
            k = new SecretKeySpec(Util.concat(key, 0, 16, key, 0, 8), ALG_TRIPLE_DES);
        }

        String transformation;
        if (k.getAlgorithm().startsWith(ALG_DES)) {
            StringBuilder sb = new StringBuilder();
            sb.append(k.getAlgorithm()).append("/").append(DES_MODE_ECB).append("/").append(DES_NO_PADDING);
            transformation = sb.toString();
        } else {
            transformation = k.getAlgorithm();
        }

        cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, k);
        return cipher.doFinal(data);
    }

    public static byte[] tdesDec(byte[] data, byte[] key) throws Exception {
        Cipher cipher = null;
        Key k = null;
        if (key.length == 8) {
            k = new SecretKeySpec(key, ALG_DES);
        } else {
            k = new SecretKeySpec(Util.concat(key, 0, 16, key, 0, 8), ALG_TRIPLE_DES);
        }

        String transformation;
        if (k.getAlgorithm().startsWith(ALG_DES)) {
            StringBuilder sb = new StringBuilder();
            sb.append(k.getAlgorithm()).append("/").append(DES_MODE_ECB).append("/").append(DES_NO_PADDING);
            transformation = sb.toString();
        } else {
            transformation = k.getAlgorithm();
        }

        cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, k );
        return cipher.doFinal(data);
    }
}
