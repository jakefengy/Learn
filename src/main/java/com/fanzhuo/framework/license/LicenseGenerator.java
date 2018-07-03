package com.fanzhuo.framework.license;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 2018-07-03.
 */
public class LicenseGenerator {
    private static final Logger log = LoggerFactory.getLogger(LicenseGenerator.class);

    // PBE
    public static final String PBE_ALGORITHM = "PBEWITHMD5andDES";
    public static final char[] PBE_PASSWORD = "Xiaokuai1234".toCharArray();
    public static final byte[] PBE_SALT = "xiaokuai".getBytes();// Base64.decodeBase64("RfjrZmCd75M=");

    // DSA
    // TODO 生成新的密钥对
    public static final String DSA_ALGORITHM = "DSA";
    public static final String DSA_PUBLIC_KEY = "MIIBuDCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYUAAoGBALraD2xKrcHUiIZd5KgjB57jkQrVprah3XPB49eghS/Wx8c5hej3W9Qb+NM66ozh+7qne3ZbwuCexDbymNzRTqrlv/8l4jaeVxt5d4WT1Foku7CfMos1IM/utbdV/cxfW2/n6pgU00leOE5M5SZJu7QOSmvxl5o2ugurS/QDyXdF";
    public static final String DSA_PRIVATE_KEY = "MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUaNW4gN7ofgwWSUQXxXJvFgoTFmM=";

    /**
     * PBE加密
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encryptPBE(byte[] data)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        PBEKeySpec keySpec = new PBEKeySpec(PBE_PASSWORD);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        PBEParameterSpec paramSpec = new PBEParameterSpec(PBE_SALT, 100);
        Cipher cipher = Cipher.getInstance(PBE_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        return cipher.doFinal(data);
    }

    /**
     * PBE解密
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decryptPBE(byte[] data)
            throws NoSuchAlgorithmException, InvalidKeySpecException,
            NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        PBEKeySpec keySpec = new PBEKeySpec(PBE_PASSWORD);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        PBEParameterSpec paramSpec = new PBEParameterSpec(PBE_SALT, 100);
        Cipher cipher = Cipher.getInstance(PBE_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
        return cipher.doFinal(data);
    }

    /**
     * 使用私钥对信息进行数字签名(DSA)
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static String signDSA(byte[] data)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        byte[] keyBytes = Base64.decodeBase64(DSA_PRIVATE_KEY);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(DSA_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initSign(priKey);
        signature.update(data);
        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * 使用公钥校验数字签名(DSA)
     *
     * @param data
     * @param sign
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static boolean verifyDSA(byte[] data, String sign)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        byte[] keyBytes = Base64.decodeBase64(DSA_PUBLIC_KEY);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(DSA_ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /**
     * 生成license文件内容
     */
    public static void generate() {
        try {
            License lic = new License();
            lic.setType("server");// Server, Client
            lic.setDateExpired("2018-12-31");
            lic.setMac("");
            lic.setIp("");

            lic.setUser("Developer");// Developer
            lic.setAddress("");
            lic.setContact("");
            lic.setTel("");
            lic.setZip("");// 邮编

            // dsa
//			log.info(lic.getSignValues());
            lic.setSign(LicenseGenerator.signDSA(lic.getSignValues().getBytes()));

            log.info(lic.toString());
            String licStr = Base64.encodeBase64String(LicenseGenerator.encryptPBE(lic.toString().getBytes()));
            log.info(licStr);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static void main(String[] args) {

        LicenseGenerator.generate();

        // verify
//		log.info("read license file for test");
//		License lic = new License();
//		lic.readFile();
//		log.info("license:\n" + lic.toString());
//		lic.verify();
//		log.info("license isValid={}", lic.isValid());
    }
}
