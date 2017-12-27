package cf.lukasheinzl.graph.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cf.lukasheinzl.graph.Node;

/**
 * This class is an {@link cf.lukasheinzl.graph.algorithm.GraphAlgorithm GraphAlgorithm} implementation of the A*
 * algorithm
 * 
 * @author Lukas Heinzl
 *
 * @param <T>
 *            The Type of Node to use the Algorithm with, must be a sub-class of {@link cf.lukasheinzl.graph.Node}
 */
public class AStar<T extends Node<T>> implements GraphAlgorithm<T>{

	private List<T>			nodeQueue;
	private Map<T, T>		backtrackMap;
	private List<T>			visited;
	private Map<T, Integer>	startDistMap;
	private Map<T, Integer>	totalDistMap;

	/**
	 * Package-private constructor to initialize instance variables. Can only be called from
	 * {@link cf.lukasheinzl.graph.algorithm.GraphAlgorithm#getAlgorithm(String) getAlgorithm(String)}
	 */
	AStar(){
		nodeQueue = new ArrayList<>();
		backtrackMap = new HashMap<>();
		visited = new ArrayList<>();
		startDistMap = new HashMap<>();
		totalDistMap = new HashMap<>();
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

		backtrackMap.put(start, null);
		startDistMap.put(start, 0);
		totalDistMap.put(start, start.getDistance(end));
		nodeQueue.add(start);

		while(!nodeQueue.isEmpty()){
			Collections.sort(nodeQueue, (n1, n2) -> totalDistMap.get(n1) - totalDistMap.get(n2));
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
				}

				int dist = startDistMap.get(n) + n.getDistance(link);
				if(startDistMap.containsKey(link) && dist >= startDistMap.get(link)){
					continue;
				}

				backtrackMap.put(link, n);
				startDistMap.put(link, dist);
				totalDistMap.put(link, dist + link.getDistance(end));
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
