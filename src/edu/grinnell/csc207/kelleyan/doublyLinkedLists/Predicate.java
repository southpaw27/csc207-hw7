package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

public interface Predicate<T> {
    /**
     * Determine if a value meets the predicate.
     */
    boolean test(T val);
} // interface Predicate<T>
