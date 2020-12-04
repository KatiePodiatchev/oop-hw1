package homework1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A GeoFeature represents a route from one location to another along a
 * single geographic feature. GeoFeatures are immutable.
 * <p>
 * GeoFeature abstracts over a sequence of GeoSegments, all of which have
 * the same name, thus providing a representation for nonlinear or nonatomic
 * geographic features. As an example, a GeoFeature might represent the
 * course of a winding river, or travel along a road through intersections
 * but remaining on the same road.
 * <p>
 * GeoFeatures are immutable. New GeoFeatures can be constructed by adding
 * a segment to the end of a GeoFeature. An added segment must be properly
 * oriented; that is, its p1 field must correspond to the end of the original
 * GeoFeature, and its p2 field corresponds to the end of the new GeoFeature,
 * and the name of the GeoSegment being added must match the name of the
 * existing GeoFeature.
 * <p>
 * Because a GeoFeature is not necessarily straight, its length - the
 * distance traveled by following the path from start to end - is not
 * necessarily the same as the distance along a straight line between
 * its endpoints.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint       // location of the start of the geographic feature
 *   end : GeoPoint         // location of the end of the geographic feature
 *   startHeading : angle   // direction of travel at the start of the geographic feature, in degrees
 *   endHeading : angle     // direction of travel at the end of the geographic feature, in degrees
 *   geoSegments : sequence	// a sequence of segments that make up this geographic feature
 *   name : String          // name of geographic feature
 *   length : real          // total length of the geographic feature, in kilometers
 * </pre>
 **/
public class GeoFeature {
	
	// Implementation hint:
	// When asked to return an Iterator, consider using the iterator() method
	// in the List interface. Two nice classes that implement the List
	// interface are ArrayList and LinkedList. If comparing two Lists for
	// equality is needed, consider using the equals() method of List. More
	// info can be found at:
	//   http://docs.oracle.com/javase/8/docs/api/java/util/List.html
	
	 private final ArrayList<GeoSegment> geoSegmentList; 

	
  	// TODO Write abstraction function and representation invariant
	// Abstraction function: geoSegmentList is a list of GeoSegments 
	// 
	// Representation invariant: GeoSegments is a non empty list of GeoSegments. If gs2 comes right after gs1 in geoSegmentList,
	// then the first endpoint of gs2 is equal to the second endpoint of gs1. 
	// All GeoSegments in the list must have equal names.

	
	/**
     * Constructs a new GeoFeature.
     * @requires gs != null
     * @effects Constructs a new GeoFeature, r, such that
     *	        r.name = gs.name &&
     *          r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public GeoFeature(GeoSegment gs) {
  		// TODO Implement this constructor
  		geoSegmentList = new ArrayList<GeoSegment>();
  		geoSegmentList.add(gs);
  		assert(this.checkRep());
  	}
  	
	/**
     * Constructs a new GeoFeature.
     * @requires gsList != null && gsLis.size() > 0 && all GeoSegment in gsList have the same name
     * @effects Constructs a new GeoFeature, r, such that 
     *	        r.name = the name of all the segments in gsList &&
     *          r.startHeading = gsLis[0].heading &&
     *          r.endHeading = gsList[gsList.size - 1].heading &&
     *          r.start = gsList[0].p1 &&
     *          r.end = gsList[gsList.size - 1].p2
     **/
  	public GeoFeature(ArrayList<GeoSegment> gsList) {
  		// TODO Implement this constructor
  		geoSegmentList = gsList.clone();
  		assert(this.checkRep());
  	}
  	
 	/**
 	  * Returns name of geographic feature.
      * @return name of geographic feature
      */
  	public String getName() {
  		assert(this.checkRep());
  		String name = geoSegmentList.get(0).getName();
  		assert(this.checkRep());
  		return name;
  	}


  	/**
  	 * Returns location of the start of the geographic feature.
     * @return location of the start of the geographic feature.
     */
  	public GeoPoint getStart() {
  		assert(this.checkRep());
  		GeoPoint start = geoSegmentList.get(0).getP1();
  		assert(this.checkRep());
  		return start;
  	}


  	/**
  	 * Returns location of the end of the geographic feature.
     * @return location of the end of the geographic feature.
     */
  	public GeoPoint getEnd() {
  		assert(this.checkRep());
  		GeoPoint end = geoSegmentList.get(geoSegmentList.size() - 1).getP2();
  		assert(this.checkRep());
  		return end;
  	}


  	/**
  	 * Returns direction of travel at the start of the geographic feature.
     * @return direction (in standard heading) of travel at the start of the
     *         geographic feature, in degrees.
     */
  	public double getStartHeading() {
  		assert(this.checkRep());
  		double startHeading = geoSegmentList.get(0).getHeading();
  		assert(this.checkRep());
  		return startHeading;
  	}


  	/**
  	 * Returns direction of travel at the end of the geographic feature.
     * @return direction (in standard heading) of travel at the end of the
     *         geographic feature, in degrees.
     */
  	public double getEndHeading() {
  		assert(this.checkRep());
  		double endHeading = geoSegmentList.get(geoSegmentList.size() - 1).getHeading();
  		assert(this.checkRep());
  		return endHeading;
  	}


  	/**
  	 * Returns total length of the geographic feature, in kilometers.
     * @return total length of the geographic feature, in kilometers.
     *         NOTE: this is NOT as-the-crow-flies, but rather the total
     *         distance required to traverse the geographic feature. These
     *         values are not necessarily equal.
     */
  	public double getLength() {
  		assert(this.checkRep());
  		
  		double length = 0;
  		for (GeoSegment segment: geoSegmentList) {
  			length += segment.getLength();
  		}
  		
  		assert(this.checkRep());
  		return length;
  	}


  	/**
   	 * Creates a new GeoFeature that is equal to this GeoFeature with gs
   	 * appended to its end.
     * @requires gs != null && gs.p1 = this.end && gs.name = this.name.
     * @return a new GeoFeature r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *    	   r.length = this.length + gs.length
     **/
  	public GeoFeature addSegment(GeoSegment gs) {
  		assert(this.checkRep());
  		
  		geoSegmentList.add(gs);

  		assert(this.checkRep());
  		return this;
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this GeoFeature. All the
     * GeoSegments have the same name.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length-1 => (a[i].name == a[i+1].name &&
     *                                   a[i].p2   == a[i+1].p1))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     */
  	public Iterator<GeoSegment> getGeoSegments() {
  		assert(this.checkRep());
  		Iterator<GeoSegment> geoSegments = geoSegmentList.iterator();
  		assert(this.checkRep());
  		return geoSegments;
  	}


  	/**
     * Compares the argument with this GeoFeature for equality.
     * @return o != null && (o instanceof GeoFeature) &&
     *         (o.geoSegments and this.geoSegments contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object obj) {
  		assert(this.checkRep());
		if (!(obj instanceof GeoFeature))
			return false;
		GeoFeature otherGeoFeature = (GeoFeature)obj;
		boolean isEqual = otherGeoFeature.getName().equals(this.getName()) && 
				otherGeoFeature.geoSegmentList.equals(this.geoSegmentList);
		assert(this.checkRep());
		return isEqual;
  	}


  	/**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {	
    	return this.getName().hashCode();
  	}


  	/**
  	 * Returns a string representation of this.
   	 * @return a string representation of this.
     **/
  	public String toString() {
  		assert(this.checkRep());
  		String representationString = this.getName() + ": " + this.getStart().toString() + " ";
  		for (GeoSegment segment:geoSegmentList) {
  			representationString += String.format("==> %s", segment.getP2().toString());
  		}
  		assert(this.checkRep());
  		return representationString;
  	}
  
  
  	private boolean checkRep() {
  		if (geoSegmentList == null || geoSegmentList.size() < 1 || geoSegmentList.contains(null)) {
  			return false;
  		}
  		GeoSegment previousSegment = null;
  		for (GeoSegment currentSegment:geoSegmentList) {
  			if (previousSegment != null) {
  				boolean isTheSameString = previousSegment.getName().equals(currentSegment.getName());
  				boolean isTheSamePoint = previousSegment.getP2().equals(currentSegment.getP1());
  	  			if (!isTheSameString && !isTheSamePoint) {
  	  				return false;
  	  			}
  			}
  			previousSegment = currentSegment;
  		}
  		return true;
  	}
}
