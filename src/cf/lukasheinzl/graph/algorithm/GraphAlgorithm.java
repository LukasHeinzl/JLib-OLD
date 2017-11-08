package cf.lukasheinzl.graph.algorithm;

import java.util.List;

import cf.lukasheinzl.graph.Node;

/**
 * This class acts as an algorithm-factory for graph-solving algorithms provided by the
 * {@link cf.lukasheinzl.graph.algorithm} package.
 * 
 * @author Lukas Heinzl
 *
 * @param <T>
 *            The Type of Node to use the Algorithm with, must be a sub-class of {@link cf.lukasheinzl.graph.Node}
 */
public interface GraphAlgorithm<T extends Node<T>>{

	/**
	 * Finds the path from the start node (first element) to the end node (last element in list)
	 * 
	 * @param nodes
	 *            The list of nodes
	 * @return The list of nodes on the path in order or null if no path from start to finish could be found
	 */
	public List<T> findPath(List<T> nodes);

	/**
	 * Get an algorithm-instance by name
	 * 
	 * @param <T>
	 *            The Type of Node to use the Algorithm with, must be a sub-class of {@link cf.lukasheinzl.graph.Node}
	 * 
	 * @param name
	 *            The name of the algorithm
	 * @return An instance of the algorithm or null if the given algorithm does not exist
	 */
	public static <T extends Node<T>> GraphAlgorithm<T> getAlgorithm(String name){
		if("DepthFirst".equalsIgnoreCase(name)){
			return new DepthFirstSearch<>();
		} else if("BreadthFirst".equalsIgnoreCase(name)){
			return new BreadthFirstSearch<>();
		} else if("Dijkstra".equalsIgnoreCase(name)){
			return new Dijkstra<>();
		} else if("AStar".equalsIgnoreCase(name)){
			return new AStar<>();
		}

		return null;
	}

}
