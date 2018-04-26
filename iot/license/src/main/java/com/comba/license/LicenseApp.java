package com.comba.license;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LicenseApp {
	@Value("${license.enabled}")
	private boolean enabled;
	
	@PostConstruct
	public void init() throws IOException{
		if (enabled){
			//初始化 license
			initLicense();
		}else {
			log.warn("license module is disabled.");
		}
	}
	
	private void initLicense() throws IOException{
		Resource licenseRes = new ClassPathResource("license.lic");
		Resource publicKeyRes = new ClassPathResource("PublicKey.pem");
		LicenseService service = LicenseService.getInstance();
		service.setLicenseFile(licenseRes.getFile().getPath());
		service.setPublicKeyFile(publicKeyRes.getFile().getPath());
		LicenseStatus status = service.parseLicense();
		log.info("license result:[{}]",status);
	}
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
	}
}
