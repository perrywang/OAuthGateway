package org.thinkinghub.gateway.oauth.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@MappedSuperclass
public abstract class GatewayPersistable {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
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
