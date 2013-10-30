import edu.grinnell.csc207.kelleyan.doublyLinkedLists.Predicate;
import java.lang.Math;

public class Nearby implements Predicate<T> {
	/*
	 * Fields
	 */
	
	/*
	 * The latitude & longitude of the location from which distance is calculated
	 */
	Double barrierLat;
	Double barrierLong;

	/*
	 * The farthest distance Nearby will return true for
	 */
	Double distance;
	
	
	/*
	 * Constructors
	 */
	public Nearby(Double distance, Double locationLat, Double locationLong) {
		this.distance = distance;
		this.barrierLat = locationLat + distance;
		this.barrierLong = locationLong + distance;
		double barrierDistance = Math.sqrt(Math.pow((this.distance + this.locationLat), 2)
				+ Math.pow(this.distance + this.locationLong, 2));
	}
	
	/*
	 * Methods
	 */
	
	public boolean test(Double testLat, Double testLong) {
		double barrierDistance = Math.sqrt(Math.pow((this.distance + this.locationLat), 2)
				+ Math.pow(this.distance + this.locationLong, 2));
		return (testLat <= this.distance + this.locationLat) &&
				(testLong <= this.distance + this.locationLong);
	}
}
