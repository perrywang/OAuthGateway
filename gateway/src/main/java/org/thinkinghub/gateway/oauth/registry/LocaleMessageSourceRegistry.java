package org.thinkinghub.gateway.oauth.registry;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LocaleMessageSourceRegistry {
	private static LocaleMessageSourceRegistry self;

	@Resource
	private MessageSource messageSource;
	
	@PostConstruct
	public void initialize(){
		self = this;
	}
	
	public static LocaleMessageSourceRegistry instance(){
		return self;
	}
	
	public String getMessage(String msg){
		return getMessage(msg,null);
	}
	
	public String getMessage(String code,Object[] args){
	       return getMessage(code, args,"");
	    }
	
    public String getMessage(String code,Object[] args,String defaultMessage){
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
     }
}
