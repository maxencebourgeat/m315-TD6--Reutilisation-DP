package reseauSocialTestV2;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import reseauSocial.core.MemberInterface;
import reseauSocial.core.SocialNetworkInterface;
import reseauSocial.implementation2020.Member;
import reseauSocial.implementation2020.SocialNetWorkImpl;




public class FirstTests {

	
    //TODO : reference to be modified according to your implementation
	//Member refers to your implementation of MemberInterface
	//SocialNetWorkImpl refers to your implementation of SocialNetworkInterface
    SocialNetworkInterface<Member> iutRS;
    static String nomGeek = "geek01";
    
    @BeforeEach
    public void setUp(){
        iutRS = new SocialNetWorkImpl("IUT");
    }
    
    /*
     * utility functions
     */

    private Member addMember(String nom, String location, SocialNetworkInterface<Member> nw  ) {
    	Member m = new Member(nom);
    	m.setLocation(location);
        nw.addMember(m);
        return m;
	}
   

    @Test
    public void testInit() {
        assertTrue(iutRS != null);
    }

    
    
    /*
        Serie of simple tests
     */


    @Test
    public void testAddAndGetMemberSimple() {
    	String location = "Nice";
        MemberInterface mGeek01 = addMember(nomGeek, location, iutRS);
        assertEquals(nomGeek, mGeek01.getName());
        assertEquals(location, mGeek01.getLocation());
        assertEquals(mGeek01,iutRS.getMember(nomGeek));
        
    }
    @Test
    public void testAddAndGetTwoMembers() {
        MemberInterface mGeek01 = addMember(nomGeek, "Nice", iutRS);
        MemberInterface mGeek02 = addMember("ivana","Toulon", iutRS);
        assertEquals(nomGeek, mGeek01.getName());
        assertEquals("ivana", mGeek02.getName());
        assertEquals(mGeek01,iutRS.getMember(nomGeek));
        assertEquals(mGeek02,iutRS.getMember("ivana"));
    }

    @Test
    public void testGetMembers() {
    	 MemberInterface mGeek01 = addMember("geek01", "Nice", iutRS);
         MemberInterface mGeek02 = addMember("ivana","Toulon", iutRS);
        Collection<? extends MemberInterface> membres = iutRS.getMembers();
        assertEquals("taille du reseau est bien de 2 membres", 2,membres.size());
        assertTrue(membres.contains(mGeek01));
        assertTrue(membres.contains(mGeek02));
    }
}
