package grapheX;

/**
 * Classe de sommets, toutes les propriétés sont héritées de Identifiable.
 * 
 * @author FMorain (morain@lix.polytechnique.fr)
 * @author PChassignet (chassign@lix.polytechnique.fr)
 * @version 2007.03.21
 */

public class Sommet extends Identifiable {

  public Sommet(String nn) {
    super(nn);
  }

  @Override
public String toString() {
    return identifiant();
  }

  
}
