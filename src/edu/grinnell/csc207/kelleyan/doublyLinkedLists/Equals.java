package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

public class Equals<T> implements Predicate<T> {

	/*
	 * Fields
	 */

	/*
	 * The value to test for equality with
	 */
	
	T i;
	
	/*
	 * Methods
	 */
	public boolean test(T item) {
		return this.i.equals(item);
	}
	
	/*
	 * Constructors
	 */
	public Equals(T i){
		this.i = i;
	}
}
