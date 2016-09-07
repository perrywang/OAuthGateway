package org.thinkinghub.gateway.oauth.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.thinkinghub.gateway.oauth.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class User extends GatewayPersistable implements Serializable{

	private static final long serialVersionUID = 7170397482816364599L;
	
	@Column(unique=true)
	private String key;
	
    private String name;
    
    private String phone;
    
    private String address;
    
    @Enumerated(EnumType.STRING)
    private UserType type = UserType.NORMAL;

    private String callback;
    
    public User(String name) {
        this.name = name;
    }
    
    @Override
    public String toString(){
        return this.getName();
    }
}
