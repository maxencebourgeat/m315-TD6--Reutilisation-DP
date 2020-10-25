package facebookGhost;

public class UserEvent implements Event {

	User user;
	
	public UserEvent(User user) {
		this.user = user;
	}

}
