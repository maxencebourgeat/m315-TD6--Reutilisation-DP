package facebookGhost;

public class RelationEvent implements Event {
	public String getNature() {
		return nature;
	}

	public User getU1() {
		return u1;
	}

	public User getU2() {
		return u2;
	}

	String nature;
	User u1;
	User u2;
	
	public RelationEvent(String nature, User u1, User u2) {
		this.nature = nature;
		this.u1 = u1;
		this.u2=u2;
	}

	@Override
	public String toString() {
		return "RelationEvent [nature=" + nature + ", u1=" + u1 + ", u2=" + u2
				+ "]";
	}

}
