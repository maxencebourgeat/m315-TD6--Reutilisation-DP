package facebookGhost;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

/**
 *
 * Inspired by
 * //https://github.com/roundrop/facebook4j/blob/master/facebook4j-core/src/main/java/facebook4j/api/FriendMethods.java
 * pour imaginer une esquisse de reseau tres tres simplifie
 * 
 * @author blay
 *
 */
public class FacebookGhostNetwork {

	public static final String DEFAULT_LOCATION = "unspecified";
	HashMap<String, User> users = new HashMap<>();

	//To be observable
	private PropertyChangeSupport observables;

	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		observables.addPropertyChangeListener(pcl);
	}

	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		observables.removePropertyChangeListener(pcl);
	}

	//Constructor
	public FacebookGhostNetwork() {
		observables = new PropertyChangeSupport(this);
	}

	
	public User addUser(String nom, String profile) {
		return addUser(nom, profile, DEFAULT_LOCATION);
	}

	public User addUser(String nom, String profile, String location) {
		User user = new UserImpl(nom, profile, location);
		users.put(nom, user);
		Event e = new UserEvent(user);
		observables.firePropertyChange("addUser", null, e);
		return user;
	}

	/**
	 * Returns a user's friends.
	 * 
	 * @param userId the ID of a user
	 * @return users
	 * @see <a href=
	 *      "https://developers.facebook.com/docs/reference/api/user/#friends">User#friends
	 *      - Facebook Developers</a>
	 */
	ArrayList<User> getFriends(String userId) {
		return getUser(userId).getFriends();
	}

	/**
	 * Returns a user's family members.
	 * 
	 * @param userId the ID of a user
	 * @return users
	 * @see <a href=
	 *      "https://developers.facebook.com/docs/reference/api/user/#friends">User#friends
	 *      - Facebook Developers</a>
	 */
	ArrayList<User> getFamily(String userId) {
		return getUser(userId).getFamily();
	}

	/**
	 * Returns a given user, specified by ID.
	 * 
	 * @param userId the ID of the user
	 * @return user remove but not the use of dedicated exception !! @throws
	 *         FacebookException when Facebook service or network is unavailable
	 * @see <a href="https://developers.facebook.com/docs/reference/api/user/">User
	 *      - Facebook Developers</a>
	 */
	public User getUser(String userId) {
		return users.get(userId);
	}

	/**
	 * Create a relation between 2 given members of a same family
	 * 
	 * @param id1 the ID of the user
	 * @param id2 the ID of the user
	 */
	public void addFamilyRelation(String id1, String id2) {
		User u1 = users.get(id1);
		User u2 = users.get(id2);
		addFamilyRelation(u1, u2);

	}

	public void addFamilyRelation(User u1, User u2) {
		u1.addFamily(u2);
		u2.addFamily(u1);
		Event e = new RelationEvent("family", u1, u2);
		observables.firePropertyChange("addFamilyRelation", null, e);

	}

	/**
	 * Create a relation between 2 given friends
	 * 
	 * @param id1 the ID of the user
	 * @param id2 the ID of the user
	 */
	public void addFriendRelation(String id1, String id2) {
		User u1 = users.get(id1);
		User u2 = users.get(id2);
		addFriendRelation(u1, u2);
	}

	public void addFriendRelation(User u1, User u2) {
		u1.addFriend(u2);
		u2.addFriend(u1);
		Event e = new RelationEvent("Friend", u1, u2);
		observables.firePropertyChange("addFriendRelation", null, e);

	}

	@Override
	public String toString() {
		return "FacebookGhostNetwork [ " + users + "]";
	}

}
