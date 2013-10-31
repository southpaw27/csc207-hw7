package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

/**
 * A predicate. That is, a procedure that can be applied to values and that
 * returns true or false.
 */
public interface PredicateTwo<T> {
    /**
     * Test to see if val meets the predicate.
     */
    public boolean test(T val, T valTwo);
} // interface PredicateTwo<T>
