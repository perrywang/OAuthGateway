package org.thinkinghub.gateway.oauth.controller;


import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thinkinghub.gateway.oauth.bean.WeiboAccessToken;
import org.thinkinghub.gateway.oauth.service.QQService;
import org.thinkinghub.gateway.oauth.service.WeiboService;

@RestController
public class GatewayController {
	@Autowired
	QQService qqService;
	
	@Autowired
	WeiboService weiboService;
	
    @RequestMapping(value = "/controllers/gateway", method = RequestMethod.GET)
    ResponseEntity<String> route(){
        return new ResponseEntity("{}", HttpStatus.OK);
    }
    
	private Map<String,String> map = new ConcurrentHashMap<String, String>();
	
	@RequestMapping(value="/qq_login", method=RequestMethod.GET)
	public void qqLogin(@RequestParam("callbackUrl")String custCallbackUrl, HttpServletResponse response){
		String state = Long.toString(System.currentTimeMillis());
		map.put(state, custCallbackUrl);
		String returnURL = qqService.getAuthorizationUrl(state);
		
		try{
			response.sendRedirect(returnURL);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/oauth/qq", method=RequestMethod.GET)
	public ResponseEntity<String> requestQQAccessToken(
			HttpServletRequest request, 
			@RequestParam("code")String code,
			@RequestParam("state")String state){
		String custCallbackUrl = "thinkinghub.org";
		if(state != null){
			custCallbackUrl = map.get(state);
		}
		String tokenStr = qqService.getResult(state, code);
		String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?" + tokenStr;
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.set(HttpHeaders.LOCATION, redirectUrl);
		return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
	}
	
	@RequestMapping(value="/weibo_login", method=RequestMethod.GET)
	public void weiboLogin(@RequestParam("callbackUrl")String custCallbackUrl, HttpServletResponse response){
		String state = Long.toString(System.currentTimeMillis());
		map.put(state, custCallbackUrl);
		String returnURL = weiboService.getAuthorizationUrl(state);
		try{
		response.sendRedirect(returnURL);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/oauth/sina", method=RequestMethod.GET)
	public ResponseEntity<String> requestWeiboAccessToken(
			HttpServletRequest request, 
			@RequestParam("code")String code,
			@RequestParam("state")String state){
		String custCallbackUrl = "thinkinghub.org";
		if(state != null){
			custCallbackUrl = map.get(state);
		}
		WeiboAccessToken token = weiboService.getResult(state, code);
		String redirectUrl = request.getScheme() + "://" + custCallbackUrl + "?uid=" + token.getUid();
		HttpHeaders responseHeader = new HttpHeaders();
		responseHeader.set(HttpHeaders.LOCATION, redirectUrl);
		return new ResponseEntity<String>("Success", responseHeader, HttpStatus.TEMPORARY_REDIRECT);
	}

}
