package edu.grinnell.csc207.kelleyan.doublyLinkedLists;
import java.io.PrintWriter;
import java.util.ArrayList;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiWebClient;

public class SummaryInfo {

    /*
     * Reads UshahidiIncidents from www.farmersmarket.crowdmap.com Eliminates
     * the first unsupported item Prints the average latitude and longitude of
     * the incidents, then Prints incidents within 30 degrees, then those within
     * 50, then those within 100, then those within 360 (all incidents)
     */
    public static void main(String[] args) throws Exception {
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

	Double[] avLatLong = UshahidiLatLong.avLatLong(incidents);

	pen.println("average latitude of the list: " + avLatLong[0]);
	pen.println("\naverage longitude of the list: " + avLatLong[1]);

	Double[] distances = { new Double(30), new Double(50), new Double(80),
		new Double(100), new Double(360) };
	DoublyLinkedList[] groupings = new DoublyLinkedList[distances.length];
	for (int i = 0; i < distances.length; i++) {
	    groupings[i] = UshahidiLatLong.extractNearby(incidents,
		    distances[i]);
	} // for

	for (int i = 0; i < groupings.length; i++) {
	    pen.println("Incidents within " + distances[i]
		    + " degrees of average location:");
	    UshahidiLatLong.printIncidentList(pen, groupings[i]);
	} // for
    } // while
} // class SummaryInfo
