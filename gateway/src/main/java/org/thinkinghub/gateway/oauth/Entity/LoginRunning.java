package org.thinkinghub.gateway.oauth.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LoginRunning")
@Data
@NoArgsConstructor
public class LoginRunning extends GatewayAuditable<User>{
    @ManyToOne
    private User user;
    @ManyToOne
    private Model model;
    private boolean isSuccess;
    private String errorCode;
    private String errorDesc;

}
