package org.thinkhub.gateway.oauth.util;

import org.junit.Test;
import org.thinkinghub.gateway.oauth.util.RegExpUtil;
import org.junit.Assert;

public class RegExpUtilTest {
	@Test
	public void isProperUrl_TestWithHttp(){
		String url = "http://abc.com.hk";
		Assert.assertTrue(RegExpUtil.isProperUrl(url));
	}
	
	@Test
	public void isProperUrl_TestWithHttps(){
		String url = "https://abc.com.hk";
		Assert.assertTrue(RegExpUtil.isProperUrl(url));
	}
	
	@Test
	public void isProperUrl_TestWithNonUrl(){
		String url = "httpsaaa://abc.com.hk-";
		Assert.assertFalse(RegExpUtil.isProperUrl(url));
	}
}
