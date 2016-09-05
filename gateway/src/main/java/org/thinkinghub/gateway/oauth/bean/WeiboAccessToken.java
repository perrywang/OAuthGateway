package org.thinkinghub.gateway.oauth.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WeiboAccessToken{
	
	private String access_token;
	private int remind_in;
	private int expires_in;
	private String uid;
	
	public WeiboAccessToken(String access_token, int remind_in, int expires_in, String uid) {
		super();
		this.access_token = access_token;
		this.remind_in = remind_in;
		this.expires_in = expires_in;
		this.uid = uid;
	}
}
