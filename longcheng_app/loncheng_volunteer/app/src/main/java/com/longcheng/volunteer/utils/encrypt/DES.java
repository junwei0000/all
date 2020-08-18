package com.longcheng.volunteer.utils.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 作者：MarkMingShuai
 * 时间 2017-11-22 16:30
 * 邮箱：mark_mingshuai@163.com
 * <p>
 * 类的意图：DES加密与解密
 */
public class DES {
    public DES() {
    }

    /**
     * @param datasource
     * @param key
     * @return byet数组
     * @auhtor MarkMingShuai
     * @Data 2017-11-22 16:30
     * 加密方法
     */
    public static byte[] encrypt(byte[] datasource, byte[] key) {
        try {
            SecureRandom e = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(1, securekey, e);
            return cipher.doFinal(datasource);
        } catch (Throwable var7) {
            var7.printStackTrace();
            return null;
        }
    }

    /**
     * @param src
     * @param key
     * @return byet数组
     * @auhtor MarkMingShuai
     * @Data 2017-11-22 16:30
     * 解密方法
     */
    public static byte[] decrypt(byte[] src, byte[] key) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, securekey, random);
        return cipher.doFinal(src);
    }
}
