package org.thinkinghub.gateway.oauth.bean;

public class WeiboAccessToken{
	
	private final String access_token;
	private final int remind_in;
	private final int expires_in;
	private final String uid;
	
	public WeiboAccessToken(String access_token, int remind_in, int expires_in, String uid) {
		super();
		this.access_token = access_token;
		this.remind_in = remind_in;
		this.expires_in = expires_in;
		this.uid = uid;
	}
	public String getAccess_token() {
		return access_token;
	}
	public int getRemind_in() {
		return remind_in;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public String getUid() {
		return uid;
	}
}
