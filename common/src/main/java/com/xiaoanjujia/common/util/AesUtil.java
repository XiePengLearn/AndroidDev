package com.xiaoanjujia.common.util;

import android.util.Base64;

import com.xiaoanjujia.common.widget.bottomnavigation.utils.Utils;

import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Auther: xp
 * @Date: 2020/7/24 14:12
 * @Description:
 */
public class AesUtil {

    //AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";
    //AES 加密
    private static final String AES = "AES";
    // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
    private static final String SHA1PRNG = "SHA1PRNG";


    /**
     * 对秘钥进行处理
     *
     * @param seed 动态生成的秘钥
     * @return
     * @throws Exception
     */
    private static byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(AES);
        //for android
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        int sdk_version = android.os.Build.VERSION.SDK_INT;
        // Android  6.0 以上
        if (sdk_version > 23) {
            sr = SecureRandom.getInstance(SHA1PRNG, new CryptoProvider());
            //4.2及以上
        } else if (android.os.Build.VERSION.SDK_INT >= 19) {
            sr = SecureRandom.getInstance(SHA1PRNG);
        } else {
            sr = SecureRandom.getInstance(SHA1PRNG);
        }


        // for Java
        // secureRandom = SecureRandom.getInstance(SHA1PRNG);
        sr.setSeed(seed);
        //256 bits or 128 bits,192bits
        kgen.init(128, sr);
        //AES中128位密钥版本有10个加密循环，192比特密钥版本有12个加密循环，256比特密钥版本则有14个加密循环。
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    /**
     * 加密
     *
     * @param key
     * @param cleartext
     * @return
     */
    public static String encrypt(String key, String cleartext) {
        if (Utils.isNull(cleartext)) {
            return cleartext;
        }
        try {
            byte[] result = encrypt(key, cleartext.getBytes());
            return new String(Base64.encode(result, Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] encrypt(String key, byte[] clear) throws Exception {
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
        Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }


    // 增加  CryptoProvider  类
    public static class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }


    /**
     * 加密
     * @param sSrc
     *   加密字段
     * @param sKey
     *   key秘钥
     *   1：加密完成后Base64转码
     *   2: 加密完成后二进制转换成16进制
     * @return
     * @throws Exception
     */
    public static String Encrypt( String sKey,String sSrc) throws Exception {
        try {
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
            String sign = new String(Base64.encode(encrypted, Base64.NO_WRAP));
            String noBlankSign = sign.replaceAll(" ", "");
            return noBlankSign.trim();
        } catch (Exception e) {
            e.printStackTrace();
            return sSrc;
        }
    }

    /**
     * 备注
     *
     * 密钥:sgg45747ss223455
     *
     * aes加密
     * @param String input 加密的字符串
     * @param String key   解密的key
     * @return HexString
     *
     * public function encrypt($input = '') {
     *         $size = mcrypt_get_block_size(MCRYPT_RIJNDAEL_128, MCRYPT_MODE_ECB);
     *         $input = $this->pkcs5_pad($input, $size);
     *         $td = mcrypt_module_open(MCRYPT_RIJNDAEL_128, '', MCRYPT_MODE_ECB, '');
     *         $iv = mcrypt_create_iv(mcrypt_enc_get_iv_size($td), MCRYPT_RAND);
     *         mcrypt_generic_init($td, $this->key, $iv);
     *         $data = mcrypt_generic($td, $input);
     *         mcrypt_generic_deinit($td);
     *         mcrypt_module_close($td);
     *         $data = base64_encode($data);
     *
     *         return $data;
     * }
     */
}

