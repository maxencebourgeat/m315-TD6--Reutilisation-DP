package facebookGhost;

import java.util.ArrayList;


public class UserImpl implements User {

	
	private String id;
	private String myProfile;
	private User.AgeRange range;
	private IdNameEntity homeTown;
	
	
	 class AgeRangeImpl implements User.AgeRange{

		int age = 20;
		public Integer getMin() {
			return 0;
		}

		public Integer getMax() {
			return 120;
		}

		public int getAge() {
			return age;
		}
		
	}
		
	 
	 public UserImpl(String id, String profile, String phomeTown) {
			this.id = id;
			myProfile = profile;
			range = new AgeRangeImpl();
			homeTown = new IdNameEntityImpl(phomeTown,phomeTown);
		}
		

	public UserImpl(String id, String profile) {
		this.id = id;
		myProfile = profile;
		range = new AgeRangeImpl();
	}

	public String myProfil() {
		return myProfile;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return id;
	}

	public AgeRange getAgeRange() {
		return range;
	}



	private ArrayList friends = new ArrayList ();
	
	public void addFriend(User friend) {
		friends.add(friend);
	}

	public ArrayList getFriends() {
		return friends;
	}
	
	private ArrayList family = new ArrayList ();
	
	public void addFamily(User familyMember) {
		family.add(familyMember);
	}
	
	public ArrayList getFamily(){
		return family;
	}

	@Override
	public String toString() {
		return "UserImpl [id=" + id + ", myProfile=" + myProfile + ", range="
				+ range + 
				//",friends=" + friends + ", family=" + family + 
				"]";
	}

	@Override
	public IdNameEntity getHometown() {
		return homeTown;
	}


	@Override
	public void setLocation(String name) {
		homeTown = new IdNameEntityImpl(name,name) ;
	}
	
	
}
