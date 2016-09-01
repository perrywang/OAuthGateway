package org.thinkinghub.gateway.oauth.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "FORMAT", uniqueConstraints = @UniqueConstraint(columnNames = {"CODE"}))
@Data
@NoArgsConstructor
public class Format extends GatewayPersistable{
    private String code;
    private String name; //like json, xml, etc.
}
