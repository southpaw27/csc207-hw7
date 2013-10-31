package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Iterator;


/**
 * Doubly linked lists.
 *   This class implements a doubly linked list. A doubly linked list
 *   is a list whose nodes are connected with pointers to both the next
 *   and the previous element. This class gives you the opportunity to
 *   use iterators like a traditional linked list, as well as cursors, a
 *   slightly different implementation of a place holder. to iterate through
 *   and mutate a doubly linked list.
 * @author Mira Hall
 * @author Andrew Kelley
 * 
 */
public class DoublyLinkedList<T> implements ListOf<T> {

    // FIELDS

    /**
     * The front of the list.  Set to null for the empty list.
     */
    Node<T> front;

    /**
     * The back of the list.  Set to null for the empty list.
     */
    Node<T> back;
    
    /**
     * The dummy node!  Yay!
     */
    Node<T> dummy;
    
    // CONSTRUCTORS
    /**
     * Create a new linked list.
     */
    public DoublyLinkedList() {
    	this.dummy = new Node(null);
    	this.front = dummy;
    	this.back = dummy;
    	// mutations needed?
    	
    } // DoublyLinkedList

    //
    // Internal methods
    //
    
    /**
     * isEmpty 
     * @return returns true if the list is empty, false otherwise
     */
    boolean isEmpty() {
    	return (this.front == this.dummy);
    } // isEmpty()
    
    //
    // ITERABLE METHODS
    //
    
    /**
     * iterator creates a new DoublyLinkedListIterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator<T>(this);
    } // iterator()

    //
    // LISTOF METHODS
    //
    
    /**
     * insert takes a value and inserts it into a DoublyLinkedList at the
     * location of the cursor
     * @type mutator
     * @param val, a value of type T
     * @param c, a Cursor
     * @postCondition the DoublyLinkedList is mutated in that it now contains
     *  the inserted value with the cursor now in front of the inserted value
     */
    public void insert(T val, Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	// Special case: Empty list
    	if (this.isEmpty()) {
    		this.dummy.next = new Node<T>(val);
    		this.dummy.next.prev = this.dummy;
    		this.front = this.dummy.next;
    		this.back = this.front;
    	} else {
    		// Add the value into the array, re-linking the nodes before and 
    		// after it appropriately
    		Node<T> newNode = new Node<T>(val);
    		newNode.next = dllc.prev.next;
    		newNode.prev = dllc.prev;
    		// Reset the location of the cursor to be in front of the newNode
    		dllc.prev.next.prev = newNode;
    		dllc.prev.next = newNode;
    	} // else
    } // insert(T, Cursor)

    /**
     * append places a value at the end of a list
     * @param val, a value of type T
     * @postCondition the list now has the value val at the end
     */
    public void append(T val) throws Exception {
    	// Special case: Empty list 
        if (this.isEmpty()) {
        	this.front = new Node<T>(val);
        	this.back = this.front;
        	this.dummy.next = this.front;
        	this.front.prev = this.dummy;
        }
        // Normal case: Nonempty list
        else {
        	this.back.next = new Node<T>(val);
        	this.back.next.prev = this.back;
        	this.back = this.back.next;
        } // else
    } // append(T)

    /**
     * prepend places a value at the beginning of a list
     * @param val, a value of type T
     * @postCondition the list now has a the value val at the beginning
     */
    public void prepend(T val) throws Exception {
        // Special case: Empty list
    	if (this.isEmpty()) {
    		this.front = new Node<T>(val);
    		this.back = this.front;
    		this.dummy.next = this.front;
    		this.front.prev = this.dummy;
    	}
    	// Normal case: Nonempty list
    	else {
    		this.front.prev = new Node<T>(val);
    		this.front.prev.next = this.front;
    		this.front = this.front.prev;
    		this.dummy.next = this.front;
    		this.front.prev = this.dummy;
    	} // else
    } // prepend(T)

    /**
     * delete removes an element from the list
     * @param c, a Cursor
     * @postCondition the list now has one less element as the element that 
     * was in front of the cursor is now removed
     */
    public void delete(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	// Case at end of list
    	if (dllc.prev.next.next == null) {
    		dllc.prev.next = null;
    	}
    	// Rest of cases
    	else {
    		dllc.prev.next = dllc.prev.next.next;
    		dllc.prev.next.prev = dllc.prev;
    	} // else
    } // delete(Cursor)

    /**
     * front places a cursor at the front of a list
     * @postConditions a cursor is now in front of the first element of a list
     */
    public Cursor front() throws Exception {
        return new DoublyLinkedListCursor<T>(this.dummy);
    } // front()
    
    public Cursor back() throws Exception {
        return new DoublyLinkedListCursor<T>(this.back.prev);
    } // front()
    
    /**
     * advance moves a cursor one place forward in a list
     * @param c, a Cursor
     * @postCondition the cursor has been moved one place forward in the list
     */
    public void advance(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	if (hasNext(dllc)){
    		dllc.prev = dllc.prev.next;
    	} else if (this.isEmpty()) {
    		throw new Exception("list is empty, cannot advance");
    	} else {
    		throw new NoSuchElementException("last element in list");
    	} // else
    } // advance(Cursor)

    /**
     * retreat moves a cursor back one place in the list
     * @param c, a Cursor
     * @postCondition the cursor has been moved one place backwards in the list
     */
    public void retreat(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	if (hasPrev(dllc)){
    		dllc.prev = dllc.prev.prev;	
        } else if (this.isEmpty()) {
    		throw new Exception("list is empty, cannot advance");
    	} else { 
        	throw new NoSuchElementException("first element in list");
        } // else
    } // retreat(Cursor)

    /**
     * get returns the value of the node in front of the cursor
     * (node cursor -> node <- returns that one)
     * @param c, a Cursor
     * @return the value of the node in front of the cursor
     */
    public T get(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	// Check for empty list
    	if (this.isEmpty()) {
    		throw new Exception("Empty list, no values present");
    	} else if (hasNext(dllc)) {
    	return dllc.prev.next.val;
    	} else {
    		throw new Exception("At the end of the list, no values to get");
    	} // else
    } // get(Cursor)

    /**
     * get returns the value of the node behind the cursor
     * (returns this -> node <- cursor node)
     * @param c, a Cursor
     * @return the value of the node behind the cursor
     * @postCondition moves the cursor back one location
     */
    public T getPrev(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	// Check to see if there is a previous node
    	if (hasPrev(dllc)) {
        	T val = dllc.prev.val;
        	dllc.prev = dllc.prev.prev;
        	return val;
    	} else {
    		throw new Exception("No previous node");
    	} // else
    } // getPrev(Cursor)

    /**
     * hasNext says whether or not there is an element in front of the cursor's
     * current location
     * @param c, a Cursor
     * @return true if there is another node in the list ahead of the cursor's
     * location, false otherwise
     * @postCondition true if another node present in list past current location
     */
    public boolean hasNext(Cursor c) {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
        return (dllc.prev.next != null);
    } // hasNext(Cursor)

    /**
     * hasPrev says whether or not there is an element behind the cursor's 
     * current location
     * @param c, a Cursor
     * @return true if there is another node in the list behind the cursor's
     * location, false otherwise
     * @postCondition true if another node present in list behind current location
     */
    public boolean hasPrev(Cursor c) {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
        return (dllc.prev != this.dummy);
    } // hasPrev(Cursor)

    /**
     * swap takes the values at cursor positions c1 and c2 and switches them,
     * with c1.val -> c2.val and c2.val -> c1.val
     * @param c1, a Cursor
     * @param c2, a Cursor
     * @postcondition the list is mutated such that the value that was at 
     * c1 is now located at c2 and the value that was located at c2 is now
     * located at c1
     */
    public void swap(Cursor c1, Cursor c2) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c1;
    	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) c2;
    	T val = dllc.prev.next.val;
    	dllc.prev.next.val = dllc2.prev.next.val;
    	dllc2.prev.next.val = val; 
    } // swap(Cursor, Cursor)
    
    /**
     * search looks for the next item in the list that fulfills the predicate
     * pred.
     * @param c, a Cursor
     * @param pred, a Predicate
     * @return true if a value that satisfies the predicate is found,
     * false otherwise
     * @postCondition if true is returned, the cursor is moved to the location
     * of the item. If false is returned, the cursor is returned to its
     * original location.
     */
    public boolean search(Cursor c, Predicate<T> pred) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	DoublyLinkedListCursor<T> temp = dllc;
    	while (this.hasNext(dllc)) {
    		if (pred.test(this.get(c))) {
    			return true;
    		} else {
    			this.advance(dllc);
    		}
    	} // while
    	// If it makes it out of while loop, the search is false
    	dllc = temp;
    	return false;

    } // search(Cursor, Predicate<T>)

    /**
     * select creates a list of values that fulfill the predicate test
     * @param pred, a Predicate
     * @return a list of values
     * @postCondition a list of values that cause the predicate to be 
     * true is returned
     */
    public ListOf<T> select(Predicate<T> pred) throws Exception {
    	DoublyLinkedListIterator<T> it = new DoublyLinkedListIterator<T>(this);
    	DoublyLinkedList<T> sel = new DoublyLinkedList<T>();

    	while (it.hasNext()) {
    		T current = it.next(); 
    		if (pred.test(current)) { 
    			sel.append(current);
    		}
    	}
    	return sel;
    } // select(Predicate<T>)
    

    // one test done, pretty sure it works
    /**
     * subList creates a list of elements that is a sublist of the parent 
     * list
     * @param start, a Cursor that marks the first element wanted in sublist
     * @param end, a Cursor that marks the last element inclusive
     * @returns a DoublyLinkedList that is a sublist of the parent list
     * @postCondition the list contains the nodes that are between the node in
     * front of start and behind end. The cursor that denotes the front of the
     * sublist remains in its original position.
     * 
     */
    public ListOf<T> subList(Cursor start, Cursor end) throws Exception {
        DoublyLinkedList<T> sub = new DoublyLinkedList<T>();
        
        DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) start;
    	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) end;
    	Node<T> temp = dllc.prev;
    	while (dllc.prev != dllc2.prev.next) {
    		sub.append(this.get(dllc));
    		this.advance(dllc);
    	}
        dllc.prev = temp;
        return sub;
    } // subList(Cursor, Cursor)

    /**
     * precedes finds whether one cursor is earlier in the list than the other 
     * cursor
     * @param c1, a Cursor
     * @param c2, a Cursor
     * @return true if c1 is behind c2 in the list, false if not
     * @postCondition if c1 is behind c2, then true is returned, false otherwise
     * and both cursors remain in the same location in which they started
     */
    public boolean precedes(Cursor c1, Cursor c2) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c1;
    	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) c2;
    	Predicate<Node<T>> equalsc1 = new Equals<Node<T>>(dllc.prev.next);
    	Node<T> temp = dllc2.prev;
    	while (this.hasPrev(dllc2)) {
    		if (equalsc1.test(dllc2.prev.next)) {
    			dllc2.prev = temp;
    			return true;
    		} else {
    			this.retreat(dllc2);
    		} // else
    	} // while
    	dllc2.prev = temp;
    	return false;
    } // precedes(Cursor, Cursor)
    
} // class DoublyLinkedList

/**
 * Nodes in the list.
 */
class Node<T> {
    T val;
    Node<T> next;
    Node<T> prev;

    /**
     * Create a new node.
     */
    public Node(T val) {
        this.val = val;
        this.next = null;
        this.prev = null;
    } // Node(T)
} // Node<T>

/**
 * Cursors in the list.
 */
class DoublyLinkedListCursor<T> implements Cursor {
    Node<T> prev;

    /**
     * Create a new cursor that points to a node.
     * @preCondition pos cannot be this.back
     */
    public DoublyLinkedListCursor(Node<T> pos) { 
        this.prev = pos;
    } // DoublyLinkedListCursor
} // DoublyLinkedListCursor<T>

/**
 * Iterators in the list.
 */
class DoublyLinkedListIterator<T> implements Iterator<T> {
    Node<T> pos;

    /**
     * Create a new iterator at the front of the list.
     */
    public DoublyLinkedListIterator(DoublyLinkedList<T> list) {
        this.pos = list.front;
    } // LinkedListIterator(Node<T>)

    /**
     * Returns the next value in the list
     */
    public T next() throws NoSuchElementException {
        if (this.pos == null) {
            throw new NoSuchElementException("at end of list");
        }
        T val = this.pos.val;
        this.pos = this.pos.next;
        return val;
    } // next()

    /**
     * Says whether or not there is another node in the list
     */
    public boolean hasNext() {
        return this.pos != null;
    } // hasNext()

    /**
     * Returns the value of the item that is behind the current item
     * @return the value of the previous node
     * @throws NoSuchElementException if no previous node exists
     * @postCondition the iterator is moved back one position in the list
     */
    public T previous() throws NoSuchElementException {
    	if (this.pos.prev == null) {
    		throw new NoSuchElementException("at beginning of list");
    	}
    	T val = this.pos.prev.val;
    	this.pos.prev = this.pos.prev.prev;
    	this.pos = this.pos.prev;
    	return val;
    } // previous()
    
    /**
     * Says whether or not there is a node behind the current node in the list
     * @return true if there is another node behind the current node in the
     * list, false if not
     */
    public boolean hasPrevious() {
    	return this.pos.prev != null;
    } // hasPrevious()
    
    /**
     * Not implemented
     */
    public void remove() throws UnsupportedOperationException {
         throw new UnsupportedOperationException("No way, no how");
    } // remove()
} // LinkedListIterator<T>
