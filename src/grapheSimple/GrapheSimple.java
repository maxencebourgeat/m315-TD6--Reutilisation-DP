package grapheSimple;

import grapheX.Arc;
import grapheX.Graphe;
import grapheX.Sommet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Cette classe a ete necessaire pour gérer les graphes ayant plusieurs arétes entre deux sommets données
 * et ayant des arcs dans les deux sens entre deux sommets.
 * Ces fonctionnalités n'étaient pas prises en charge par les classes initiales.
 * Elle permet egalement de simplifier la comprehension des codes données.
 * @author blay
 *
 */
public class GrapheSimple<T extends Sommet> extends Graphe<T> {
    HashMap<String,T> mesSommets = new HashMap<>();

    HashMap<T,HashMap<T,ArrayList<Arc<T>>>> aretes;


    public  GrapheSimple() {
        aretes = new HashMap<>();
    }


    public T getSommet(String ident){
        return mesSommets.get(ident);
    }

    @Override
    public int taille() {
        return mesSommets.size();
    }


    @Override

    public Graphe<T> copie() {
        //@TODO
        return null;
    }

    //n'ajoute le sommet que s'il n'est pas deja dans le graphe.
    @Override
    public void ajouterSommet(T s) {
        if (existeSommet(s))
            return;
        mesSommets.put(s.identifiant(), s);
        aretes.put(s,new HashMap<T,ArrayList<Arc<T>>>());
    }

    @Override
    public boolean existeArc(Sommet s, Sommet t) {
        return aretes.get(s).containsKey(t);
    }


    //A revoir avec la nouvelle version
    //@todo
    private boolean existeSommet(T s) {
        return aretes.containsKey(s);
    }


    public List<Arc<T>> arcs(T s, T t) {
        return aretes.get(s).get(t);
    }

    @Override
    public void ajouterArc(T s, T t) {
        this.ajouterArc(s,t,0);
    }


    @Override
    public void ajouterArc(Arc<T> arc) {
        HashMap<T, ArrayList<Arc<T>>> s = aretes.get(arc.origine());
        if (s == null){
            ajouterSommet(arc.origine());
            s=aretes.get(arc.origine());
        }
        if (aretes.get(arc.destination()) == null)
            ajouterSommet(arc.destination());
        if  (s.get(arc.destination()) == null)
            s.put(arc.destination(), new ArrayList<>());
        s.get(arc.destination()).add(arc);

    }
    @Override
    public void ajouterArc(T s, T t, int val) {
        Arc<T> a = new Arc<>(s,t,val);
        this.ajouterArc(a);
    }

    //Remarque : gestion horrible des exceptions mais on s'adapte à cause de l'héritage qui nous interdit de lever une exception.
    @Override
    public int valeurArc(T s, T t)  {
        if (!existeArc(s,t)) throw new Error("Arc inexistant");
        return aretes.get(s).get(t).get(0).valeur();
    }

    //RETIRE TOUS LES ARCS VERS T
    @Override
    public void enleverArc(Sommet s, Sommet t) {
        if (!existeArc(s,t)) return ;
        aretes.get(s).remove(t);
    }

    @Override
    public Collection<T> sommets() {
        return mesSommets.values();
    }

    @Override
    public Collection<Arc<T>> voisins(Sommet s) {
        ArrayList<Arc<T>> voisins = new ArrayList<>();
        HashMap<T, ArrayList<Arc<T>>> arcs = aretes.get(s);
        if ( arcs != null )
            for (ArrayList<Arc<T>> av : arcs.values())
                voisins.addAll(av);
        return voisins ;
    }

    @Override
    public String toString() {
        return "Sommets=" + mesSommets + ";\n arcs="
                + toStringArretes(aretes) + "]";
    }

    private String toStringArretes(
            HashMap<T, HashMap<T, ArrayList<Arc<T>>>> aretes2) {
        StringBuilder bld = new StringBuilder();
        for ( HashMap<T, ArrayList<Arc<T>>> x : aretes2.values()){
            for ( ArrayList<Arc<T>> edges : x.values())
                for (Arc<T> a : edges)
                    bld.append( "\t").append(a).append("\n" );
        }
        return bld.toString();
    }


    /**
	 * @param origine
	 * @return une liste de chemins
	 * Cette m�thode renvoie les chemins les plus longs possibles � partir du point d'orgine
	 */
	public List<Chemin<T>> chemins(T origine){
		HashMap<T,ArrayList<Arc<T>>> dejaVu = new HashMap<>();
		return chemins(origine,dejaVu);
	}
	
	/**
	 * @param origine
	 * @param destination
	 * @return une liste de chemins entre deux Ts
	 * Cette m�thode renvoie tous les chemins entre deux Sommets donnes
	 */
	//Pbme avec la comparaison des chemins... qui considere comme �gal deux objets diff�rents... cela doit venir de l'h�ritage...

	public List<Chemin<T>> chemins(T origine, T destination){
		List<Chemin<T>> chemins = chemins(origine);
		List<Chemin<T>> cheminsEntreDeuxSommets = new ArrayList<> ();
		for(Chemin<T> c : chemins) {
			if (c.atteint(destination)){
				Chemin<T> raccourcis = c.extraireChemin(origine, destination);
				if (! cheminsEntreDeuxSommets.contains(raccourcis)) {
					cheminsEntreDeuxSommets.add(raccourcis);
				}
			}
		}
		return cheminsEntreDeuxSommets;
	}
	

	private List<Chemin<T>> chemins(T origine, HashMap<T,ArrayList<Arc<T>>> dejaVu){

		List<Chemin<T>> chemins = new ArrayList<>();

		if  (dejaVu.containsKey(origine)){
			chemins.add(new Chemin<>(dejaVu.get(origine)));
			return chemins;
		}

		
		dejaVu.put(origine, new ArrayList<Arc<T>>());


		Collection<Arc<T>> voisins = voisins(origine);	
		HashMap<T,ArrayList<Arc<T>>> dejavVuLocal ;

		for (Arc<T> a : voisins) {
			T destination = a.destination();
			dejavVuLocal= new HashMap<>(dejaVu);

			if (nouvelleDestinationOuNouvelArcSansRetour(origine,dejavVuLocal,destination,a)) { 
				dejavVuLocal.get(origine).add(a);
				List<Chemin<T>> cheminsLocaux = chemins(destination,dejavVuLocal);
				if (cheminsLocaux.isEmpty()) {
					Chemin<T> chemin = new Chemin<>();
					chemin.add(a);
					chemins.add(chemin);
					}
				else {
					for (Chemin<T> c : cheminsLocaux) {
						c.add(0,a);
						chemins.add(c);
					}
				}
			}
		}
		return chemins;
	}
	
	//todo : tenir compte de Origine
		private boolean nouvelleDestinationOuNouvelArcSansRetour(
			T origine, HashMap<T, ArrayList<Arc<T>>> dejaVu, T destination,
			Arc<T> a) {

			if (! dejaVu.containsKey(destination) )
				return true;

			return ( (! dejaVu.get(destination).contains(a)) && (! dejaVu.containsKey(a.destination()) ) );
		}
		
		
		public Chemin<T> cheminLePlusCourt(T origine, T destination){
			List<Chemin<T>> chemins = this.chemins(origine, destination);
			Chemin<T> cheminLePlusCourt = null;
			int distanceLaPlusCourte = Integer.MAX_VALUE;
			for(Chemin<T> c : chemins) {
				if (distanceLaPlusCourte > c.distance()) {
					distanceLaPlusCourte = c.distance();
					cheminLePlusCourt = c;
				}
			}
			return cheminLePlusCourt;
		}

		/**
		 * @param origine
		 * @param rang
		 * @return l'ensemble des voisins de {@code origine} au rang {@code rang}, i.e. qu'il y a {@code rang} arcs entre eux.
		 */
		public Set<T> voisinsAuRang(T origine, int rang){
			List<Chemin<T>> chemins = chemins(origine);
			Set<T> tsVoisinsDejaVu = new TreeSet<>();
			Set<T> tsDeBonRang = new TreeSet<>();
			for (Chemin<T> c : chemins) {
				List<T> sommets = c.sommets();
				int i = 0;
				for (i = 0; (i <sommets.size() && i < rang); i++)
					tsVoisinsDejaVu.add(sommets.get(i));
				if ( (i == rang) && (i < sommets.size()) )
					tsDeBonRang.add(sommets.get(i));
				}
			tsDeBonRang.removeAll(tsVoisinsDejaVu);
			return tsDeBonRang;
		}

}
