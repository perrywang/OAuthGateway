package org.thinkinghub.gateway.oauth.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MODELS", uniqueConstraints = @UniqueConstraint(columnNames = {"CODE"}))
@Data
@NoArgsConstructor
public class Model extends GatewayPersistable{
    public Model(Long id) {
		super(id);
	}

	private static final long serialVersionUID = 7817446544819117831L;
	private String code; //like Weixin, QQ, Weibo, etc.
    private String name;

    @ManyToMany
    private List<User> users = new ArrayList<>();

}
