package reseauSocial.core;

public class Member {
	
	private String nom;
	private String localisation;
	
	public void Mumber(String nom, String localisation) {
		this.nom = nom;
		this.localisation = localisation;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getLocalisation() {
		return localisation;
	}

	public void setLocalisation(String localisation) {
		this.localisation = localisation;
	}

}
