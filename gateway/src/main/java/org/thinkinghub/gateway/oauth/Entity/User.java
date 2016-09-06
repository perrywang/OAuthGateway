package org.thinkinghub.gateway.oauth.Entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = {"KEY"}))
@Data
@NoArgsConstructor
public class User extends GatewayPersistable implements Serializable{

	private static final long serialVersionUID = 7170397482816364599L;
	private String key; //note: this is the key for gateway system, must be unique.
    private String name;
    private String phone;
    private String address;
    private String type; //1: normal user, 2: VIP user
    private String callback;
    
//    private List<Service> services = new ArrayList<>();
}
