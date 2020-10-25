package reseauSocial.core;


import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Observer;
import java.util.Set;

import facebookGhost.FacebookGhostNetwork;

/**
 * 
 * Basic" interface for defining a social network
 * 
 * @author blay
 *
 */

public interface SocialNetworkInterface<T>  {

	/**
	 * @param identifier for a member
	 * @return member if known by the network
	 */
	public T getMember(String identifier);

	/**
	 * @return the set of members belonging to the network
	 */
	public Collection<T> getMembers();

	/**
	 * add a member to the network
	 */
	public void addMember(T membre);

	/**
	 * @param ident
	 * @param fg a facebookGhostNetwork, if the member belongs to fg, an adapter to this network is created.
	 * @return the member corresponding to the identifier. If a member with this identifer already exists, the member is not created.
	 */
	public T addMember(String ident, FacebookGhostNetwork fg);
	public T addMember(String identifier);
	

	/**
	 * 
	 * Add the relationship between a member and a friend with a given force.
	 * 
	 * @param force
	 * @param member
	 * @param friend
	 * 
	 */
	public void relate(Strength force, T member, T friend);

	/**
	 * @param member
	 * @param rank
	 * @return returns all members given the rank (at rank 1 : direct neighbors; at rank 2 :  neighbors of neighbors.
	 * A member appears at the lowest rank, it does not appear in a higher rank
	 */
	public Set<T> relatedToRank(T member, int rank);

	/**
	 * @param member1
	 * @param member2
	 * @return distance between the two members
	 */
	public int distance(T member1, T member2);



}
