package com.comba.license;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;

//使用公钥文件，解码成license的xml明文

public class LicenseRSADecrypter {
	private RSADecrypt rsaDecrypt;
	private byte[] decryptedData;
	
	public LicenseRSADecrypter(){
		rsaDecrypt = new RSADecrypt();
	}
	
	public byte[] decrypt(String license,String publicKey) {
		if(publicKey == null){
			throw new LicenseException(LicenseStatus.NOT_PUBLICKEY,"PublicKey file path is null !");
		}else if(license == null){
				throw new LicenseException(LicenseStatus.NOT_LICENSE,"license file path is null !");
		}
		
		File publicKeyFile = new File(publicKey);
		File licenseFile = new File(license);
		
		if(!publicKeyFile.exists()){
			throw new LicenseException(LicenseStatus.NOT_PUBLICKEY,"Not found the publicKey file:" + publicKeyFile.getPath());
		}else if(!licenseFile.exists()){
			throw new LicenseException(LicenseStatus.NOT_LICENSE,"Not found the license file:" + licenseFile.getPath());
		}
		
		try {
			InputStream publicKeyStream = new FileInputStream(publicKey);
			InputStream licenseStream = new FileInputStream(license);
			rsaDecrypt.loadPublicKey(publicKeyStream);
			//使用公钥文件，进行RSA解码，返回字符流
			decryptedData = rsaDecrypt.decrypt(IOUtils.toByteArray(licenseStream));
		} catch (Exception e) {
			throw new LicenseException(LicenseStatus.INVALID_PUBLICKEY,e.getMessage());
		}
		return decryptedData;
	}
}
