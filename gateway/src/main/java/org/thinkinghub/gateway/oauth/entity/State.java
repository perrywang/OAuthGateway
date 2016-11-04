package org.thinkinghub.gateway.oauth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Data
@Table(indexes = { @Index(unique = true, columnList = "key") })
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class State extends GatewayPersistable {
     String key;
}
