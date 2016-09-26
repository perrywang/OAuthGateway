package org.thinkhub.gateway.oauth.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.thinkinghub.gateway.oauth.OAuthGatewayApplication;
import org.thinkinghub.gateway.oauth.controller.GatewayController;
import org.thinkinghub.gateway.oauth.controller.GlobalExceptionHandlingController;
import org.thinkinghub.gateway.oauth.exception.GatewayException;
import org.thinkinghub.gateway.oauth.service.WeiboService;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest(classes=OAuthGatewayApplication.class)
@ContextConfiguration(classes = GlobalExceptionHandlingController.class)
public class GlobalExceptionHandlingControllerTest extends AbstractJUnit4SpringContextTests{
    private MockMvc mockMvc;

	@Autowired
    private WebApplicationContext wac;
	
	@Mock
	WeiboService weiboService;
	
	@InjectMocks
	GatewayController gatewayController;
	
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    } 
    
	@Test(expected = GatewayException.class)
	public void gatewayError_GWUserNotFoundException_WhenGatewayExceptionReceived() throws Exception{
		mockMvc.perform(get("/oauthgateway").param("key", "123").param("service", "WEIBO"))
//		.andDo(print());
		.andExpect(status().isOk());
	}
	
/*    @Test(expected = Exception.class)
    public void defaultError_OAuthException_WhenExceptionReceived() throws Exception{
    	mockMvc.perform(get("/oauthgateway").param("key", "testuserkey").param("callbackUrl", "http://thinkinghub.org").param("service", "WEIBO"))
		.andDo(print()).andExpect(status().is3xxRedirection());
    	mockMvc.perform(get("/oauth/WEIBO").param("code", "123").param("state","456"));
    }*/
    
	@Test
	public void badAccessTokenExceptionTest() throws Exception{
/*		mockMvc.perform(get("/oauth/WEIBO").param("code", "123").param("state","456"))
		.andDo(print()).andExpect(status().isOk());;*/

		//		when(weiboService.checkToken(null));
//		OAuthService weiboService = ServiceRegistry.getService(ServiceType.WEIBO);
/*		when(weiboService.authenticated("1", "2")).thenReturn(new GatewayResponse("testuser","Tom Hanks",
				"http://qzapp.qlogo.cn/qzapp/101343463/9AA08384ABCDD6F9709DDAAE169AAC14/30",ServiceType.WEIBO,"testing response"));*/
	}

}
