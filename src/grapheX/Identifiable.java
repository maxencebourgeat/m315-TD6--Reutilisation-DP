package grapheX;

/**
 * Définit des objets identifiés par une <tt>String</tt>. Deux objets
 * <tt>Identifiable</tt> qui sont construit avec le même identifiant seront
 * considérés comme identiques pour les méthodes <tt>hashCode</tt>,
 * <tt>equals</tt> et <tt>compareTo</tt>. Cette classe sert notamment de
 * super-classe pour les sommets d'un graphe.
 * 
 * @author PChassignet (chassign@lix.polytechnique.fr)
 * @version 2007.03.21
 */

public class Identifiable implements Comparable<Identifiable> {
  private final String ident;

  /**
   * @param identifiant
   *          l'identifiant pour cet objet.
   */
  public Identifiable(String identifiant) {
    ident = identifiant;
  }

  /**
   * @return l'identifiant pour cet objet.
   */
  public final String identifiant() {
    return ident;
  }

  /**
   * @return le <tt>hashCode</tt> de l'identifiant de cet objet.
   */
  @Override
public final int hashCode() {	
    if (ident == null)
      return 0;
    else
      return ident.hashCode();
  }

  /**
   * @return le résultat de <tt>equals</tt> sur les identifiants.
   */
  @Override
public final boolean equals(Object o) {
    if (!(o instanceof Identifiable)) {
      return false;
    }
    String oid = ((Identifiable) o).ident;
    if (ident == null)
      return oid == null;
    else
      return ident.equals(oid);
  }

  /**
   * @return le résultat de <tt>compareTo</tt> sur les identifiants.
   */
public final int compareTo(Identifiable id) {	 	 
    if (ident == null)
      if (id.ident == null)
        return 0;
      else
        return -1;
    else if (id.ident == null)
      return 1;
    else
      return ident.compareTo(id.ident);
  }

  /**
   * @param id1
   *          premier objet à comparer,
   * @param id2
   *          second objet à comparer,
   * @return le résultat de <tt>id1.compareTo(id2)</tt>.
   */
  public static final int compare(Identifiable id1, Identifiable id2) {
    if (id1 == null)
      if (id2 == null)
        return 0;
      else
        return -1;
    else if (id2 == null)
      return 1;
    else
      return id1.compareTo(id2);
  }

  @Override
public String toString() {
    if (ident == null)
      return "[null]";
    else
      return "[\"" + ident + "\"]";
  }

}
