package org.thinkinghub.gateway.oauth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
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

    @Enumerated(EnumType.STRING)
    private ErrorType errorType;

    private String callback;
    
    private String state;
    
    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    private String gwErrorCode;

    private String errorCode;

    private String errorDesc;
    
    @Column(name="rawResponse")
    @Lob
    private String rawResponse;


    public AuthenticationHistory(Long id) {
        super(id);
    }
}
