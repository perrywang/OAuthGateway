package org.thinkhub.gateway.oauth.util;

import org.junit.Assert;
import org.junit.Test;
import org.thinkinghub.gateway.util.RegexUtil;

public class RegexUtilTest {
	@Test
	public void isProperUrl_TestWithHttp(){
		String url = "http://abc.com.hk/abc/a.json";
		Assert.assertTrue(RegexUtil.isProperUrl(url));
	}
	
	@Test
	public void isProperUrl_TestWithHttps(){
		String url = "https://abc.com.hkhkaaaaaaaaaa";
		Assert.assertTrue(RegexUtil.isProperUrl(url));
	}
	
	@Test
	public void isProperUrl_TestWithSpace(){
		String url = "https://abc.com.hk/ab. cd/b";
		Assert.assertTrue(RegexUtil.isProperUrl(url));
	}

	
	@Test
	public void isProperUrl_TestWithNonUrl(){
		String url = "https://abc.com.hk/+/b";
		Assert.assertFalse(RegexUtil.isProperUrl(url));
	}
}
