package reseauSocialTestV2;

import static org.junit.Assert.*;
import facebookGhost.FacebookGhostNetwork;
import facebookGhost.User;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reseauSocial.core.SocialNetworkInterface;
import reseauSocial.implementation2020.Member;
import reseauSocial.implementation2020.SocialNetWorkImpl;
import reseauSocial.implementation2020.Spy;



public class ThirdTests{

	//TODO : reference to be modified according to your implementation
	//Member refers to your implementation of MemberInterface
	//SocialNetWorkImpl refers to your implementation of SocialNetworkInterface
	
	//SocialNetworkInterface<Member> iutRS;
	
	SocialNetWorkImpl iutRS;

	//Spy snoop = new Spy();

	FacebookGhostNetwork fg ;



	//------------ Connexions to FG ------- //
	private FacebookGhostNetwork getFacebookGhostNetwork() {
		return  new FacebookGhostNetwork();
	}

	private void buildGreekmythologieNetwork() {
		User zeus = fg.addUser("Zeus","le dieu ...","Olympe");
		User alcmene = fg.addUser("Alcmene","la mere d'hercule");
		User hercule = fg.addUser("Hercule","le hero");
		User admete = fg.addUser ("Admete","l'ami","Grece");
		fg.addUser("Hera","la femme de zeus","Olympe");
		fg.addFamilyRelation(hercule, zeus);
		fg.addFamilyRelation(hercule, alcmene);
		fg.addFriendRelation(hercule,admete);		
	}

	
	@BeforeEach
	public void setUp() {
		iutRS = new SocialNetWorkImpl("IUT");
		fg = getFacebookGhostNetwork();
		buildGreekmythologieNetwork();
	}


	@Test
	public void testInit() {
		assertTrue(iutRS != null);	
		assertTrue(fg.getUser("Zeus") != null);
	}


	@Test
	public void testConnexionToFg() {
		Member admeteLocal = iutRS.addMember("Admete", fg);
		assertEquals("location de Admete",
				fg.getUser("Admete").getHometown().getName(),
				admeteLocal.getLocation()
				);
	}
	
	@Test
	public void testConnexionToFGInitialisation() {
		Member admeteLocal = iutRS.addMember("Admete", fg);
		Set<Member> membresAmis = iutRS.relatedToRank(admeteLocal, 1);
		assertEquals("taille des amis d'ADMETE au rang 1 : 0 membres", 0, membresAmis.size());
	}

	
	
	@Test
	public void testAdapter() {
		Member admeteLocal = iutRS.addMember("Admete", fg);
		assertEquals("location de Admete",
				fg.getUser("Admete").getHometown().getName(),
				admeteLocal.getLocation()
				);
		fg.getUser("Admete").setLocation("mer Egee");
		assertEquals("location de Admete",
				fg.getUser("Admete").getHometown().getName(),
				admeteLocal.getLocation()
				);
	}



	@Test
	public void testConnexionToFGWithlinks() {
		// Hera (not known as Zeus wife)
		Member admete = iutRS.addMember("Admete", fg);
		Member zeus = iutRS.addMember("Zeus", fg);
		Member hera = iutRS.addMember("Hera", fg);
		Member hercule = iutRS.addMember("Hercule", fg);

		//Test relations
		Set<Member> membresAmis = iutRS.relatedToRank(admete, 1);
		assertEquals("taille des amis de Admete au rang 1 : 1 membre Hercule", 1, membresAmis.size());

		membresAmis = iutRS.relatedToRank(hera, 1);
		//System.out.println("amis Hera au rang 1" + membresAmis);
		assertEquals("taille des amis Hera au rang 1 : 1 membres", 0, membresAmis.size());

		membresAmis = iutRS.relatedToRank(hercule, 1);
		//System.out.println("relations de hercule" + membresAmis);
		assertEquals("taille des amis d'Hercule au rang 1 : 2 membres Admete et Zeus: ", 2, membresAmis.size());
		assertTrue("Zeus est bien ami de Hercule au rang 1", membresAmis.contains(zeus));
		assertTrue("Admete est bien ami de Hercule au rang 1", membresAmis.contains(admete));

		int distance = iutRS.distance(hercule, zeus);
		assertEquals("distance entre hercule et zeus", 2, distance);
		distance = iutRS.distance(hercule, admete);
		assertEquals("distance entre hercule et admete", 3, distance);

		membresAmis = iutRS.relatedToRank(hercule, 2);
		//System.out.println("amis d'Hercule au rang 2" + membresAmis);
		assertEquals("taille des amis d'Hercule au rang 2 : 0 membre", 0, membresAmis.size());


		membresAmis = iutRS.relatedToRank(admete, 1);
		//System.out.println("amis d'ADMETE au rang 1" + membresAmis);
		assertEquals("taille des amis d'ADMETE au rang 1 : 1 membres", 1, membresAmis.size());
		distance = iutRS.distance(admete, hercule);
		assertEquals("distance entre hercule et admete", 3, distance);
	}


	//------------ Observations  : Connexions e FG ------- //
	@Test
	public void testConnexionToFGAndObserver() {
		iutRS.addMember("Zeus", fg);
		Member hera = iutRS.addMember("Hera", fg);
		Member hercule = iutRS.addMember("Hercule", fg);
		
		Set<Member> membresAmis = iutRS.relatedToRank(hercule, 1);
		assertEquals("taille des amis d'Hercule au rang 1 : 1 membre car seul zeus est connu de nous", 1, membresAmis.size());
	
		//si on ajoute un lien dans fg entre Hera et hercule on ne le voit pas.
		fg.addFamilyRelation("Hercule", "Hera");
		membresAmis = iutRS.relatedToRank(hercule, 1);
		assertEquals("taille des amis d'Hercule au rang 1 : 1 membre car on ne voit pas lien avec Hera", 1, membresAmis.size());
		assertEquals(0,iutRS.relatedToRank(hera, 1).size());
		
		//maintenant si lajout de lien se fait dans fg nous les verrons grace à l'observer
		fg.addPropertyChangeListener(iutRS);
		fg.addFamilyRelation("Zeus", "Hera");
		
		//Seul le lien entre Zeus et Hera est enregistré
		membresAmis = iutRS.relatedToRank(hera, 1);
		assertEquals("taille des amis Hera au rang 1 : 1 membres", 1, membresAmis.size());
		

		//fg.addPropertyChangeListener(snoop);
		//((SocialNetWorkImpl)iutRS).addPropertyChangeListener(snoop);
		
		//Facultatif
		Member megara = iutRS.addMember("Mégara");
		assertEquals(Member.DEFAULT_LOCATION , megara.getLocation());
        fg.addUser("Mégara", "megara fut l'épouse d'Hercule","Thèbes");
        assertEquals("Thèbes" , megara.getLocation());

	}
}
