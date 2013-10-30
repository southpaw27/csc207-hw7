package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

import edu.grinnell.glimmer.ushahidi.UshahidiClient;
import edu.grinnell.glimmer.ushahidi.UshahidiIncident;
import edu.grinnell.glimmer.ushahidi.UshahidiLocation;
import edu.grinnell.glimmer.ushahidi.UshahidiUtils;
import edu.grinnell.glimmer.ushahidi.UshahidiWebClient;

public class UshahidiReadIntoList {

	public static DoublyLinkedList<UshahidiIncident> 
	readIntoList(UshahidiClient incidents) throws Exception {
		DoublyLinkedList<UshahidiIncident> dllu = 
				new DoublyLinkedList<UshahidiIncident>();
		while (incidents.hasMoreIncidents()) {
			dllu.append(incidents.nextIncident());
		}
		return dllu;
	}

}
