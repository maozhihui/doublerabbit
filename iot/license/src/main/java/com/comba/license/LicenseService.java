package com.comba.license;

import java.io.File;

import com.bis.common.license.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LicenseService {
	private static volatile LicenseService instance;
	private LicenseRSADecrypter decrypter;
	private ContentDecoder contentDecoder;
	private LicenseParser parser;
	private LicenseStatus status;
	private License license;
	private String licenseFile;
	private String publicKeyFile;
	
	private LicenseService() {
		license = new License();
		decrypter = new LicenseRSADecrypter();
		contentDecoder = new ContentDecoder();
		parser = new LicenseParser();
		
		//公钥文件，默认路径；tomcat路径
		
	}
	
	public static LicenseService getInstance() {
		if(instance==null) {
			synchronized (LicenseService.class) {
				if(instance==null) {
					instance = new LicenseService();
				}
			}
		}
		return instance;
	}
	
	public License getLicense(){
		return license;
	}
	
	public LicenseStatus getLicenseStatus(){
		return status;
	}
	
	public String getLicenseFile(){
		return licenseFile;
	}
	
	public boolean setLicenseFile(String licenseFile){
		File file = new File(licenseFile);
		if(!file.exists()){
			log.error("LicenseStatus.NOT_LICENSE");
			return false;
		}
		this.licenseFile = licenseFile;
		return true;
	}
	
	public String getPublicKeyFile(){
		return publicKeyFile;
	}
	
	public boolean setPublicKeyFile(String publicKeyFile){
		File file = new File(publicKeyFile);
		if(!file.exists()){
			log.error("LicenseStatus.NOT_PUBLICKEY");
			return false;
		}
		this.publicKeyFile = publicKeyFile;
		return true;
	}
	
	//是否在有效期，endTime时间是包含当天，所以加上dayMillis。
	public boolean isVaildTime() {
		long dayMillis = 24 * 60 * 60 * 1000;
	
		if(System.currentTimeMillis() >= license.getStartTime().getTime() && 
			System.currentTimeMillis() <= (license.getEndTime().getTime() + dayMillis)){
			return true;
		}
		else{
			return false;
		}
	}
	
	//解析license文件
	public LicenseStatus parseLicense(){
		try {
			// 使用公钥文件，对license文件进行RSA解码,返回字符流
			byte[] decryptedData = decrypter.decrypt(licenseFile,publicKeyFile);
			
			// 对content内容进行HEX解码，返回xml内容
			String content = contentDecoder.getContent(decryptedData);
	
			//解析xml内容，返回license对象
			license = parser.parse(content);
			
			//license序列号
			String licenseSerialNumber = license.getSerialNumber();
			String systemSerialNumber = Utils.getSerialNumber();
			if(!licenseSerialNumber.contains(systemSerialNumber)){
				status = LicenseStatus.ILLEGAL_SN;
				return status;
			}
			
			//license有效期
			if(!isVaildTime()){
				status = LicenseStatus.EXPIRED;
				return status;
			}
			status = LicenseStatus.VALIDITY_LICENSE;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			if(e instanceof LicenseException){
				LicenseException exception = (LicenseException)e;
				status = exception.getLicenseStatus();
			}
		}
		
		return status;
	}
}
