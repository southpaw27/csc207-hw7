package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;
import edu.grinnell.glimmer.ushahidi.UshahidiUtils;
import edu.grinnell.glimmer.ushahidi.UshahidiWebClient;

public class UshahidiLatLong extends DoublyLinkedList {

	public static Double[] avLatLong(DoublyLinkedList<UshahidiIncident> incidents) throws Exception {
		DoublyLinkedListCursor<UshahidiIncident> dllc = 
				new DoublyLinkedListCursor<UshahidiIncident>(incidents.front);
		//first, move through list and count total number of incidents
		int count = 0;
		while (incidents.hasNext(dllc)) {
			count++;
			incidents.advance(dllc);
			if (dllc.pos.next == null) {
				count++;
			}
		}
		/*then, move backwards
		adding (individual latitude or longitude) / total number of incidents
		to the appropriate running sum */
		
		Double avLat = incidents.get(dllc).getLocation().getLatitude() / count;
		Double avLong = incidents.get(dllc).getLocation().getLongitude() / count;
		
		while(incidents.hasPrev(dllc)) {
			avLat += incidents.getPrev(dllc).getLocation().getLatitude() / count;
			avLong += incidents.getPrev(dllc).getLocation().getLongitude() / count;
		}
		//return averages
		return new Double[] {avLat, avLong};
	}
}
