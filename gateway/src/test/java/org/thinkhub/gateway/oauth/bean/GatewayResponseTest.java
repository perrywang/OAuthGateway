package org.thinkhub.gateway.oauth.bean;

import org.junit.Before;
import org.junit.Test;
import org.thinkinghub.gateway.oauth.response.GatewayResponse;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

public class GatewayResponseTest {
	GatewayResponse response;
	@Before
	public void setUp(){
		response = new GatewayResponse("testuser","Tom Hanks",
				"http://qzapp.qlogo.cn/qzapp/101343463/9AA08384ABCDD6F9709DDAAE169AAC14/30",ServiceType.QQ,"testing response");
	}
	
	@Test
	public void shouldReturnToString(){
		System.out.println(response);
	}
}
