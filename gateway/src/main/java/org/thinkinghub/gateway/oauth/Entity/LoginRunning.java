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
    public LoginRunning(Long id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 6893602210224518770L;
	@ManyToOne
    private User user;
    @ManyToOne
    private Model model;
    private boolean isSuccess;
    private String errorCode;
    private String errorDesc;
	@Override
	public User getCreatedBy() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setCreatedBy(User createdBy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public User getLastModifiedBy() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setLastModifiedBy(User lastModifiedBy) {
		// TODO Auto-generated method stub
		
	}

}
