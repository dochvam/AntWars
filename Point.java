/**
 * Point object for use with Watershed algorithms. Used in queueing specific spots
 * on the grid.
 *
 * @author <a href="mailto:bg5087a@american.edu">Ben Goldstein</a>
 * @version 1.1
 */

public class Point implements Comparable<Point>{

	public int x;
	public int y;
	public int depth;
	private int id;

	 /**
     * Point constructor for use in Terrain and Watershed algorithms
     *
     * @param x is the x-coordinate
     * @param y is the y-coordinate
     * @param value is the point's cover
     */
	public Point (int x, int y, int depth) {
		this.x = x;
		this.y = y;
		this.depth = depth;
		this.id = (int)(Math.random() * 100000);
	}

	 /**
     * compareTo override. The lesser height has greater priority.
     *
     * @param other is the point to compare with.
     */
	@Override
	public int compareTo(Point other) {

		if (other == null) throw new NullPointerException();

		if (this.depth != other.depth) return ((this.depth) - (other.depth));
		else return (this.id - other.id);
	} 

	 /**
     * toString returns the coordinates of the point followed by its height.
     */
	@Override
	public String toString(){
		return x + "x" + y + " h: " + depth;
	}


	 /**
     * equals override. Compares coordinates; assumes two points on the same Terrain object
     * with the same loc have the same properties.
     *
     * @param obj is the other item.
     */
	@Override
	public boolean equals(Object obj){
		
		Point other;

		if (obj == null) return false;
		if (obj instanceof Point){
			other = (Point) obj;
		} else return false;

		if (other.x == this.x && other.y == this.y) return true;
		return false;
	}


	 /**
     * hashCode override corresponding to the above equals override
     */
	@Override
	public int hashCode() {
		return this.x * 777 + this.y;
	}

}