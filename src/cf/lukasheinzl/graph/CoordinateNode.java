package cf.lukasheinzl.graph;

/**
 * A subclass of {@link cf.lukasheinzl.graph.Node Node} containing an x,y coordinate for distance measuring
 * 
 * @author Lukas Heinzl
 *
 */
public class CoordinateNode extends Node<CoordinateNode>{

	private int	x;
	private int	y;

	/**
	 * Sets the coordinates of this Node
	 * 
	 * @param x
	 *            The x coordinate
	 * @param y
	 *            The y coordinate
	 */
	public CoordinateNode(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate
	 * 
	 * @return The x coordinate
	 */
	public int getX(){
		return x;
	}

	/**
	 * Returns the y coordinate
	 * 
	 * @return The y coordinate
	 */
	public int getY(){
		return y;
	}

	/**
	 * Returns the distance between this Node and another {@link cf.lukasheinzl.graph.CoordinateNode CoordinateNode}
	 * <br>
	 * If the x coordinates are the same it returns the difference of the y coordinates and vice-versa. <br>
	 * If both coordinates are different the distance if calculated as: <br>
	 * {@link java.lang.Math#round(double) Math.round}({@link java.lang.Math#sqrt(double) Math.sqrt}(dx * dx + dy * dy))
	 */
	@Override
	public int getDistance(CoordinateNode other){
		if(other == null){
			return Integer.MAX_VALUE;
		}

		if(this.x == other.x){
			return Math.abs(this.y - other.y);
		} else if(this.y == other.y){
			return Math.abs(this.x - other.x);
		}

		int dx = Math.abs(this.x - other.x);
		int dy = Math.abs(this.y - other.y);
		return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
	}

}
