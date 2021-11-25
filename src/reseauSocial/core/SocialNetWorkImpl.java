package reseauSocial.core;

public class SocialNetWorkImpl extends Member{

	private String nom;
	
	public void reseauSocial(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
