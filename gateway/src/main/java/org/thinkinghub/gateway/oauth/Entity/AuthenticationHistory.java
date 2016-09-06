package org.thinkinghub.gateway.oauth.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AuthenticationHistory")
@Data
@NoArgsConstructor
public class AuthenticationHistory extends GatewayPersistable implements Serializable{

	private static final long serialVersionUID = 6893602210224518770L;
	
	@ManyToOne
	private User user;
	
    private Service service;
    
    private boolean isSuccess;
    private String errorCode;
    private String errorDesc;


}
