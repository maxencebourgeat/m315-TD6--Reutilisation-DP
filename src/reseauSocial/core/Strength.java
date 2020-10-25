package reseauSocial.core;

public enum Strength {
	LOW (4), MEDIUM (3), HIGH (2), STRONG(1);
	
	int value;
	
	public int getValue() {
		return value;
	}

	Strength( int pvalue){
		value = pvalue;
	}
	
	
}
