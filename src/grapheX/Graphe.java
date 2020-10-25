package grapheX;

import java.util.*;

/**
   Classe abstraite de graphes


   @author FMorain (morain@lix.polytechnique.fr)
   @version 2008.03.01
   @author MBF revisée pour la rendre générique
 */

public abstract class Graphe<T extends Sommet> extends GrapheGenerique<T>{

  @Override
public abstract int taille();
 public abstract Graphe<T> copie();

  @Override
public abstract void ajouterSommet(T s);
  @Override
public abstract boolean existeArc(T s, T t);
  @Override
public abstract void ajouterArc(T s, T t, int val);
  public abstract void ajouterArc(Arc<T> arc);
  @Override
public abstract int valeurArc(T s, T t);
  @Override
public abstract void enleverArc(T s, T t);

  @Override
public abstract Collection<T> sommets();
  @Override
public abstract Collection<Arc<T>> voisins(T s);
  
  public boolean oriente;
  
  public static String version(){
  	return "grapheX -- version 1.3d, 2008/02/27";
  }

  public void ajouterArc(T s, T t){
    ajouterArc(s, t, 0);
  }
  
  public boolean existeArc(Arc<T> alpha){
    return existeArc(alpha.origine(), alpha.destination());
  }
  


public String toString(){
    String str = "";

    for(T s : sommets()){
      str += s + ":";
      for(T t : sommets()){
        if(existeArc(s, t))
          str += " " + valeurArc(s, t);
        else
          str += " -";
      }
      str += "\n";
    }
    return str;
  }

 /* public void faireComplet(){
    int n = taille();
    for(int i = 0; i < n; i++)
      ajouterSommet(new Sommet(i+""));
    for(Sommet s : sommets())
      for(Sommet t : sommets())
        if(!s.equals(t))
          ajouterArc(s, t, 1);
  }

  public static void afficherParcours(HashMap<Sommet,Integer> L){
	System.err.println("PARCOURS");
    for(Sommet s : L.keySet())
      System.err.print(" ["+s+", "+L.get(s)+"]");
    System.err.println();
  }

  public static Graphe RoyWarshall(Graphe G){
    Graphe F = G.copie();

    for(Sommet s : G.sommets())
      F.ajouterArc(s, s, 1);
    System.err.println("FT_ini = \n"+F);
    for(Sommet r : G.sommets()){
      for(Sommet s : G.sommets())
        for(Sommet t : G.sommets())
          if(!F.existeArc(s, t)
              && (F.existeArc(s, r) && F.existeArc(r, t)))
            F.ajouterArc(s, t, 1);
      System.err.println("\nA_"+r);
      System.err.println(F);
    }
    return F;
  }*/

  //////////////////// Dijkstra ////////////////////

  public HashMap<T,Integer> Dijkstra(T s){
    int INFINITY = 1000000;
    HashMap<T,Integer> L = new HashMap<T,Integer>();

    // 1. initialisation
    for(T t : sommets())
      if(! t.equals(s))
        if(existeArc(s, t))
          L.put(t, valeurArc(s, t));
        else
          L.put(t, INFINITY);
    // 2. 
    HashSet<T> U = new HashSet<>(sommets()); // copie
    L.put(s, 0);
    U.remove(s);
    // 3.
    while(! U.isEmpty()){
      //afficherParcours(L);
      // 4.
      T v = null;
      int Lv = INFINITY;
      for(T t : U)
        if(L.get(t) < Lv){
          v = t;
          Lv = L.get(t);
        }
      System.err.println("-->v="+v);
      // 5.
      U.remove(v);
      // 6.
      //	    for(Sommet t : U)
      //		if(existeArc(v, t)){
      for(Arc<T> a : voisins(v)){
        T t = a.destination();
        int tmp = Lv + a.valeur();
        if(tmp < L.get(t))
          L.put(t, tmp);
      }
    }
    return L;
  }
/*
  TreeSet<SommetValue> Dijkstra2Init(HashMap<T,Integer> L, T s){
    int INFINITY = 1000000;
    TreeSet<SommetValue> fp = 
      new TreeSet<SommetValue>(
          new Comparator<SommetValue>() {
            public int compare(SommetValue s1, SommetValue s2) {
              if(s1.valeur < s2.valeur)
                return -1;
              else if(s1.valeur > s2.valeur)
                return 1;
              if(s1.s.equals(s2.s))
                return 0;
              return -1;
            }
          }
      );
    for(Sommet u : sommets()){
      if(u.equals(s))
        L.put(u, 0);
      else if(existeArc(s, u)){
        int val = valeurArc(s, u);
        fp.add(new SommetValue(u, val));
        L.put(u, val);
      }
      else{
        fp.add(new SommetValue(u, INFINITY));
        L.put(u, INFINITY);
      }
    }
    return fp;
  }

  public HashMap<T,Integer> Dijkstra2(T s){
    HashMap<T,Integer> L = new HashMap<T,Integer>();

    // 1. initialisation
    TreeSet<SommetValue> U = Dijkstra2Init(L, s);
    // 3.
    while(! U.isEmpty()){
      //afficherParcours(L);
      // 4.
      SommetValue v = U.first();
      U.remove(v);
      int Lv = v.valeur;
      //System.err.println("v="+v.s+" Lv="+Lv);
      // 5.
      // 6.
      for(Arc<T> a : voisins(v.s)){
        Sommet t = a.destination();
        int tmp = Lv + a.valeur();
        if(tmp < L.get(t)){
        //  System.err.println("-"+t+" "+L.get(t));
          U.remove(new SommetValue(t, L.get(t)));
         // System.err.println("+"+t+" "+tmp);
          U.add(new SommetValue(t, tmp));
          L.put(t, tmp);
        }
      }
    }
    return L;
  }

  public void Prim(){
    HashSet<Sommet> HS = new HashSet<Sommet>();
    for(Sommet u : sommets()){
      if(! HS.contains(u)){
        //System.err.println("Je pars de "+u);
        HS.add(u);
        TreeSet<Arc<Sommet>> fp =
          new TreeSet<Arc<Sommet>>(
              new Comparator<Arc<Sommet>>(){
				public int compare(Arc<Sommet> a1, Arc<Sommet> a2){
                  if(a1.valeur() < a2.valeur())
                    return -1;
                  else if(a1.valeur() > a2.valeur())
                    return 1;
                  else if(a1.equals(a2))
                    return 0;
                  else if(! a1.origine().equals(a2.origine()))
                    return a1.origine().compareTo(a2.origine());
                  else if(! a1.destination().equals(a2.destination()))
                    return a1.destination().compareTo(a2.destination());
                  else
                    return 0;
                }
              }
          );
        for(Arc<Sommet> a : voisins(u)){
          //System.err.println("fp += "+a+" ["+a.valeur()+"]");
          fp.add(a);
        }
        while(! fp.isEmpty()){
          System.err.println("FP_BEGIN");
          for(Arc<Sommet> z : fp){
            System.err.println(z+" "+z.valeur());
          }
          System.err.println("FP_END");
          Arc<Sommet> a = fp.first();
          fp.remove(a);
          Sommet t = a.destination();
          System.err.print("Je regarde "+a+"["+a.valeur()+"] : ");
          if(HS.contains(t))
            System.err.println("il forme un cycle");
          else{
            System.err.println("je l'ajoute dans T");
            HS.add(t);
            for(Arc<Sommet> b : voisins(t)){
              System.err.println("fp += "+b+" ["+b.valeur()+"]");
              fp.add(b);
            }
          }
        }
      }
    }
  }

  //////////////////// BFS ////////////////////


  final static int inexplore = 0, encours = 1, explore = 2;

  public void bfs(HashMap<Sommet,Integer> etat, Sommet s){
    LinkedList<Sommet> f = new LinkedList<Sommet>();

    etat.put(s, encours);
    f.addLast(s);
    while(! f.isEmpty()){
      Sommet t = f.removeFirst();
      System.err.println("J'explore "+t);
      for(Arc<Sommet> a : voisins(t)){
        Sommet u = a.destination();
        if(etat.get(u) == inexplore){
          etat.put(u, encours);
          f.addLast(u);
        }
      }
      etat.put(t, explore);
    }
  }

  public HashMap<Sommet,Integer> bfsDistance(Sommet s){
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
    LinkedList<Sommet> f = new LinkedList<Sommet>();
    HashMap<Sommet,Integer> distance = 
      new HashMap<Sommet,Integer>();
    HashMap<Sommet,Integer> numero =
      new HashMap<Sommet,Integer>();
    int num = 0;

    for(Sommet t : sommets()){
      etat.put(t, inexplore);
      distance.put(t, 0);
      numero.put(t, 0);
    }
    etat.put(s, encours);
    f.addLast(s);
    numero.put(s, num++);
    while(! f.isEmpty()){
      Sommet t = f.removeFirst();
      System.err.println("J'explore "+t);
      for(Arc<Sommet> a : voisins(t)){
        Sommet u = a.destination();
        if(etat.get(u) == inexplore){
          etat.put(u, encours);
          f.addLast(u);
          numero.put(u, num++);
          System.err.println("num["+u+"]="+numero.get(u));
          distance.put(u, distance.get(t) + 1);
        }
      }
      etat.put(t, explore);
    }
    return distance;
  }

  public void composantesConnexesBFS(){
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
    for(Sommet s : sommets())
      etat.put(s, inexplore);
    int ncc = 0;
    for(Sommet s : sommets())
      if(etat.get(s) == inexplore){
        ncc++;
        System.err.println("Composante "+ncc);
        bfs(etat, s);
      }
  }
  
  public boolean bfsBiparti(HashMap<Sommet,Integer> etat, HashMap<Sommet,Integer> couleur, Sommet s){
    LinkedList<Sommet> f = new LinkedList<Sommet>();
    int c = 0; // la couleur

    etat.put(s, encours);
    couleur.put(s, c);
    f.addLast(s);
    while(! f.isEmpty()){
      Sommet t = f.removeFirst();
      System.err.println("J'explore "+t+" de couleur "+couleur.get(t));
      // c <- couleur des voisins de t
      c = 1 - couleur.get(t);
      for(Arc<Sommet> a : voisins(t)){
        Sommet u = a.destination();
        if(etat.get(u) == inexplore){
          etat.put(u, encours);
          couleur.put(u, c);
          f.addLast(u);
        }
        else if(couleur.get(u) != c){
          System.err.println(u+" est de'ja` colorie' avec la mauvaise couleur");
          return false;
        }
      }
      etat.put(t, explore);
    }
    return true;
  }

  // n'a vraiment un sens que sur les graphes non oriente's connexes
  public HashMap<Sommet,Integer> estBiparti(){
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
    HashMap<Sommet,Integer> couleur = new HashMap<Sommet,Integer>();
    for(Sommet s : sommets())
      etat.put(s, inexplore);
    int ncc = 0;
    for(Sommet s : sommets())
      if(etat.get(s) == inexplore){
        ncc++;
        System.err.println("Composante "+ncc);
        if(!bfsBiparti(etat, couleur, s))
          // le graphe n'est pas biparti
          return null;
      }
    return couleur;
  }

////////////DFS

  public void dfs(HashMap<Sommet,Integer> etat, Sommet s){
    LinkedList<Sommet> F = new LinkedList<Sommet>();
    int rg = -1;

    F.addFirst(s);
    while(! F.isEmpty()){
      Sommet t = F.removeFirst();
      if(etat.get(t) != explore){
      	System.err.println("J'explore "+t);
      	etat.put(t, encours);
      	++rg;
      	System.err.println("rang["+t+"]="+rg);
      	for(Arc<Sommet> a : voisins(t)){
      		Sommet u = a.destination();
      		F.addFirst(u);
        }
      }
      etat.put(t, explore);
    }
  }

  public void dfs(){
  	Sommet r = null;
  	for(Sommet s : sommets()){
  		r = s;
  		break;
  	}
  	HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
  	for(Sommet s : sommets())
  		etat.put(s, inexplore);
  	//dfsFausse(etat, r); // pour avoir un exemple qui foire Data/pchassignet.in
  	dfs(etat, r);
  }
  
  public int dfsRec(HashMap<Sommet,Integer> etat,
                    HashMap<Arc<Sommet>,String> etat_a,
                    HashMap<Sommet,Integer> rang, int rg,
                    Sommet racine, Sommet s){
    etat.put(s, encours);
    rang.put(s, rg++);
    System.err.println("J'explore "+s);
    for(Arc<Sommet> a : voisins(s)){
      Sommet t = a.destination();
      if(etat.get(t) == inexplore){
        System.err.println("("+s+", "+t+") liaison");
        etat_a.put(a, "arcliaison");
        rg = dfsRec(etat, etat_a, rang, rg, racine, t);
      }
      else{
        if(etat.get(t) == encours){
          // t est ascendant de s
          System.err.println("("+s+", "+t+") arrie`re");
          etat_a.put(a, "arcarriere");
        }
        else{
          // t a de'ja` e'te' explore'
          if(rang.get(t) < rang.get(racine)){
            System.err.println("("+s+", "+t+") transverse car racine("+t+") <> racine("+s+")");
            etat_a.put(a, "arcinter");
          }
          else{ // t est dans la me^me arborescence que s
            if(rang.get(s) < rang.get(t)){
              System.err.println("("+s+", "+t+") avant");
              etat_a.put(a, "arcavant");
            }
            else{
              System.err.println("("+s+", "+t+") transverse");
              etat_a.put(a, "arcintra");
            }
          }
        }
      }
    }
    etat.put(s, explore);
    return rg;
  }

  public void dfsRang(){
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
    HashMap<Arc<Sommet>,String> etat_a = new HashMap<Arc<Sommet>,String>();
    HashMap<Sommet,Integer> rang = new HashMap<Sommet,Integer>();

    for(Sommet s : sommets()){
      etat.put(s, inexplore);
      for(Arc<Sommet> alpha : voisins(s))
        etat_a.put(alpha, "arclibre");
    }
    int rg = 0;
    for(Sommet s : sommets())
      if(etat.get(s) == inexplore)
        rg = dfsRec(etat, etat_a, rang, rg, s, s);
    System.err.print("       ");
    for(Sommet t : sommets())
      System.err.print(t);
    System.err.print("\nrang   ");
    for(Sommet t : sommets())
      System.err.print(rang.get(t));
    System.err.println();
  }

  // on retourne un arbre couvrant de racine s
  public Arbre dfsRec(HashMap<Sommet,Integer> etat, Sommet s){
    etat.put(s, encours);
    Arbre A = new Arbre(s);
    System.err.println("J'explore "+s);
    for(Arc<Sommet> a : voisins(s)){
      Sommet t = a.destination();
      if(etat.get(t) == inexplore)
        A.ajouterFils(dfsRec(etat, t));
    }
    etat.put(s, explore);
    return A;
  }

  public void dfsCouvrant(){
    Sommet ALPHA = new Sommet("ALPHA");
    Arbre F = new Arbre(ALPHA);
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();

    for(Sommet s : sommets())
      etat.put(s, inexplore);
    for(Sommet s : sommets())
      if(etat.get(s) == inexplore)
        F.ajouterFils(dfsRec(etat, s));
    System.err.println("F=\n"+F);
  }

  //// Tri topologique

  boolean dfsTopo(HashMap<Sommet,Integer> etat,
                  LinkedList<Sommet> L, Sommet s){
    etat.put(s, encours);
    System.err.println("J'explore "+s);
    for(Arc<Sommet> a : voisins(s)){
      Sommet t = a.destination();
      if(etat.get(t) == inexplore){
        if(! dfsTopo(etat, L, t))
          return false;
      }
      else if(etat.get(t) == encours){
        // t est ascendant de s
        System.err.println("("+s+", "+t+") arrie`re -> erreur");
        return false;
      }
    }
    etat.put(s, explore);
    L.addFirst(s);
    return true;
  }

  public void triTopologique(){
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
    for(Sommet s : sommets())
      etat.put(s, inexplore);
    for(Sommet s : sommets()){
      if(etat.get(s) == inexplore){
        LinkedList<Sommet> L = new LinkedList<Sommet>();
        if(! dfsTopo(etat, L, s)){
          System.err.println("Graphe avec circuit");
          return;
        }
        for(Sommet t : L)
          System.err.print(t+" -> ");
        System.err.println();
      }
    }
  }

  //// Points d'attache
  int dfsAttache(HashMap<Sommet,Integer> etat,
                 HashMap<Sommet,Integer> rang, int rg,
                 HashMap<Sommet,Integer> rat, Sommet racine, Sommet s){
    etat.put(s, encours);
    System.err.println("J'explore "+s);
    rang.put(s, rg++);
    int rats = rang.get(s), ratt;
    for(Arc<Sommet> a : voisins(s)){
      Sommet t = a.destination();
      if(etat.get(t) == inexplore){
        rg = dfsAttache(etat, rang, rg, rat, racine, t);
        ratt = rat.get(t);
      }
      else if(etat.get(t) == encours)
        // (s, t) est un arc arrie`re
        ratt = rang.get(t);
      else if(rang.get(t) < rang.get(racine))
        // (s, t) transverse inter
        continue;
      else // (s, t) avant ou transverse intra
        ratt = rang.get(t);
      // HERE: vrai? Cf. exemple bbc1 pour l semble faux...!
      rats = Math.min(rats, ratt);
    }
    rat.put(s, rats);
    etat.put(s, explore);
    return rg;
  }

  public void pointsDAttache(){
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
    HashMap<Sommet,Integer> rang = new HashMap<Sommet,Integer>();
    HashMap<Sommet,Integer> rat = new HashMap<Sommet,Integer>();

    for(Sommet s : sommets())
      etat.put(s, inexplore);
    int rg = 0;
    for(Sommet s : sommets())
      if(etat.get(s) == inexplore)
        rg = dfsAttache(etat, rang, rg, rat, s, s);
    System.err.println("Rangs");
    for(Sommet s : sommets())
      System.err.println(s+" "+rang.get(s));
    System.err.println("Rangs d'attache");
    for(Sommet s : sommets())
      System.err.println(s+" "+rat.get(s));
  }

  //// Composantes fortement connexes

  final static int libre = 0, empile = 1, exclus = 2, pointdentree = 3, dansCFC = 4;

  int dfsCfc(HashMap<Sommet,Integer> etat,
             HashMap<Sommet,Integer> etat_cfc,
  		       HashMap<Arc<Sommet>,String> etat_a,
             LinkedList<Sommet> pile,
             HashMap<Sommet,Integer> rang, int rg,
             HashMap<Sommet,Integer> rat, 
             Sommet racine, Sommet s){
    etat.put(s, encours);
    System.err.println("J'explore "+s);
    rang.put(s, rg++);
    rat.put(s, rang.get(s));
    pile.addFirst(s);
    etat_cfc.put(s, empile);
    for(Arc<Sommet> a : voisins(s)){
      Sommet t = a.destination();
      if(etat_cfc.get(t) == inexplore){
        System.err.println("("+s+", "+t+") liaison");
        etat_a.put(a, "arcliaison");
        rg = dfsCfc(etat, etat_cfc, etat_a, pile, rang, rg, rat, racine, t);
        if(rat.get(t) < rat.get(s)){
        	rat.put(s, rat.get(t));
        }
      }
      else{
        if(etat_cfc.get(t) == empile){
          if(rang.get(t) > rang.get(s))
            // (s, t) est avant
            etat_a.put(a, "arcavant");
          else{
            // (s, t) est arriere ou intra-arbre
            // t est dans C(s) car t descendant de s et s -> t
            if(etat.get(t) == encours){
              System.err.println("("+s+", "+t+") arrie`re");
              etat_a.put(a, "arcarriere");
            }
            else{
              System.err.println("("+s+", "+t+") intra-arbre");
              etat_a.put(a, "arcintra");
            }
            System.err.println(t+" est dans C("+s+")");
            if(rang.get(t) < rat.get(s))
              rat.put(s, rang.get(t));
          }
        }
        else{
          // t est exclus
          System.err.println(t+" exclus");
          if(rang.get(t) < rang.get(racine))
            // (s, t) est inter-arbre
            etat_a.put(a, "arcinter");
          else
            etat_a.put(a, "arcintra");
      	}
      }
    }
    etat.put(s, explore);
    System.err.print("rang["+s+"]="+rang.get(s));
    System.err.println(" rat["+s+"]="+rat.get(s));
    if(rat.get(s) == rang.get(s)){
      // s est un point d'entre'e
    	etat_cfc.put(s, pointdentree);
    	for(Sommet u : pile){
    		if(u.equals(s))
    			break;
    		etat_cfc.put(u, dansCFC);
    	}
      Sommet t;
      System.err.print("CFC={");
      do{
        t = pile.removeFirst();
        etat_cfc.put(t, exclus);
        System.err.print(" "+t);
      } while(! t.equals(s));
      System.err.println(" }");
    }
    return rg;
  }

  public void cfc(){
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
    HashMap<Sommet,Integer> etat_cfc = new HashMap<Sommet,Integer>();
    HashMap<Arc<Sommet>,String> etat_a = new HashMap<Arc<Sommet>, String>();
    HashMap<Sommet,Integer> rang = new HashMap<Sommet,Integer>();
    HashMap<Sommet,Integer> rat = new HashMap<Sommet,Integer>();
    LinkedList<Sommet> pile = new LinkedList<Sommet>();

    for(Sommet s : sommets()){
      etat.put(s, inexplore);
      etat_cfc.put(s, libre);
      for(Arc<Sommet> alpha : voisins(s))
      	etat_a.put(alpha, "arclibre");
    }
    int rg = 0;
    for(Sommet s : sommets())
      if(etat.get(s) == inexplore)
        rg = dfsCfc(etat, etat_cfc, etat_a, pile, rang, rg, rat, s, s);
    System.err.println("Rangs");
    for(Sommet s : sommets())
      System.err.println(s+" "+rang.get(s));
  }

  //// Points d'articulation et blocs

  void depiler(LinkedList<Arc<Sommet>> pile, Sommet s, Sommet t){
    Sommet x, y;
    System.err.print("Bloc = {");
    do{
      Arc<Sommet> a = pile.removeFirst();
      x = a.origine();
      y = a.destination();
      System.err.print("("+x+", "+y+")");
    } while(!x.equals(s) && !y.equals(t));
    System.err.println("}");
  }

  void depiler(LinkedList<Arc<Sommet>> pile){
    System.err.print("Bloc_racine = {");
    while(! pile.isEmpty()){
      Arc<Sommet> a = pile.removeFirst();
      System.err.print("("+a.origine()+", "+a.destination()+")");
    }
    System.err.println("}");
  }

  // pere -> s
  int dfsPda(LinkedList<Sommet> lpda,
             HashMap<Sommet,Integer> etat,
             LinkedList<Arc<Sommet>> pile,
             HashMap<Sommet,Integer> rang, int rg,
             HashMap<Sommet,Integer> rat, 
             Sommet pere, Sommet s){
    etat.put(s, encours);
    System.err.println("J'explore "+s);
    rang.put(s, rg++);
    int rats = rang.get(s);
    for(Arc<Sommet> a : voisins(s)){
      Sommet t = a.destination();
      if(etat.get(t) == inexplore){
        System.err.println("("+s+", "+t+") liaison");
        pile.addFirst(a);
        rg = dfsPda(lpda, etat, pile, rang, rg, rat, s, t);
        rats = Math.min(rats, rat.get(t));
        if(rat.get(t) >= rang.get(s)){
          System.err.println(s+" est point d'articulation");
          depiler(pile, s, t);
          lpda.addFirst(s);
        }
      }
      else if((rang.get(t) < rang.get(s)) && !t.equals(pere)){
        System.err.println("("+s+", "+t+") arrie`re ou transverse");
        pile.addFirst(a);
        rats = Math.min(rats, rang.get(t));
      }
    }
    rat.put(s, rats);
    System.err.print("rang["+s+"]="+rang.get(s));
    System.err.println(" rat["+s+"]="+rat.get(s));
    etat.put(s, explore);
    return rg;
  }

  // s est la racine de l'arborescence
  int dfsPda(LinkedList<Sommet> lpda,
             HashMap<Sommet,Integer> etat,
             LinkedList<Arc<Sommet>> pile, 
             HashMap<Sommet,Integer> rang, int rg,
             HashMap<Sommet,Integer> rat, Sommet s){
    etat.put(s, encours);
    System.err.println("J'explore "+s);
    rang.put(s, rg++);
    int rats = rang.get(s);
    int nfils = 0;
    for(Arc<Sommet> a : voisins(s)){
      Sommet t = a.destination();
      if(etat.get(t) == inexplore){
        System.err.println("("+s+", "+t+") liaison");
        pile.addFirst(a);
        rg = dfsPda(lpda, etat, pile, rang, rg, rat, s, t);
        nfils++;
      }
    }
    if(nfils > 1){
      lpda.addFirst(s);
      System.err.println(s+" est racine ET point d'articulation");
      depiler(pile);
    }
    rat.put(s, rats);
    System.err.print("rang["+s+"]="+rang.get(s));
    System.err.println(" rat["+s+"]="+rat.get(s));
    etat.put(s, explore);
    return rg;
  }

  public void pointsDArticulation(LinkedList<Sommet> lpda,
                                  HashMap<Sommet,Integer> rat){
    HashMap<Sommet,Integer> etat = new HashMap<Sommet,Integer>();
    HashMap<Sommet,Integer> rang = new HashMap<Sommet,Integer>();

    for(Sommet s : sommets())
      etat.put(s, inexplore);
    int rg = 0;
    LinkedList<Arc<Sommet>> pile = new LinkedList<Arc<Sommet>>();
    for(Sommet s : sommets())
      if(etat.get(s) == inexplore)
        // nouvelle composante connexe
        rg = dfsPda(lpda, etat, pile, rang, rg, rat, s);
    System.err.println("Rangs");
    for(Sommet s : sommets())
      System.err.println(s+" "+rang.get(s));
  }

  public static String XMLHeader(){
    String str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    str += "<graphml xmlns=\"http://graphml.graphdrawing.org/xmlns\"\n";
    str += "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n";
    str += "xsi:schemaLocation=\"http://graphml.graphdrawing.org/xmlns\n";
    str += "http://graphml.graphdrawing.org/xmlns/1.0/graphml.xsd\">\n";
    str += "  <key id=\"Pigale/version\" for=\"graph\" attr.name=\"Pigale version\" attr.type=\"string\">\n";
    str += "    <default>1.3.9</default>\n";
    str += "  </key>\n";
    str += "  <key id=\"Pigale/V/16\" for=\"node\" attr.name=\"Coordinates\" attr.type=\"string\"/>\n";
    str += "  <key id=\"Pigale/V/1\" for=\"node\" attr.name=\"Color\" attr.type=\"string\"/>\n";
    str += "  <key id=\"Pigale/V/0\" for=\"node\" attr.name=\"Label\" attr.type=\"string\"/>\n";
    str += "  <key id=\"Pigale/E/1\" for=\"edge\" attr.name=\"Color\" attr.type=\"string\"/>\n";
    str += "  <key id=\"Pigale/E/16\" for=\"edge\" attr.name=\"Width\" attr.type=\"string\"/>\n";
    return str;
  }
  public static String XMLTrailer(){
    return "</graphml>\n";
  }
  // pos[s] est la position de s dans le plan, color[s] le contenu de s, par exemple une couleur.
  // pos[s] doit etre de la forme x,y
  public String toGraphml(String nom, HashMap<Sommet,String> pos, HashMap<Sommet,Integer> color){
    String str = XMLHeader();
    str += "  <graph id=\"" + nom + "\"";
    if(oriente)
      str += " edgedefault=\"directed\">\n";
    else
      str += " edgedefault=\"undirected\">\n";
    // les sommets
    for(Sommet s : sommets()){
      str += "    <node id=\"" + s + "\">\n";
      if(pos != null)
        str += "      <data key=\"Pigale/V/16\">"+pos.get(s)+"</data>\n";
      if(color != null)
        str += "      <data key=\"Pigale/V/1\">" + color.get(s) + "</data>\n";
      str += "      <data key=\"Pigale/V/0\">" + s + "</data>\n";
      str += "    </node>\n";
    }
    // les aretes
    for(Sommet s : sommets()){
      for(Arc<Sommet> arc : voisins(s)){
        // TODO: permettre des couleurs et labels sur les arcs
        Sommet t = arc.destination();
        str += "    <edge source=\""+s+"\" target=\""+t+"\">\n";
        str += "      <data key=\"Pigale/E/1\">1</data>\n";
        str += "      <data key=\"Pigale/E/16\">1</data>\n";
        str += "    </edge>\n";
      }
    }
    str += "  </graph>\n";
    str += XMLTrailer();
    return str;
  }*/
}

