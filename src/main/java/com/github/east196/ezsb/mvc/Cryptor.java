package com.github.east196.ezsb.mvc;



import cn.hutool.json.JSONUtil;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/*-- JAVA --*/

/**
 * Is used for encrypting and decrypting Strings and JSONObjects. <br>
 * The JSON Objects can then be sent to a PHP script where they can be encrypted and decrypted with the same algorithm. 
 * @throws SecurityException
 */
public class Cryptor {
	
    private Cipher cipher;
    private final String secretKey = "east196";
    private final String iv = "east196";
    private final String CIPHER_MODE = "AES/CFB8/NoPadding";

    private SecretKey keySpec;
    private IvParameterSpec ivSpec;
    private Charset CHARSET = Charset.forName("UTF-8");

    public Cryptor() {
        keySpec = new SecretKeySpec(secretKey.getBytes(CHARSET), "AES");
        ivSpec = new IvParameterSpec(iv.getBytes(CHARSET));
        try {
            cipher = Cipher.getInstance(CIPHER_MODE);
        } catch (NoSuchAlgorithmException e) {
            throw new SecurityException(e);
        } catch (NoSuchPaddingException e) {
            throw new SecurityException(e);
        }
    }
    
    /**
     * @param input A "AES/CFB8/NoPadding" encrypted String
     * @return The decrypted String
     * @throws SecurityException
     */
    public String decrypt(String input) {
    	//logger.info("Client sent request >>uriParams："+input);
    	if(input==null || "".equals(input)){
    		throw new IllegalArgumentException("参数为空");
    	}
        try {
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return  new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(new String(input.getBytes(CHARSET),CHARSET))),CHARSET); 
        } catch (IllegalBlockSizeException e) {
            throw new SecurityException(e);
        } catch (BadPaddingException e) {
            throw new SecurityException(e);
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new SecurityException(e);
        }
    }

    /**
     * @param input Any String to be encrypted
     * @return An "AES/CFB8/NoPadding" encrypted String
     * @throws SecurityException
     */
    public String encrypt(String input)  {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return DatatypeConverter.printBase64Binary(cipher.doFinal(input.getBytes(CHARSET))).trim();
        } catch (InvalidKeyException e) {
            throw new SecurityException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new SecurityException(e);
        } catch (IllegalBlockSizeException e) {
            throw new SecurityException(e);
        } catch (BadPaddingException e) {
            throw new SecurityException(e);
        }
    }

    public static <T> T  toBean(String src,Class<T> classOfT){
        Cryptor cryptor = new Cryptor();
        String decrypt = cryptor.decrypt(src);
        T t = JSONUtil.toBean(decrypt, classOfT);
        return t;
    }

    public static void main(String[] args) {
        Cryptor cryptor = new Cryptor();
        System.out.println(cryptor.encrypt("{\"address\":\"中科院AAAAAAAA栋\",\"areaIds\":[\"440000\",\"440300\",\"440304\"],\"dates\":[\"2019-07-02 17:23:13\",\"2019-07-30 17:23:13\"],\"mainName\":\"深圳民生安全设备维保公司\",\"phoneNum\":\"0731-88899900\",\"principal\":\"贺洋维保员\"}"));
        System.out.println(cryptor.encrypt("{\"userId\":\"a38a44047dbdc4ec8d6520271285c365\",\"name\":\"创建工程A\",\"address\":\"大学城地铁站A出口前进100米\",\"adminName\":\"刚回家\",\"phone\":\"17665308351\",\"companyArea\":[\"440000\",\"440300\",\"440305\"],\"cycle\":[\"2019-07-12 05:19:31\",\"2019-09-12 05:19:31\"],\"totalSmoke\":\"1000\",\"totalWater\":\"1000\",\"totalGas\":\"1000\",\"totalElectric\":\"1000\"}"));
        System.out.println(cryptor.encrypt("userId=498c88d34fad11e98f3200e04cd15bc3&phone=17688940146"));
        System.out.println(cryptor.encrypt("{\"address\":\"aaaa\"}"));
        System.out.println("解密后：");
        String encrypt = cryptor.encrypt("userId=498c88d34fad11e98f3200e04cd15bc3&phone=17688940146");
        System.out.println("解密后："+cryptor.decrypt(encrypt));
    }
}


