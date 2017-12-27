package cf.lukasheinzl.graph.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cf.lukasheinzl.graph.Node;

/**
 * This class is an {@link cf.lukasheinzl.graph.algorithm.GraphAlgorithm GraphAlgorithm} implementation of the Breadth
 * First Search algorithm
 * 
 * @author Lukas Heinzl
 *
 * @param <T>
 *            The Type of Node to use the Algorithm with, must be a sub-class of {@link cf.lukasheinzl.graph.Node}
 */
public class BreadthFirstSearch<T extends Node<T>> implements GraphAlgorithm<T>{

	private List<T>		nodeQueue;
	private Map<T, T>	backtrackMap;
	private List<T>		visited;

	/**
	 * Package-private constructor to initialize instance variables. Can only be called from
	 * {@link cf.lukasheinzl.graph.algorithm.GraphAlgorithm#getAlgorithm(String) getAlgorithm(String)}
	 */
	BreadthFirstSearch(){
		nodeQueue = new ArrayList<>();
		backtrackMap = new HashMap<>();
		visited = new ArrayList<>();
	}

	/**
	 * Finds the path from the start node (first element) to the end node (last element in list)
	 * 
	 * @param nodes
	 *            The list of nodes
	 * @return The list of nodes on the path in order or null if no path from start to finish could be found
	 */
	@Override
	public List<T> findPath(List<T> nodes){
		T start = nodes.get(0);
		T end = nodes.get(nodes.size() - 1);

		nodeQueue.add(start);
		backtrackMap.put(start, null);

		while(!nodeQueue.isEmpty()){
			T n = nodeQueue.get(0);

			if(n == end){
				return getPath(n);
			}

			nodeQueue.remove(n);
			visited.add(n);

			for(T link: n.getLinks()){
				if(visited.contains(link)){
					continue;
				}

				if(!nodeQueue.contains(link)){
					nodeQueue.add(link);
					backtrackMap.put(link, n);
				}
			}
		}

		return null;
	}

	/**
	 * Returns a List&lt;T&gt; containing all nodes on the path between start and end
	 * 
	 * @param n
	 *            The end node
	 * @return The Path as a List&lt;T&gt;
	 */
	private List<T> getPath(T n){
		List<T> path = new ArrayList<>();

		while(n != null){
			path.add(0, n);
			n = backtrackMap.get(n);
		}

		return path;
	}
}
