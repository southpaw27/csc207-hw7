package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

import java.io.PrintWriter;

/**
 * A simple set of experiments to make sure that our amazing LinkedList class
 * works fine.
 */
public class Experiment {

    /**
     * Print a list of objects.
     */
    public static <T> void printList(PrintWriter pen, DoublyLinkedList<T> list) {
	for (T val : list) {
	    pen.print(val);
	    pen.print(" ");
	} // for
	pen.println();
	pen.flush();
    } // printList(PrintWriter, LinkedList<T>)

    public static <T> void printListReverse(PrintWriter pen,
	    DoublyLinkedList<T> list) throws Exception {
	DoublyLinkedListCursor<T> c = new DoublyLinkedListCursor<T>(list.back);

	if (list.isEmpty()) {
	    pen.print("Empty List");
	} else {
	    while (list.hasPrev(c)) {
		pen.print(list.getPrev(c));
		pen.print(" ");
	    } // for
	} // else
	pen.println();
	pen.flush();

    } // printList(PrintWriter, LinkedList<T>)

    public static void main(String[] args) throws Exception {
	// Set up output
	PrintWriter pen = new PrintWriter(System.out, true);

	// Create some lists
	DoublyLinkedList<String> strings = new DoublyLinkedList<String>();
	DoublyLinkedList<Integer> numbers = new DoublyLinkedList<Integer>();

	// Prepend a few elements
	numbers.prepend(42);
	numbers.prepend(77);
	numbers.prepend(11);

	// printList(pen, numbers);

	// Append a few elements
	numbers.append(1);
	numbers.append(2);
	numbers.append(3);
	// printList(pen, numbers);

	// create an iterator
	DoublyLinkedListIterator<Integer> ni = new DoublyLinkedListIterator<Integer>(
		numbers);

	// advance it
	ni.next();
	// create a cursor
	DoublyLinkedListCursor<Integer> nCurs0 = (DoublyLinkedListCursor) numbers
		.front();// new DoublyLinkedListCursor<Integer>(numbers.dummy);
	DoublyLinkedListCursor<Integer> nCurs1 = new DoublyLinkedListCursor<Integer>(
		ni.pos);

	// This shows that hasNext() and get() both work
	pen.println("Test 1");
	while (numbers.hasNext(nCurs0)) {
	    pen.print(numbers.get(nCurs0) + " ");
	    numbers.advance(nCurs0);
	}

	// Show that printList does indeed print the list
	pen.println("\n\nTest 2");
	printList(pen, numbers);

	// Show that both hasPrev() and getPrev() work
	pen.println("\nTest 3");
	printListReverse(pen, numbers);

	// Insert at 42, so 99 should be before 42 and after 77.
	pen.println("\nTest 4");
	pen.println("nCurs1 is at: " + numbers.get(nCurs1));
	numbers.insert(new Integer(99), nCurs1);
	// Show insert worked correctly and linked correctly both forwards and
	// backwards
	printList(pen, numbers);
	printListReverse(pen, numbers);

	// Move the iterator forward one location
	ni.next();
	ni.next();
	// Create new Cursor at new iterator position
	DoublyLinkedListCursor<Integer> nCurs2 = new DoublyLinkedListCursor<Integer>(
		ni.pos);
	// Test sublist by printing the numbers between nCurs1 and nCurs2
	pen.println("\nTest 5");
	pen.println("nCurs1 is at: " + numbers.get(nCurs1));
	pen.println("nCurs2 is at: " + numbers.get(nCurs2));
	// Should be {99 42}
	printList(pen,
		(DoublyLinkedList<Integer>) numbers.subList(nCurs1, nCurs2));

	// Create a Cursor at the end of the list to test sublist ending at
	// the end
	pen.println("\nTest 6");
	DoublyLinkedListCursor<Integer> nCurs3 = (DoublyLinkedListCursor) numbers
		.back();
	pen.println("nCurs1 is at: " + numbers.get(nCurs1));
	pen.println("nCurs3 is at: " + numbers.get(nCurs3));
	printList(pen,
		(DoublyLinkedList<Integer>) numbers.subList(nCurs1, nCurs3));

	// Show that swap works
	pen.println("\nTest 7");
	pen.print("Original list: ");
	printList(pen, numbers);
	pen.println("to be swapped: " + numbers.get(nCurs1) + " and "
		+ numbers.get(nCurs2));
	numbers.swap(nCurs1, nCurs2);
	// Show that numbers are still linked correctly both ways and that swap
	// worked
	printList(pen, numbers);
	printListReverse(pen, numbers);

	// Show that delete works
	pen.println("\nTest 8");
	pen.print("Original list: ");
	printList(pen, numbers);
	pen.println("Number to delete: " + numbers.get(nCurs2));
	numbers.delete(nCurs2);
	// Show that delete works and links are still good both ways
	printList(pen, numbers);
	printListReverse(pen, numbers);

	// Show that select works
	// Append 42 so that there are two 42s in the list so that select
	// will return a list of two 42s
	pen.println("\nTest 9");
	numbers.append(42);
	pen.print("Original list: ");
	printList(pen, numbers);
	// Should return {42 42}
	printList(pen, (DoublyLinkedList) numbers.select(new Equals<Integer>(
		new Integer(42))));

	// Show that search works
	pen.println("\nTest 10");
	// Copy nCurs4 and nCurs5 for use later in precedes test
	DoublyLinkedListCursor<Integer> nCurs4 = new DoublyLinkedListCursor<Integer>(
		nCurs1.prev);
	DoublyLinkedListCursor<Integer> nCurs5 = new DoublyLinkedListCursor<Integer>(
		nCurs2.prev);
	// Show where nCurs1 and nCurs2 are currently
	pen.println("nCurs1 is at: " + numbers.get(nCurs1));
	pen.println("nCurs2 is at: " + numbers.get(nCurs2));
	// Show that nCurs2 will find a 42
	pen.println(numbers
		.search(nCurs2, new Equals<Integer>(new Integer(42))));
	// Show that nCurs2 will not find a 34
	pen.println(numbers
		.search(nCurs2, new Equals<Integer>(new Integer(34))));

	// Show that precedes works
	pen.println("\nTest 11");
	// Show original list
	pen.print("Original list: ");
	printList(pen, numbers);
	pen.println("nCurs4 is at: " + numbers.get(nCurs4));
	pen.println("nCurs5 is at: " + numbers.get(nCurs5));
	// Should return true
	pen.println(numbers.precedes(nCurs4, nCurs5));
	// Should return false
	pen.println(numbers.precedes(nCurs5, nCurs4));

	// And we're done
	pen.close();
    } // main(String[])
} // class Experiment
