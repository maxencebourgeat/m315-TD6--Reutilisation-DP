package grapheSimple;

import grapheX.Arc;
import grapheX.Identifiable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * La classe Chemin définit un chemin comme un ensemble d'arcs et maintient pour
 * chaque chemin la somme des valeurs des arcs
 * 
 * @author blay
 * @param <S>
 *
 */
public class Chemin<S extends Identifiable> implements Comparable<Chemin<S>> {

	// On pourrait éviter cet attribut en calculant la distance à la demande.
	int distance = 0;

	List<Arc<S>> paths = new ArrayList<>();

	public Chemin() {
		paths = new ArrayList<>();
	}

	public Chemin(List<Arc<S>> arcs) {
		paths = arcs;
	}

	/**
	 * @return le Sommet terminant le chemin si le chemin est vide il vaut null
	 */
	public S arrivee() {
		if (paths.isEmpty())
			return null;
		Arc<S> arc = paths.get(paths.size() - 1);
		return arc.destination();
	}

	/**
	 * @return la somme des valeurs des arcs
	 */
	public int distance() {
		return distance;
	}

	/**
	 * @return la liste des arcs qui composent le chemin Attention dangereux une
	 *         copie serait préférable
	 */
	public List<Arc<S>> getArcs() {
		return paths;
	}

	/**
	 * Ajoute un arc � la fin du chemin
	 * 
	 * @TODO : verifier que le dernier noeud est bien le premier noeud de l'arc
	 *       ajoute
	 */
	public boolean add(Arc<S> e) {
		distance += e.valeur();
		return paths.add(e);
	}

	public void add(int x, Arc<S> e) {
		distance += e.valeur();
		paths.add(x, e);
	}

	public boolean addAll(Collection<Arc<S>> c) {
		for (Arc<S> a : c)
			distance += a.valeur();
		return paths.addAll(c);
	}

	public void clear() {
		distance = 0;
		paths.clear();

	}

	/**
	 * verifie l'appartenance d'un arc au chemin
	 * 
	 * @param arc
	 * @return vrai si l'arc appartient au chemin
	 */
	public boolean contains(Arc<S> arc) {
		return paths.contains(arc);
	}

	public boolean isEmpty() {
		return paths.isEmpty();
	}

	public boolean remove(Arc<S> o) {
		return paths.remove(o);
	}

	public boolean removeAll(Collection<Arc<S>> c) {
		return paths.removeAll(c);
	}

	public int size() {
		return paths.size();
	}

	@Override
	public String toString() {
		return "Chemin [dist.=" + distance + ", paths=" + paths + "]";
	}

	/**
	 * d�termine si le sommet appartient au chemin
	 * 
	 * @param sommet
	 * @return vrai si le sommet appartient au chemin
	 */
	public boolean atteint(S sommet) {
		for (Arc<S> a : paths)
			if (a.destination().equals(sommet))
				return true;
		return false;
	}

	/**
	 * @param depart
	 * @param arrivee
	 * @return le sous-chemin reliant depart et arrivee si les deux noeuds
	 *         appartiennent au chemin.
	 * 
	 */
	public Chemin<S> extraireChemin(S depart, S arrivee) {
		boolean debutee = false;
		Chemin<S> c = new Chemin<>();
		for (Arc<S> a : paths) {
			if (debutee) {
				c.add(a);
			}
			if (a.origine().equals(depart)) {
				c.add(a);
				debutee = true;
			}
			if (debutee && a.destination().equals(arrivee))
				return c;
		}
		return c;

	}

	public int compareTo(Chemin<S> c) {
		if (this.distance() < c.distance())
			return -1;
		else if (this.distance() == c.distance())
			return 0;
		else
			return 1;

	}

	public boolean equals(Object o) {
		if (!(o instanceof Chemin))
			return false;
		
		Chemin<S> o2 = (Chemin<S>) o;
		Chemin<S> c = o2;
		if ((distance == c.distance) && (c.paths.size() == this.paths.size())) {
			for (Arc<S> a : c.paths) {
				if (!paths.contains(a)) {
					return false;
				}
			}
			return true;
		} 
		else
			return false;

	}

	public List<S> sommets() {
		List<S> sommets = new ArrayList<>();
		if (! paths.isEmpty()) {
			for (Arc<S> arc : paths)
				sommets.add(arc.origine());
			sommets.add(paths.get(paths.size() - 1).destination());
		}
		return sommets;
	}

}
