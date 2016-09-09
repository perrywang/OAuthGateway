package org.thinkinghub.gateway.oauth.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationHistory extends GatewayPersistable {

    private static final long serialVersionUID = 6893602210224518770L;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private ServiceType serviceType;
    
    private String callback;
    
    private String state;
    
    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    private String errorCode;

    private String errorDesc;

    public AuthenticationHistory(Long id) {
        super(id);
    }
}
