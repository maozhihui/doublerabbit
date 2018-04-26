package com.comba.mantun.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author maozhihui
 * @date 2017年12月14日 下午2:47:15
 */
@Data
public class AuthverifyParam{
	private String response_type = "code";
	private String client_id;
	private String redirect_uri;
	private String uname;
	private String passwd;
	
	public AuthverifyParam(String clientId,String redirectUri,String uname,String passwd){
		this.client_id = clientId;
		this.redirect_uri = redirectUri;
		this.uname = uname;
		this.passwd = passwd;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("response_type=").append(response_type)
			.append("&client_id=").append(client_id)
			.append("&redirect_uri=").append(redirect_uri)
			.append("&uname=").append(uname)
			.append("&passwd=").append(passwd);
		return sb.toString();
	}
}
