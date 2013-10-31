package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

import java.io.PrintWriter;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;
import edu.grinnell.glimmer.ushahidi.UshahidiUtils;
import edu.grinnell.glimmer.ushahidi.UshahidiWebClient;

public class UshahidiLatLong extends DoublyLinkedList {
    /**
     * returns the average latitude and the average longitude for a
     * DoublyLinkedList of UshahidiIncidents
     * 
     * @param incidents
     * @preConditions incidents is a DoublyLinkedList of UshahidiIncidents and
     *                all the incidents should have valid latitudes and
     *                longitudes
     * @postconditions returns a Double[] = {averageLatitude, averageLongitude};
     */
    public static Double[] avLatLong(
	    DoublyLinkedList<UshahidiIncident> incidents) throws Exception {
	DoublyLinkedListCursor<UshahidiIncident> dllc = new DoublyLinkedListCursor<UshahidiIncident>(
		incidents.front);
	// first, move through list and count total number of incidents
	int count = 0;
	while (incidents.hasNext(dllc)) {
	    count++;
	    incidents.advance(dllc);
	} // while
	/*
	 * then, move backwards adding (individual latitude or longitude) /
	 * total number of incidents to the appropriate running sum
	 */

	Double avLat = new Double(0);
	Double avLong = new Double(0);

	while (incidents.hasPrev(dllc)) {
	    avLat += incidents.getPrev(dllc).getLocation().getLatitude()
		    / count;
	    avLong += incidents.getPrev(dllc).getLocation().getLongitude()
		    / count;
	} // while
	  // return averages
	return new Double[] { avLat, avLong };
    } // avLatLong(DoublyLinkedList<UshahidiIncident>)

    /**
     * select creates a list of values that fulfill the predicate test
     * 
     * @param incidents
     * @param pred
     * @return a list of UshahidiIncidents
     * @preCondition pred must be a PredicateTwo incidents must be a
     *               DoublyLinkedList<UshahidiIncident> and all values in
     *               incidents must have valid latitudes and longitudes
     * @postCondition a list of values that cause the predicate to be true is
     *                returned
     */

    public static DoublyLinkedList<UshahidiIncident> selectNearby(
	    DoublyLinkedList<UshahidiIncident> incidents,
	    PredicateTwo<Double> pred) throws Exception {
	DoublyLinkedListIterator<UshahidiIncident> it = new DoublyLinkedListIterator<UshahidiIncident>(
		incidents);
	DoublyLinkedList<UshahidiIncident> sel = new DoublyLinkedList<UshahidiIncident>();

	while (it.hasNext()) {
	    UshahidiIncident test = it.next();
	    Double testLat = test.getLocation().getLatitude();
	    Double testLong = test.getLocation().getLongitude();
	    if (pred.test(testLat, testLong)) {
		sel.append(test);
	    } // if
	} // while
	return sel;
    } // select(DoublyLinkedList<UshahidiIncident>, Predicate<T>)

    /**
     * extracts a DoublyLinkedList of all the UshahidiIncidents within distance
     * of the average latitude and longitude of the list
     * 
     * @param incidents
     *            , a DoublyLinkedList of UshahidiIncidents
     * @param distance
     *            , a distance in degrees away
     * @preCondition incidents is a DoblyLinkedList<UshahidiIncident> and
     *               distance is a double of degrees away from average latitude
     *               and longitude
     * @return a DoublyLinkedList of the specified UshahidiIncidents
     * @throws Exception
     */
    public static DoublyLinkedList<UshahidiIncident> extractNearby(
	    DoublyLinkedList<UshahidiIncident> incidents, Double distance)
	    throws Exception {
	Double[] averages = avLatLong(incidents);
	return (selectNearby(incidents, new Nearby(distance, averages[0],
		averages[1])));
    } // extractNearby(DoublyLinkedList<UshahidiIncident>, Double)

    // from Assignment 5 by Andrew Kelley and Mira Hall
    /**
     * printIncident prints out a given incident.
     * 
     * @param pen
     * @param inc
     * @preCondition pen is a PrintWriter, and inc is an UshahidiIncident
     * @note NOTE: For purely reading the results of the list tests, the boolean
     *       printFlag set equal to false will allow for less clutter when
     *       reading the output as it only shows the title. If you want the
     *       whole incident to be printed, change printFlag to true.
     */
    public static void printIncident(PrintWriter pen, UshahidiIncident inc) {
	// This flag prevents some everything but the title from being printed
	boolean printFlag = true;
	pen.println("Incident: " + inc.getTitle());
	if (printFlag) {
	    pen.println("DESCRIPTION");
	    pen.println("Location: " + inc.getLocation()
		    + inc.getLocation().getLatitude()
		    + inc.getLocation().getLongitude());
	    pen.println("Status: (" + inc.getMode() + ", " + inc.getActive()
		    + ", " + inc.getVerified() + ")");
	} // if
    } // printIncident

    /**
     * Print a list of UshahidiIncidents.
     */
    public static void printIncidentList(PrintWriter pen,
	    DoublyLinkedList<UshahidiIncident> list) {
	for (UshahidiIncident val : list) {
	    printIncident(pen,
		    (edu.grinnell.glimmer.ushahidi.UshahidiIncident) val);
	} // for
	pen.println();
	pen.flush();
    } // printList(PrintWriter, LinkedList<T>)

    public static void main(String[] args0) throws Exception {
	// One that requires connecting to the server
	PrintWriter pen = new PrintWriter(System.out, true);

	UshahidiClient webclient = new UshahidiWebClient(
		"https://farmersmarket.crowdmap.com");
	DoublyLinkedList<UshahidiIncident> incidents = new DoublyLinkedList<UshahidiIncident>();
	while (webclient.hasMoreIncidents()) {
	    incidents.append(webclient.nextIncident());
	} // while
	  // remove the first location with an unsupported latitude and
	  // longitude
	incidents.delete((incidents.front()));

	Double[] avLatLong = avLatLong(incidents);

	pen.println("average latitude of the list: " + avLatLong[0]);
	pen.println("average longitude of the list: " + avLatLong[1]);

	DoublyLinkedList<UshahidiIncident> nearby = extractNearby(incidents,
		new Double(50));
	Experiment.printList(pen, nearby);

    } // main
} // class UshahidiLatLong
