package org.thinkinghub.gateway.oauth.entity;

import java.time.ZonedDateTime;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class GatewayAuditable extends GatewayPersistable {

    private static final long serialVersionUID = -3641663563907196150L;

    @CreatedDate
    @JsonIgnore
    private ZonedDateTime createdAt;

    @LastModifiedDate
    @JsonIgnore
    private ZonedDateTime updatedAt;

    @CreatedBy
    @JsonIgnore
    private User createdBy;

    @LastModifiedBy
    @JsonIgnore
    private User updatedBy;

    public GatewayAuditable(Long id) {
        super(id);
    }

}
