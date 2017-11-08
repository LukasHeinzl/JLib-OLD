package cf.lukasheinzl.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the base-class for all graph nodes used in the {@link cf.lukasheinzl.graph.algorithm} package
 * 
 * @author Lukas Heinzl
 *
 * @param <T>
 *            A subclass of {@link cf.lukasheinzl.graph.Node Node}
 */
public abstract class Node<T extends Node<T>>{

	private List<T> links;

	/**
	 * Empty constructor, used for instance variable initialization
	 */
	public Node(){
		links = new ArrayList<>();
	}

	/**
	 * Returns a List&lt;T extends Node&lt;T&gt;&gt; containing all nodes linked to this one
	 * 
	 * @return The List of nodes
	 */
	public List<T> getLinks(){
		return links;
	}

	/**
	 * Links the other node to this one
	 * 
	 * @param other
	 *            Node to be linked to this one
	 */
	public void link(T other){
		links.add(other);
	}

	/**
	 * Abstract method for determining the distance from this node to another
	 * 
	 * @param other
	 *            The other node to get the distance to
	 * @return The distance between the nodes
	 */
	public abstract int getDistance(T other);

	@Override
	public String toString(){
		return "Node(" + Integer.toHexString(hashCode()) + ") " + links.size() + " links";
	}

}
