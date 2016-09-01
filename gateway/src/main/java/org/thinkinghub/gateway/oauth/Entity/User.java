package org.thinkinghub.gateway.oauth.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS", uniqueConstraints = @UniqueConstraint(columnNames = {"CODE"}))
@Data
@NoArgsConstructor
public class User extends GatewayPersistable{
    public User(Long id) {
		super(id);
	}

	private static final long serialVersionUID = 7170397482816364599L;
	private String code; //note: this is the code for gateway system, must be unique.
    private String name;
    private String phone;
    private String address;
    private String type; //1: normal user, 2: VIP user
    private String callback;

    @ManyToMany(mappedBy = "users")
    private List<Model> jobs = new ArrayList<>();
}
