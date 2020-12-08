package homework1;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A Route is a path that traverses arbitrary GeoSegments, regardless
 * of their names.
 * <p>
 * Routes are immutable. New Routes can be constructed by adding a segment 
 * to the end of a Route. An added segment must be properly oriented; that 
 * is, its p1 field must correspond to the end of the original Route, and
 * its p2 field corresponds to the end of the new Route.
 * <p>
 * Because a Route is not necessarily straight, its length - the distance
 * traveled by following the path from start to end - is not necessarily
 * the same as the distance along a straight line between its endpoints.
 * <p>
 * Lastly, a Route may be viewed as a sequence of geographical features,
 * using the <tt>getGeoFeatures()</tt> method which returns an Iterator of
 * GeoFeature objects.
 * <p>
 * <b>The following fields are used in the specification:</b>
 * <pre>
 *   start : GeoPoint            // location of the start of the route
 *   end : GeoPoint              // location of the end of the route
 *   startHeading : angle        // direction of travel at the start of the route, in degrees
 *   endHeading : angle          // direction of travel at the end of the route, in degrees
 *   geoFeatures : sequence      // a sequence of geographic features that make up this Route
 *   geoSegments : sequence      // a sequence of segments that make up this Route
 *   length : real               // total length of the route, in kilometers
 *   endingGeoSegment : GeoSegment  // last GeoSegment of the route
 * </pre>
 **/
public class Route {

	private final ArrayList<GeoFeature> geoFeatureList;
	
	// Abstraction Function:
	// The sequence of geographic features that make up this Route are represented by the 
	// geoFeatureList.

	// Representation invariant: geoFeatureList != null && geoFeatureList.size > 0 &&
	// for every two consecutive GeoFeatures gf_1 and gf_2 in geoFeatureList gf_1 != null &&
	// gf_2 != null && gf_1.name != gf_2.name. 
	

  	/**
  	 * Constructs a new Route.
     * @requires gs != null
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = gs.heading &&
     *          r.endHeading = gs.heading &&
     *          r.start = gs.p1 &&
     *          r.end = gs.p2
     **/
  	public Route(GeoSegment gs) {  		
  		GeoFeature feature = new GeoFeature(gs);
  		this.geoFeatureList = new ArrayList<>();
  		this.geoFeatureList.add(feature);

  		assert(this.checkRep());
  	}
  	
  	
  	/**
  	 * Constructs a new Route.
     * @requires featureList != null && for every two consecutive GeoFeatures gf_1 and gf_2
				 in geoFeatureList gf_1 != null && gf_2 != null && gf_1.name != gf_2.name && 
				 gf_1.end == gf_2.start. 
     * @effects Constructs a new Route, r, such that
     *	        r.startHeading = featureList.end.heading &&
     *          r.endHeading = featureList.start.heading &&
     *          r.start = featureList.start &&
     *          r.end = featureList.end &&
     *          r.length = sum of lengths of features in featureList; 
     **/
  	public Route(ArrayList<GeoFeature> featureList) {
  		this.geoFeatureList = (ArrayList<GeoFeature>)featureList.clone();
  		assert(this.checkRep());
  	}


    /**
     * Returns location of the start of the route.
     * @return location of the start of the route.
     **/
  	public GeoPoint getStart() {
  		assert(this.checkRep());
  		GeoPoint startPoint = this.geoFeatureList.get(0).getStart();
  		assert(this.checkRep());
  		return startPoint;
  	}


  	/**
  	 * Returns location of the end of the route.
     * @return location of the end of the route.
     **/
  	public GeoPoint getEnd() {
  		assert(this.checkRep());
  		GeoPoint endPoint = this.geoFeatureList.get(0).getEnd();
  		assert(this.checkRep());
  		return endPoint;
  	}


  	/**
  	 * Returns direction of travel at the start of the route, in degrees.
   	 * @return direction (in compass heading) of travel at the start of the
   	 *         route, in degrees. 0 in case the length at the start of the 
   	 *         geographic feature is 0.
   	 **/
  	public double getStartHeading() {
  		assert(this.checkRep());
  		double startHeading = this.geoFeatureList.get(0).getStartHeading();
  		assert(this.checkRep());
  		return startHeading;
  	}


  	/**
  	 * Returns direction of travel at the end of the route, in degrees.
     * @return direction (in compass heading) of travel at the end of the
     *         route, in degrees. 0 in case the length at the end of the 
     *         rout is 0.
     **/
  	public double getEndHeading() {
  		assert(this.checkRep());
  		double endHeading = geoFeatureList.get(geoFeatureList.size() -1).getEndHeading();
  		assert(this.checkRep());
  		return endHeading;
  	}


  	/**
  	 * Returns total length of the route.
     * @return total length of the route, in kilometers.  NOTE: this is NOT
     *         as-the-crow-flies, but rather the total distance required to
     *         traverse the route. These values are not necessarily equal.
   	 **/
  	public double getLength() {
  		assert(this.checkRep());
  		double length = 0;
  		for (GeoFeature feature: geoFeatureList) {
  			length += feature.getLength();
  		}
  		assert(this.checkRep());
  		return length;
  	}


  	/**
     * Creates a new route that is equal to this route with gs appended to
     * its end.
   	 * @requires gs != null && gs.p1 == this.end
     * @return a new Route r such that
     *         r.end = gs.p2 &&
     *         r.endHeading = gs.heading &&
     *         r.length = this.length + gs.length
     **/
  	public Route addSegment(GeoSegment gs) {
  		assert(this.checkRep());
  		ArrayList<GeoFeature> newFeatureList = (ArrayList<GeoFeature>)this.geoFeatureList.clone();
  		
  		GeoFeature newFeature;
  		GeoFeature lastFeature = newFeatureList.get(newFeatureList.size()-1);
  		String lastFeatureName = lastFeature.getName();
  		if (gs.getName().equals(lastFeatureName)) {
  			newFeatureList.remove(lastFeature);
  			newFeature = lastFeature.addSegment(gs);
  		} else {
  			newFeature = new GeoFeature(gs);
  		}
  		newFeatureList.add(newFeature);
  		
		Route newRoute = new Route(newFeatureList);
		assert(this.checkRep());
		return newRoute;
  		
  	}


    /**
     * Returns an Iterator of GeoFeature objects. The concatenation
     * of the GeoFeatures, in order, is equivalent to this route. No two
     * consecutive GeoFeature objects have the same name.
     * @return an Iterator of GeoFeatures such that
     * <pre>
     *      this.start        = a[0].start &&
     *      this.startHeading = a[0].startHeading &&
     *      this.end          = a[a.length - 1].end &&
     *      this.endHeading   = a[a.length - 1].endHeading &&
     *      this.length       = sum(0 <= i < a.length) . a[i].length &&
     *      for all integers i
     *          (0 <= i < a.length - 1 => (a[i].name != a[i+1].name &&
     *                                     a[i].end  == a[i+1].start))
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoFeature
     **/
  	public Iterator<GeoFeature> getGeoFeatures() {
  		assert(this.checkRep());
  		Iterator<GeoFeature> iterator = this.geoFeatureList.iterator();
  		assert(this.checkRep());
  		return iterator;
  	}


  	/**
     * Returns an Iterator of GeoSegment objects. The concatenation of the
     * GeoSegments, in order, is equivalent to this route.
     * @return an Iterator of GeoSegments such that
     * <pre>
     *      this.start        = a[0].p1 &&
     *      this.startHeading = a[0].heading &&
     *      this.end          = a[a.length - 1].p2 &&
     *      this.endHeading   = a[a.length - 1].heading &&
     *      this.length       = sum (0 <= i < a.length) . a[i].length
     * </pre>
     * where <code>a[n]</code> denotes the nth element of the Iterator.
     * @see homework1.GeoSegment
     **/
  	public Iterator<GeoSegment> getGeoSegments() {
  		assert(this.checkRep());
  		ArrayList<GeoSegment> segments = this.flattenFeatureList(this.geoFeatureList);
  		assert(this.checkRep());
  		return segments.iterator();
  	}


  	/**
     * Compares the specified Object with this Route for equality.
     * @return true iff (o instanceof Route) &&
     *         (o.geoFeatures and this.geoFeatures contain
     *          the same elements in the same order).
     **/
  	public boolean equals(Object obj) {
  		assert(this.checkRep());
		if (!(obj instanceof Route))
			return false;
		Route otherRoute = (Route)obj;
		boolean isEqual = otherRoute.geoFeatureList.equals(this.geoFeatureList);
		assert(this.checkRep());
		return isEqual;
  	}


    /**
     * Returns a hash code for this.
     * @return a hash code for this.
     **/
  	public int hashCode() {
  		assert(this.checkRep());
    	return 1;
  	}


    /**
     * Returns a string representation of this.
     * @return a string representation of this.
     **/
  	public String toString() {
  		assert(this.checkRep());
  		String representationString = "";
  		for (GeoFeature feature: geoFeatureList) {
  			representationString += feature.toString();
  			representationString += "\n";
  		}
  		assert(this.checkRep());
  		return representationString;
  	}

  	
  	private ArrayList<GeoSegment> flattenFeatureList(ArrayList<GeoFeature> featureList) {
  		ArrayList<GeoSegment> segmentList = new ArrayList<>();
  		for (GeoFeature feature:this.geoFeatureList) {
  			Iterator<GeoSegment> featureIterator = feature.getGeoSegments();
  			while(featureIterator.hasNext()) {
  				segmentList.add(featureIterator.next());
  			}
  		}
  		return segmentList;
  	}
 
  	
  	private boolean checkRep() {
  		if (geoFeatureList == null || geoFeatureList.size() > 0 || geoFeatureList.contains(null)) {
  			return false;
  		}
  		return this.checkNoConsecutiveFeaturesWithSameName() &&
  			   this.checkNewFeatureBeginsWithEndOfOld();
  	}

  	
  	private boolean checkNoConsecutiveFeaturesWithSameName() {
  		GeoFeature previousFeature = null;
  		for (GeoFeature currentFeature:geoFeatureList) {
  			if (previousFeature != null) {
  				if (currentFeature.getName().equals(previousFeature.getName())) {
  					return false;
  				}
  			}
  			previousFeature = currentFeature;
  		}
  		return true;
  	}
  	
  	
  	private boolean checkNewFeatureBeginsWithEndOfOld() {
  		GeoFeature previousFeature = null;
  		for (GeoFeature currentFeature:geoFeatureList) {
  			if (previousFeature != null) {
  				GeoPoint lastPointAtPrevious = previousFeature.getEnd();
  				GeoPoint firstPointAtCurrent = currentFeature.getStart();
  				if (!lastPointAtPrevious.equals(firstPointAtCurrent)) {
  					return false;
  				}
  			}
  			previousFeature = currentFeature;
  		}
  		return true;
  	}
 
}
