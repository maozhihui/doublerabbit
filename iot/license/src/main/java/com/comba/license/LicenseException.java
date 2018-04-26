package com.comba.license;

@SuppressWarnings("serial")

public class LicenseException extends RuntimeException {
	LicenseStatus status;
	
	public LicenseException(LicenseStatus status,String message) {
		super(message);
		this.status = status;
	}
	
	public LicenseStatus getLicenseStatus(){
		return status;
	}
	
	public LicenseException(Throwable cause) {
		super(cause);
	}

	public LicenseException(String message, Throwable cause) {
		super(message, cause);
	}
}
