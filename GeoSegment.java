package homework1;

/**
 * A GeoSegment models a straight line segment on the earth. GeoSegments 
 * are immutable.
 * <p>
 * A compass heading is a nonnegative real number less than 360. In compass
 * headings, north = 0, east = 90, south = 180, and west = 270.
 * <p>
 * When used in a map, a GeoSegment might represent part of a street,
 * boundary, or other feature.
 * As an example usage, this map
 * <pre>
 *  Trumpeldor   a
 *  Avenue       |
 *               i--j--k  Hanita
 *               |
 *               z
 * </pre>
 * could be represented by the following GeoSegments:
 * ("Trumpeldor Avenue", a, i), ("Trumpeldor Avenue", z, i),
 * ("Hanita", i, j), and ("Hanita", j, k).
 * </p>
 * 
 * </p>
 * A name is given to all GeoSegment objects so that it is possible to
 * differentiate between two GeoSegment objects with identical
 * GeoPoint endpoints. Equality between GeoSegment objects requires
 * that the names be equal String objects and the end points be equal
 * GeoPoint objects.
 * </p>
 *
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   name : String       // name of the geographic feature identified
 *   p1 : GeoPoint       // first endpoint of the segment
 *   p2 : GeoPoint       // second endpoint of the segment
 *   length : real       // straight-line distance between p1 and p2, in kilometers
 *   heading : angle     // compass heading from p1 to p2, in degrees
 * </pre>
 **/
public class GeoSegment  {
	
	private final GeoPoint p1;
	private final GeoPoint p2;
	private final String name;

	
	// Abstraction function: p1 represents the first endpoint of the segment, p2 represents the second endpoint of the segment. 
	// name represents the name of the segment. If p1 == p2 the length of the segment is 0 and the heading is 0.
	// Representation invariant: name != null && p1 != null && p2 != null.
	
	
  	/**
     * Constructs a new GeoSegment with the specified name and endpoints.
     * @requires name != null && p1 != null && p2 != null
     * @effects constructs a new GeoSegment with the specified name and endpoints.
     **/
  	public GeoSegment(String name, GeoPoint p1, GeoPoint p2) {
  		// TODO Implement this method
  		this.name = name;
  		this.p1 = p1;
  		this.p2 = p2;
  		assert(this.checkRep());
  	}


  	/**
     * Returns a new GeoSegment like this one, but with its endpoints reversed.
     * @return a new GeoSegment gs such that gs.name = this.name
     *         && gs.p1 = this.p2 && gs.p2 = this.p1
     **/
  	public GeoSegment reverse() {
  		assert(this.checkRep());
  		GeoSegment reversed = new GeoSegment(this.name, this.p2, this.p1);
  		assert(this.checkRep());
  		return reversed;
  	}


  	/**
  	 * Returns the name of this GeoSegment.
     * @return the name of this GeoSegment.
     */
  	public String getName() {
  		assert(this.checkRep());
  		String name = this.name;
  		assert(this.checkRep());
  		return name;
  	}


  	/**
  	 * Returns first endpoint of the segment.
     * @return first endpoint of the segment.
     */
  	public GeoPoint getP1() {
  		assert(this.checkRep());
  		return p1;
  	}


  	/**
  	 * Returns second endpoint of the segment.
     * @return second endpoint of the segment.
     */
  	public GeoPoint getP2() {
  		assert(this.checkRep());
  		return p2;
  	}


  	/**
  	 * Returns the length of the segment.
     * @return the length of the segment, using the flat-surface, near the
     *         Technion approximation.
     */
  	public double getLength() {
  		assert(this.checkRep());
  		double length = p1.equals(p2) ? 0 : p1.distanceTo(p2);
  		assert(this.checkRep());
  		return length;
  	}


  	/**
  	 * Returns the compass heading from p1 to p2.
     * @requires this.length != 0
     * @return the compass heading from p1 to p2, in degrees, using the
     *         flat-surface, near the Technion approximation.
     **/
  	public double getHeading() {
  		assert(this.checkRep());
  		double heading = p1.equals(p2) ? 0 : p1.headingTo(p2);
  		assert(this.checkRep());
  		return heading;
  	}


  	/**
     * Compares the specified Object with this GeoSegment for equality.
     * @return gs != null && (gs instanceof GeoSegment)
     *         && gs.name = this.name && gs.p1 = this.p1 && gs.p2 = this.p2
   	 **/
  	public boolean equals(Object o) {
  		assert(this.checkRep());
		if (!(o instanceof GeoSegment))
			return false;
		GeoSegment otherGeoSegment = (GeoSegment)o;
		boolean isEqual = otherGeoSegment.name.equals(this.name) && 
				otherGeoSegment.p1.equals(this.p1) && 
				otherGeoSegment.p2.equals(this.p2);
		assert(this.checkRep());
		return isEqual;
  	}


  	/**
  	 * Returns a hash code value for this.
     * @return a hash code value for this.
     **/
  	public int hashCode() {
  		assert(this.checkRep());
    	int hashCode = p1.hashCode() ^ p2.hashCode();
    	assert(this.checkRep());
    	return hashCode;
  	}


  	/**
  	 * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
  		assert(this.checkRep());
  		return String.format("%s: %s --> %s", name, p1.toString(), p2.toString());
  	}
  	

  	private boolean checkRep() {
  		return name != null && p1 != null && p2 != null;
  	}

}

