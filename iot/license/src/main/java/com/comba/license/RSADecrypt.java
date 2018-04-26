package com.comba.license;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Decoder;

@Slf4j
@SuppressWarnings("restriction")
public class RSADecrypt {
	//RSA最大解密密文大小
	private static final int MAX_DECRYPT_BLOCK = 128;
	// 私钥
	private RSAPrivateKey privateKey;
	// 公钥
	private RSAPublicKey publicKey;
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	// 随机生成密钥对
	public void genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
		this.publicKey = (RSAPublicKey) keyPair.getPublic();
	}

	//从文件中输入流中加载公钥
	public void loadPublicKey(InputStream in) throws Exception {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("Read PrivateKey Stream error");
		} catch (NullPointerException e) {
			throw new Exception("PrivateKey Stream is null");
		} finally {
			br.close();
			in.close();
		}
	}

	//从字符串中加载公钥
	 public int loadPublicKey(String publicKeyStr) throws Exception {
		 int result = 0;
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
			return result;
		} catch (NoSuchAlgorithmException e) {
			log.error("No Such Algorithm");
		} catch (InvalidKeySpecException e) {
			log.error("Invalid publicKey");
		} catch (IOException e) {
			log.error("Read PublicKey Stream error");
		} catch (NullPointerException e) {
			log.error("PublicKey Stream is null");
		}
		
		result = 4;
		return result;
	}

	/**
	 * 从文件中加载私钥
	 * @param keyFileName
	 * @return 是否成功
	 * @throws Exception
	 */
	public void loadPrivateKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("Read PrivateKey Stream error");
		} catch (NullPointerException e) {
			throw new Exception("PrivateKey Stream is null");
		}
	}

	public void loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			this.privateKey = (RSAPrivateKey) keyFactory
					.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("No Such Algorithm");
		} catch (InvalidKeySpecException e) {
			throw new Exception("Invalid PrivateKey");
		} catch (IOException e) {
			throw new Exception("Read PrivateKey error");
		} catch (NullPointerException e) {
			throw new Exception("PrivateKey is null");
		}
	}

	/**
	 * 加密过程
	 * @param publicKey
	 * @param plainTextData 明文数据
	 * @return
	 * @throws Exception
	 */
	/*
	public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		if (publicKey == null) {
			throw new Exception("publicKey is null");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("No Such Algorithm");
		} catch (NoSuchPaddingException e) {
			throw new Exception("No Such Padding");
		} catch (InvalidKeyException e) {
			throw new Exception("Invalid PublicKey");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("Illegal Block Size");
		} catch (BadPaddingException e) {
			throw new Exception("Bad Padding");
		}
	}
	*/

	/**
	 * 私钥解密过程
	 * @param privateKey 私钥
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception
	 */
	/*
	public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
			throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("No Such Algorithm");
		} catch (NoSuchPaddingException e) {
			throw new Exception("No Such Padding");
		} catch (InvalidKeyException e) {
			throw new Exception("Invalid PublicKey");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("Illegal Block Size");
		} catch (BadPaddingException e) {
			throw new Exception("Bad Padding");
		}
	}
	*/

	/**
	 * 公钥解密过程
	 * @param publicKey 公钥
	 * @param cipherData 密文数据
	 * @return 明文
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] cipherData) throws Exception {
		if (publicKey == null) {
			throw new Exception("PublicKey is null");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int maxBlockLen = MAX_DECRYPT_BLOCK;
			// 对数据分段处理
			int dataLen = cipherData.length;
			try {
				for (int i = 0; dataLen - (i * maxBlockLen) > 0; i++) {
					int size = Math.min(maxBlockLen, dataLen - (i * maxBlockLen));
					out.write(cipher.doFinal(cipherData, i * maxBlockLen,size));
				}
				return out.toByteArray();
			} finally {
				out.close();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new Exception("No Such Algorithm");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new Exception("No Such Padding");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new Exception("Invalid Key");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
			throw new Exception("Illegal Block Size");
		} catch (BadPaddingException e) {
			e.printStackTrace();
			throw new Exception("Bad Padding");
		}
	}

	/**
	 * 字节数据转十六进制字符串
	 * @param data 输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

}
