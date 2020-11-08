package reseauSocialTestV2;



import java.util.Set;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reseauSocial.core.SocialNetworkInterface;
import reseauSocial.core.Strength;
import reseauSocial.implementation2020.Member;
import reseauSocial.implementation2020.SocialNetWorkImpl;



public class SecondTests {

	
    //For Students => TODO : reference to be modified according to your implementation
	//Member refers to your implementation of MemberInterface
	//SocialNetWorkImpl refers to your implementation of SocialNetworkInterface
    SocialNetworkInterface<Member> iutRS;

    @BeforeEach
    public void setUp() {
        iutRS = new SocialNetWorkImpl("IUT");
    }
    
    
    Member asterix;
    Member falbala ;
    Member obelix ;
    Member panoramix ;
    Member abraracourcix ;
    
    
    /*
     * utility functions
     */
    
    private void initGaulois(SocialNetworkInterface<Member> iutRS) {
     String location = "Armorique";
     asterix = addMember("Asterix",location,iutRS);
     falbala = addMember("Falbala",location,iutRS);
     obelix = addMember("Obelix",location,iutRS);
     panoramix =addMember("Panoramix",location,iutRS);
     abraracourcix = addMember("Abraracourcix",location,iutRS);
    }
    
    private Member addMember(String nom, String location, SocialNetworkInterface<Member> nw  ) {
    	Member m = new Member(nom);
    	m.setLocation(location);
        nw.addMember(m);
        return m;
	}


    //------------ Construction des relations ------- //

    private void buildAsterixNetwork() {
    	initGaulois(iutRS);

        iutRS.relate(Strength.STRONG, asterix, obelix);
        iutRS.relate(Strength.HIGH, asterix, panoramix);
        iutRS.relate(Strength.STRONG, obelix, asterix);
        iutRS.relate(Strength.HIGH,  obelix, falbala);
        iutRS.relate(Strength.MEDIUM, obelix, panoramix);
        iutRS.relate(Strength.LOW, falbala, obelix);
        iutRS.relate(Strength.LOW, falbala, asterix);
        iutRS.relate(Strength.LOW, panoramix,abraracourcix);
    }

      

    // ----------------- Pour compatibilité des codes avec Junit4 sans trop me fatiguer :-) 
    private static void assertEquals(String commentaire, int valeurAttendue, int valeurObtenue) {
    	Assertions.assertEquals(valeurAttendue, valeurObtenue, commentaire);
    }
    private static void assertTrue(String commentaire, boolean value) {
    	Assertions.assertTrue(value, commentaire);
    }
    


    // ---------------- Tests

    @Test
    public void testInit() {
        Assertions.assertTrue(iutRS != null);
    	initGaulois(iutRS);
    	Assertions.assertTrue(obelix!= null);
    	Assertions.assertTrue(asterix!= null);
    }

    @Test
    public void testRelationsAtRankOne() {
        buildAsterixNetwork();
        //tests au rang 1
        Set<Member> membresAmis = iutRS.relatedToRank(asterix, 1);
        //System.out.println("Amis de Asterix" + membresAmis);
        assertEquals("taille des amis d'Asterix au rang 1 : 2 membres", 2, membresAmis.size());
        assertTrue("Asterix est bien ami d'Obelix", membresAmis.contains(obelix));
        assertTrue("Asterix est bien ami de Panoramix", membresAmis.contains(panoramix));
        membresAmis = iutRS.relatedToRank(panoramix, 1);
        //System.out.println("Amis de Panoramix" + membresAmis);
        assertEquals("taille des amis de Panoramix au rang 1 : 1 membres", 1, membresAmis.size());
        membresAmis = iutRS.relatedToRank(falbala, 1);
        assertEquals("taille des amis de Panoramix au rang 1 : 2 membres", 2, membresAmis.size());
    }

    @Test
    public void testRelationsAtRankTwoandMore() {
        buildAsterixNetwork();

        //tests au rang 2
        Set< Member> membresAmis = iutRS.relatedToRank(asterix, 2);
        //System.out.println("Amis de Asterix au rang 2" + membresAmis);
        assertEquals("taille des amis d'Asterix au rang 2 : 2 membre Falbala et Abraracourcix, les autres le sont deja au rang 1", 2, membresAmis.size());
        assertTrue("Asterix est bien ami de falbala au rang 2", membresAmis.contains(falbala));
        membresAmis = iutRS.relatedToRank(panoramix, 2);
        assertEquals("taille des amis de Panoramix au rang 2 : 0 membres", 0, membresAmis.size());
        membresAmis = iutRS.relatedToRank(obelix, 2);
        //System.out.println("Amis de Obelix au rang 2" + membresAmis);
        assertEquals("taille des amis Obelix au rang 2 : 1 membre", 1, membresAmis.size());
        membresAmis = iutRS.relatedToRank(falbala, 2);
        assertEquals("taille des amis Falbala au rang 2 : 1 membre", 1, membresAmis.size());
        membresAmis = iutRS.relatedToRank(falbala, 3);
        assertEquals("taille des amis Falbala au rang 3 : 1 membre", 1, membresAmis.size());
        assertTrue("Abraracourcix est bien ami de falbala au rang 3", membresAmis.contains(abraracourcix));

    }

    @Test
    public void testDistances() {
        buildAsterixNetwork();

        //Calcul des distances
        int distance = iutRS.distance(asterix, obelix);
        assertEquals("distance entre asterix et obelix", Strength.STRONG.getValue(), distance);
        distance = iutRS.distance(asterix, falbala);
        assertEquals("distance entre asterix et falbala", Strength.STRONG.getValue() + Strength.HIGH.getValue(), distance);
        distance = iutRS.distance(asterix, panoramix);
        assertEquals("distance entre asterix et Panoramix", Strength.HIGH.getValue(), distance);
        distance = iutRS.distance(falbala, asterix);
        assertEquals("distance entre falbala et asterix", Strength.LOW.getValue(), distance);
        distance = iutRS.distance(falbala, abraracourcix);
        assertEquals("distance entre falbala et Abraracourcix, passe par Asterix", Strength.LOW.getValue()*2 + Strength.HIGH.getValue(), distance);
        distance = iutRS.distance(asterix, abraracourcix);
        assertEquals("distance entre asterix et Abraracourcix", Strength.HIGH.getValue() + Strength.LOW.getValue(), distance);
    }

}
