package facebookGhost;

public class IdNameEntityImpl implements IdNameEntity {

	private String id;
	private String name;
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	public IdNameEntityImpl(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


}
