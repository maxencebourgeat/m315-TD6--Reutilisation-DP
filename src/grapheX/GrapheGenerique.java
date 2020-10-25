package grapheX;

import java.util.Collection;

/**
 * Super-classe abstraite des graphes, les sommets doivent �tre identifiables.
 * 
 * @author FMorain (morain@lix.polytechnique.fr)
 * @author PChassignet (chassign@lix.polytechnique.fr)
 * @version 2007.03.21
 */

public abstract class GrapheGenerique<S extends Identifiable> {

  /**
   * @return le nombre de sommets de ce graphe.
   */
  public abstract int taille();

  /**
   * @param s
   *          le sommet � ajouter � ce graphe.
   */
  public abstract void ajouterSommet(S s);

  /**
   * @return une <tt>Collection</tt> de tous les sommets de ce graphe.
   */
  public abstract Collection<S> sommets();

  /**
   * Teste l'existence de l'arc de <tt>s</tt> � <tt>t</tt> dans ce graphe.
   * 
   * @param s
   *          l'origine de l'arc,
   * @param t
   *          l'extr�mit� de l'arc.
   */
  public abstract boolean existeArc(S s, S t);

  /**
   * @param s
   *          l'origine de l'arc,
   * @param t
   *          l'extr�mit� de l'arc,
   * @param val
   *          une valeur enti�re attach�e � l'arc de <tt>s</tt> � <tt>t</tt>
   *          dans ce graphe.
   */
  public abstract void ajouterArc(S s, S t, int val);

  /**
   * @param s
   *          l'origine de l'arc,
   * @param t
   *          l'extr�mit� de l'arc.
   * @return la valeur enti�re attach�e � l'arc de <tt>s</tt> � <tt>t</tt>
   *         dans ce graphe.
   */
  public abstract int valeurArc(S s, S t);

  /**
   * Supprime l'arc de <tt>s</tt> � <tt>t</tt> dans ce graphe.
   * 
   * @param s
   *          l'origine de l'arc,
   * @param t
   *          l'extr�mit� de l'arc.
   */
  public abstract void enleverArc(S s, S t);

  /**
   * @param s
   *          l'origine des arcs.
   * @return une <tt>Collection</tt> de tous les arcs de ce graphe ayant
   *         <tt>s</tt> pour origine. Ces arcs sont de type
   *         <tt>Arc&lt;S&gt;</tt>.
   */
  public abstract Collection<Arc<S>> voisins(S s);
  
}
