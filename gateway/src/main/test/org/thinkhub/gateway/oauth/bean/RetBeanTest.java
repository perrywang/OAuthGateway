package org.thinkhub.gateway.oauth.bean;

import org.junit.Before;
import org.junit.Test;
import org.thinkinghub.gateway.oauth.bean.RetBean;
import org.thinkinghub.gateway.oauth.entity.ServiceType;

public class RetBeanTest {
	RetBean retBean;
	@Before
	public void setUp(){
		retBean = new RetBean("testuser","Tom Hanks",
				"http://qzapp.qlogo.cn/qzapp/101343463/9AA08384ABCDD6F9709DDAAE169AAC14/30",ServiceType.QQ,"testing response");
	}
	
	@Test
	public void shouldReturnToString(){
		System.out.println(retBean);
	}
}
