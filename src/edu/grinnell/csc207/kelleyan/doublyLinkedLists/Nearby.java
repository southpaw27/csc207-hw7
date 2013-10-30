package edu.grinnell.csc207.kelleyan.doublyLinkedLists;
import java.lang.Math;

public class Nearby implements PredicateTwo<Double> {
	/*
	 * Fields
	 */
	
	/*
	 * The latitude & longitude of the location from which distance is calculated
	 */
	Double averageLat;
	Double averageLong;

	/*
	 * The farthest distance Nearby will return true for
	 */
	Double distance;
	
	
	/*
	 * Constructors
	 */
	public Nearby(Double distance, Double averageLat, Double averageLong) {
		this.distance = distance;
		this.averageLat = averageLat;
		this.averageLong = averageLong;
	}
	
	/*
	 * Methods
	 */
	/**
	 * test looks to see if the testLat and testLong are within a certain
	 * user chosen distance away from the average latitude and longitude
	 * of a list of Ushahidi incidents.
	 * 
	 * @param testLat, a latitude
	 * @param testLong, a longitude
	 * @return true if params are within or at the distance from the average 
	 * latitude and longitude, false if not.
	 */
	public boolean test (Double testLat, Double testLong) {
		// find the distance from the testLat and testLong and the averageLat
		// and averageLong
		double distFromLat = Math.abs(this.averageLat - testLat);
		double distFromLong = Math.abs(this.averageLong - testLong);
		/*
		 *  the equation for a unit circle is x^2 + y^2 = r^2 where r^2 is the
		 *  radius of the circle squared. The user chosen distance away from
		 *  the averageLat and averageLong is the same as setting the x and y
		 *  components of the above equation. Using this, r^2 is equal to 
		 *  2*d^2. We can then compare this r^2 to the r^2 found when taking
		 *  the differences found above and plugging distFromLat in to x, and 
		 *  distFromLong into y. If the r^2 here is smaller than or equal to 
		 *  the r^2 from the distance chosen, then the test point is within 
		 *  that distance and test will return true, otherwise false.
		 */
		return (distFromLat*distFromLat + distFromLong*distFromLong) <=
				(2*this.distance*this.distance);
		/*
		 * a side note: we do not have to worry about overflow here because
		 * the max a latitude can be is 90 degrees. 90 degrees squared times 2
		 * is definitely less than max_int
		 */
	}
}
