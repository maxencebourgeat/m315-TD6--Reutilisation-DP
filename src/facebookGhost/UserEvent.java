package facebookGhost;

public class UserEvent implements Event {

	User user;
	
	public User getUser() {
		return user;
	}
	
	public UserEvent(User user) {
		this.user = user;
	}

}
