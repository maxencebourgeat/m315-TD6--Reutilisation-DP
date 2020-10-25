package grapheX;

/**
 * Classe d'arcs
 * 
 * @author FMorain (morain@lix.polytechnique.fr)
 * @author PChassignet (chassign@lix.polytechnique.fr)
 * @author JCervelle (julien.cervelle@univ-mlv.fr)
 * @version 2007.03.21
 */

// L'arc o -> d avec valeur val
public class Arc<S extends Identifiable> implements Comparable<Arc<S>> {
  private S o, d;

  private int val;

  public Arc(S o0, S d0, int val0) {
    this.o = o0;
    this.d = d0;
    this.val = val0;
  }

  public Arc(S o0, S d0) {
    this.o = o0;
    this.d = d0;
    this.val = 0;
  }

  public Arc(Arc<S> a) {
    this.o = a.o;
    this.d = a.d;
    this.val = a.val;
  }

  public S destination() {
    return d;
  }

  public S origine() {
    return o;
  }

  public int valeur() {
    return val;
  }

  public void modifierValeur(int vv) {
    this.val = vv;
  }

  @Override
public String toString() {
    return "(" + val + ":" + this.o + ", " + this.d + ")";
  }

  @Override
public int hashCode() {
    int codeOri = (o == null ? 0 : o.hashCode());
    int codeDst = (d == null ? 0 : d.hashCode());
    return codeDst ^ (codeOri * 31);
  }

  @Override
public boolean equals(Object aa) {
    if (!(aa instanceof Arc))
      return false;
    Arc<?> arc = (Arc<?>) aa;
    boolean equalsO = o == null && arc.o == null || o != null
        && o.equals(arc.o);
    boolean equalsD = d == null && arc.d == null || d != null
        && d.equals(arc.d);
    return equalsO && equalsD && (val == arc.val);
  }

public int compareTo(Arc<S> a) {
    int comp = Identifiable.compare(this.o, a.o);
    if (comp == 0)
      comp = Identifiable.compare(d, a.d);
    return comp;
  }

}
