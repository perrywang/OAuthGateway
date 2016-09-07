package org.thinkinghub.gateway.oauth.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@MappedSuperclass
public abstract class GatewayPersistable implements Serializable {

    private static final long serialVersionUID = 8131162261220407002L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public GatewayPersistable(Long id) {
        this.id = id;
    }
}
