package grapheSimple;


import static org.junit.jupiter.api.Assertions.*;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import grapheX.Arc;
import grapheX.Sommet;


//@todo to be improved!
public class GrapheSimpleTest extends GrapheSimple<Sommet> {

	
	GrapheSimple<Sommet> graphe = new GrapheSimple<>();
	Sommet s1 = new Sommet("S1");
	Sommet s2 = new Sommet("S2");
	Sommet s3= new Sommet("S3");
	Sommet s4= new Sommet("S4");
	int distance_s1_s2 = 1;
	int distance_s1_s3 = 2;
	int distance_s2_s1 = 2;
	int distance_s2_s3 = 1;
	int distance_s3_s4 = 2;	
	int distance_s4_s1 = 10;
	
	
	/*
	 * s1 -1-> s2 -1-> s3
	 * s2-2-> s1 
	 * s3 -2-> s4
	 * s4 -10-> s1
	 * 
	 */
	@BeforeEach
	public void setUp() throws Exception {
		graphe = new GrapheSimple<>();
		
	}
	
	public void initGlobalGraph(){
		graphe = new GrapheSimple<>();
		graphe.ajouterSommet(s1);
		graphe.ajouterSommet(s2);
		graphe.ajouterSommet(s3);
		graphe.ajouterSommet(s4);
		graphe.ajouterArc(s1, s2, distance_s1_s2);
		graphe.ajouterArc(s1, s2, distance_s1_s2*2);
		graphe.ajouterArc(s1, s3, distance_s1_s3);
		graphe.ajouterArc(s2, s1, distance_s2_s1);
		graphe.ajouterArc(s2, s3, distance_s2_s3);
		graphe.ajouterArc(s3, s4, distance_s3_s4);		
		graphe.ajouterArc(s4, s1, distance_s4_s1);
	}



	
	@Test
	public void testTailleInitiale() {
		assertEquals(0,graphe.taille());
	}

	@Test
	public void testAjouterSommetInitial() {
		graphe.ajouterSommet(s1);
		assertEquals(1,graphe.taille());
		assertEquals(s1,graphe.getSommet("S1"));
	}
	
	@Test
	public void testAjouterArcInitial() {
		graphe.ajouterSommet(s1);
		graphe.ajouterSommet(s2);
		graphe.ajouterSommet(s3);
		graphe.ajouterArc(s1, s2, distance_s1_s2);
		assertEquals(3,graphe.taille());
		assertTrue(graphe.existeArc(s1, s2));
		assertFalse(graphe.existeArc(s1, s3));
		List<Arc<Sommet>> arcs = graphe.arcs(s1, s2);
		assertEquals(1,arcs.size());
	}



	@Test
	public void testVoisinsSommet() {
		initGlobalGraph();
		Collection<Arc<Sommet>> voisins = graphe.voisins(s1);
		assertEquals(3, voisins.size());
		//System.out.println("voisins de s1 :" + voisins);
		voisins = graphe.voisins(s2);
		assertEquals(2, voisins.size(), "voisins de s2 : " + voisins);
		//System.out.println("voisins de s2 : " + voisins);
	}

	@Test
	public void testArcs() {
		initGlobalGraph();
		List<Arc<Sommet>> arcs = graphe.arcs(s1, s2);
		//System.out.println("de s1 a s2 : " + arcs);
		assertEquals(2, arcs.size());
		arcs = graphe.arcs(s1, s3);
		//System.out.println("de s1 a s3 : " + arcs);
		assertEquals(1, arcs.size());
		arcs = graphe.arcs(s1, s4);
		//System.out.println("de s1 a s4 : " + arcs);
		assertTrue(arcs==null);
		arcs = graphe.arcs(s4, s1);
		//System.out.println("de s4 a s1 : " + arcs);
		assertEquals(1, arcs.size());
		
	}
	
	@Test
	public void testParcoursCheminsDe() {
		initGlobalGraph();
		List<Chemin<Sommet>> chemins = graphe.chemins(s1);
		assertEquals(3, chemins.size());
		System.out.println("Chemins a partir de s1: " + chemins);
		chemins = graphe.chemins(s2);
		System.out.println("Chemins a partir de s2: " + chemins);
		assertEquals(2, chemins.size());
		chemins = graphe.chemins(s3);
		System.out.println("Chemins a partir de s3: " + chemins);
		assertEquals(2, chemins.size());
		chemins = graphe.chemins(s4);
		assertEquals(3, chemins.size());
		System.out.println("Chemins a partir de s4: " + chemins);
	}
	
	@Test
	public void testParcoursCheminsDeA() {
		initGlobalGraph();
		List<Chemin<Sommet>> chemins = graphe.chemins(s1,s2);
		System.out.println("Chemins de s1 a s2: " + chemins);
		assertEquals(2, chemins.size());

		chemins = graphe.chemins(s1,s3);
		System.out.println("Chemins de s1 a s3: " + chemins);
		assertEquals(3, chemins.size(), "Chemins de s1 a s3:");
		chemins = graphe.chemins(s1,s4);
		System.out.println("Chemins  de s1 a s4: " + chemins);
		assertEquals(3, chemins.size());
		chemins = graphe.chemins(s2,s3);
		System.out.println("Chemins de s2 a s3: " + chemins);
		assertEquals(2, chemins.size());
		chemins = graphe.chemins(s4,s1);
		System.out.println("Chemins  de s4 a s1: " + chemins);
		assertEquals(1, chemins.size(), "Chemins  de s4 a s1: ");
		
	}
	
	@Test
	public void testCheminLePlusCourt() {
		initGlobalGraph();
		Chemin<Sommet> chemin = graphe.cheminLePlusCourt(s1, s2);
		assertEquals(distance_s1_s2, chemin.distance());
		chemin = graphe.cheminLePlusCourt(s1, s3);
		assertEquals(distance_s1_s3, chemin.distance());
		chemin = graphe.cheminLePlusCourt(s1, s4);
		assertEquals(4, chemin.distance());
		chemin = graphe.cheminLePlusCourt(s2, s3);
		assertEquals(distance_s2_s3, chemin.distance());
		chemin = graphe.cheminLePlusCourt(s4, s1);
		assertEquals(distance_s4_s1, chemin.distance());
	}
	
	@Test
	public void testVoisinsDeRangs() {
		initGlobalGraph();
		Set<Sommet> voisins = graphe.voisinsAuRang(s1, 1);
		System.out.println("voisins s1 au rang 1: " + voisins);
		assertEquals(2, voisins.size());
		assertTrue(voisins.contains(s2));
		assertTrue(voisins.contains(s3));
		voisins = graphe.voisinsAuRang(s1, 2);
		System.out.println("voisins s1 au rang 2: " + voisins);
		assertEquals(1, voisins.size());
		assertTrue(voisins.contains(s4));
		voisins = graphe.voisinsAuRang(s2, 1);
		System.out.println("voisins s2 au rang 1: " + voisins);
		assertEquals(2, voisins.size());
		assertTrue(voisins.contains(s1));
		assertTrue(voisins.contains(s3));
		voisins = graphe.voisinsAuRang(s2, 2);
		System.out.println("voisins s2 au rang 2: " + voisins);
		assertEquals(1, voisins.size());
		assertTrue(voisins.contains(s4));
		voisins = graphe.voisinsAuRang(s4, 2);
		System.out.println("voisins s4 au rang 2: " + voisins);
		assertEquals(2, voisins.size());
		assertTrue(voisins.contains(s2));
		assertTrue(voisins.contains(s3));
	}
	
	
	@Test
	public void testExtraireChemin() {
		Chemin<Sommet> cheminComplet = new Chemin<>();
		cheminComplet.add(new Arc<Sommet>(s1,s2));
		cheminComplet.add(new Arc<Sommet>(s2,s3));
		cheminComplet.add(new Arc<Sommet>(s3,s4));
		System.out.println("chemin de s1 a s4 " + cheminComplet);
		Chemin<Sommet> c= cheminComplet.extraireChemin(s1, s4);
		System.out.println("chemin de s1 a s4 apres extraction " + c);
		assertEquals(3,c.getArcs().size());
		assertEquals(s1,c.getArcs().get(0).origine());
		assertEquals(s4,c.getArcs().get(2).destination());
		
		c= cheminComplet.extraireChemin(s1, s3);
		System.out.println("chemin de s1 a s3 apres extraction " + c);
		assertEquals(2,c.getArcs().size());
		assertEquals(s1,c.getArcs().get(0).origine());
		assertEquals(s3,c.getArcs().get(1).destination());
		
		c= cheminComplet.extraireChemin(s2, s3);
		System.out.println("chemin de s2 a s3 apres extraction " + c);
		assertEquals(1,c.getArcs().size());
		assertEquals(s2,c.getArcs().get(0).origine());
		assertEquals(s3,c.getArcs().get(0).destination());
		
		
		c= cheminComplet.extraireChemin(s2, s4);
		System.out.println("chemin de s2 a s4 apres extraction " + c);
		assertEquals(2,c.getArcs().size());
		assertEquals(s2,c.getArcs().get(0).origine());
		assertEquals(s4,c.getArcs().get(1).destination());

	}
	

}
