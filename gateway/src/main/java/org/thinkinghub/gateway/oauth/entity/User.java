package org.thinkinghub.gateway.oauth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="User")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class User extends GatewayPersistable {

    private static final long serialVersionUID = 7170397482816364599L;

    @Column(unique = true)
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
    public String toString() {
        return this.getName();
    }
    
    public User(Long id) {
        super(id);
    }
}
