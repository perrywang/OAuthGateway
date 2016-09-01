package org.thinkinghub.gateway.oauth.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@MappedSuperclass
public abstract class GatewayPersistable implements Serializable {

    private static final long serialVersionUID = -8503475553208415513L;

    @Id
    @GeneratedValue
    private Long id;
    
    public Long getId(){
    	return this.id;
    }

    @Transient
    public boolean isNew() {
        return null == getId();
    }

    public GatewayPersistable(Long id){
        this.id = id;
    }

}
