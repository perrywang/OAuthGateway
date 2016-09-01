package org.thinkinghub.gateway.oauth.controller;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.oauth.bean.WeiboAccessToken;
import org.thinkinghub.gateway.oauth.httpclient.QQRequest;
import org.thinkinghub.gateway.oauth.httpclient.WeiboRequest;

@RestController
public class GatewayController {
    @RequestMapping(value = "/controllers/gateway", method = RequestMethod.GET)
    ResponseEntity<String> route(){


        return new ResponseEntity("{}", HttpStatus.OK);
    }
    

	private Map<String,String> map = new ConcurrentHashMap<String, String>();
	
	@RequestMapping("/qq_login")
	public void qqLogin(String custCallback, HttpServletResponse response){
		QQRequest request = new QQRequest(custCallback);
		try{
		String returnURL =  request.getAuthorizationUrl();
		response.sendRedirect(returnURL);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/oauth/qq")
	public void requestQQAccessToken(String code){
		new QQRequest().getAccessToken(code);
	}
	
	@RequestMapping("/weibo_login")
	public void weiboLogin(@RequestParam("callbackUrl")String custCallbackUrl, HttpServletResponse response){
		String state = Long.toString(System.currentTimeMillis());
		map.put(state, custCallbackUrl);
		WeiboRequest request = new WeiboRequest(custCallbackUrl,state);
		try{
		String returnURL =  request.getAuthorizationUrl();
		response.sendRedirect(returnURL);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/oauth/sina")
	public ResponseEntity<String> requestWeiboAccessToken(
			HttpServletRequest request, 
			HttpServletResponse response,
			@RequestParam("code")String code,
			@RequestParam("state")String state){
		String custCallbackUrl = "thinkinghub.org";
		if(state != null){
			custCallbackUrl = map.get(state);
		}
		WeiboAccessToken token = new WeiboRequest().getResult(code);
		String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?uid=" + token.getUid();
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.set(HttpHeaders.LOCATION, redirectUrl);
		return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
	}

}
