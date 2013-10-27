package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Iterator;

/***
 * Mira! So I implemented most of the things we needed to do
 * for this class, but got confused when I was sleepy. I got 
 * down to swapping when I got confused. I know most of it 
 * works so far but not sure about all of them. The experiment
 * section has some basic tests that we did in class. Let me
 * know if you have any questions. I'm most likely going to
 * take the day off from this assignment tomorrow and work on
 * all my other stuff. Just hit me up if you need anything!
 ***/

/**
 * Doubly linked lists.
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
    	//mutations needed?
    	
    } // DoublyLinkedList

    //Internal methods
    boolean isEmpty() {
    	return (this.front == this.dummy);
    } // isEmpty
    
    // ITERABLE METHODS
    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator<T>(this);
    } // iterator()

    // LISTOF METHODS
    public void insert(T val, Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	//Special case: Empty list ///how do we check if the cursor is valid??????
    	if (this.isEmpty()){
    		this.dummy.next = new Node<T>(val);
    		this.dummy.next.prev = this.dummy;
    		this.front = this.dummy.next;
    		this.back = this.front;
    	}else{
    		dllc.pos.prev.next = new Node<T>(val);
    		dllc.pos.prev = dllc.pos.prev.next;
    		dllc.pos.prev.next.next = dllc.pos;
    		dllc.pos.prev.next.prev = dllc.pos.prev;
    	}
    } // insert(T, Cursor)

    public void append(T val) throws Exception {
    	// Special case: Empty list //aren't we supposed to leave the dummy node as front? or not? i
        if (this.isEmpty()) {
        	this.front = new Node<T>(val);
        	this.back = this.front;
        	this.dummy.next = this.front;
        	this.front.prev = this.dummy;
        }
        // Normal case: Nonempty list
        else {
        	this.back.next = new Node<T>(val);
        	this.back.next.prev = this.back.next;
        	this.back = this.back.next;
        }
    } // append(T)

    public void prepend(T val) throws Exception {
        // Special case: Empty list
    	if (this.isEmpty()) {
    		this.front = new Node<T>(val);
    		this.back = this.front;
    		this.dummy.next = this.front;
    		this.front.prev = this.dummy;
    	}
    	//Normal case: Nonempty list
    	else {
    		this.front.prev = new Node<T>(val);
    		this.front.prev.next = this.front;
    		this.front = this.front.prev;
    		this.dummy.next = this.front;
    	}
    } // prepend(T)

    public void delete(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	// Case at end of list
    	if (dllc.pos.next == null) {
    		dllc.pos.prev.next = null;
    	}
    	// Rest of cases
    	else {
    		dllc.pos.prev.next = dllc.pos.next;
    		dllc.pos.next.prev = dllc.pos.prev; //need to set the backwards way working too!
    	}
    } // delete(Cursor)

    public Cursor front() throws Exception {
        return new DoublyLinkedListCursor<T>(this.dummy);
    } // front()
//maybe we need to check for the next element's existence??????
    public void advance(Cursor c) throws Exception {
    	
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	if (hasNext(dllc)){
    		dllc.pos = dllc.pos.next;
    	} else {
    		throw new NoSuchElementException("last element in list");
    	}
    } // advance(Cursor)
//
    public void retreat(Cursor c) throws Exception {
    	
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	if (hasPrev(dllc)){
    		dllc.pos = dllc.pos.prev;
        }else{ 
        	throw new NoSuchElementException("first element in list");
        }
    } // retreat(Cursor)

    public T get(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	return dllc.pos.val;
    } // get

    public T getPrev(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	dllc.pos = dllc.pos.prev;
    	return dllc.pos.val;
    } // getPrev(Cursor)

    public boolean hasNext(Cursor c) {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
        return (dllc.pos.next.val != null);
    } // hasNext

    public boolean hasPrev(Cursor c) {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
        return (dllc.pos.next.val != null);
    } // hasPrev

    //
    //THIS IS NOT COMPLETE! I got confused because it's 1:30 and I need
    //to get off the computer...are we just switching the cursors to a new
    //position, or are we switching the nodes the cursors are pointing to?
    //M: we should be switching the next and prev fields in the nodes
    // and yes, it is ugly
    //
    public void swap(Cursor c1, Cursor c2) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c1;
    	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) c2;
    	switchBothPointers(dllc.pos, dllc2.pos, "prev");
    	switchBothPointers(dllc.pos, dllc2.pos, "next");
    	   	
    } // swap(Cursor, Cursor)
    private void switchBothPointers(Node<T> node1, Node<T> node2, String prevOrNext){
    	Node<T> temp = node1;
    	if (prevOrNext.equals("prev")){
    		node1.prev = node2.prev;
    		node2.prev = temp.prev;
    		node1.prev.next = node2.prev.next;
    		node2.prev.next = temp.prev.next;
    	}else {
    		node1.next = node2.next;
    		node2.next = temp.next;
    		node1.next.prev = node2.next.prev;
    		node2.next.prev = temp.next.prev;
    	}
    }
    public boolean search(Cursor c, Predicate<T> pred) throws Exception {
        throw new UnsupportedOperationException("STUB");
    } // search(Cursor, Predicate<T>)
     
    public ListOf<T> select(Predicate<T> pred) throws Exception {
        throw new UnsupportedOperationException("STUB");
    } // select(Predicate<T>)
     
    public ListOf<T> subList(Cursor start, Cursor end) throws Exception {
        throw new UnsupportedOperationException("STUB");
    } // subList(Cursor, Cursor)

    public boolean precedes(Cursor c1, Cursor c2) throws Exception {
        throw new UnsupportedOperationException("STUB");
    } // precedes(Cursor, Cursor)
    
    public void printList(PrintWriter pen){
    	DoublyLinkedListIterator<T> it = new DoublyLinkedListIterator<T>(this);
    	while (it.hasNext()){
    		pen.println(it.next());
    	}
    }
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
    Node<T> pos;

    /**
     * Create a new cursor that points to a node.
     */
    public DoublyLinkedListCursor(Node<T> pos) {
        this.pos = pos;
    } // DoublyLinkedListCursor
} // DoublyLinkedListCursor<T>

/**
 * Iterators in the list.
 */
class DoublyLinkedListIterator<T> implements Iterator<T> {
    Node<T> pos;
    Node<T> prev;
    DoublyLinkedList<T> list;

    /**
     * Create a new iterator at the front of the list.
     */
    public DoublyLinkedListIterator(DoublyLinkedList<T> list) {
        this.pos = list.front;
        this.prev = null;
        this.list = list;
    } // LinkedListIterator(Node<T>)

    public T next() throws NoSuchElementException {
        if (this.pos == null) {
            throw new NoSuchElementException("at end of list");
        }
        T val = this.pos.val;
        this.pos = this.pos.next;
        return val;
    } // next()

    public boolean hasNext() {
        return this.pos != null;
    } // hasNext()

    public T previous() throws NoSuchElementException {
    	if (this.prev == null) {
    		throw new NoSuchElementException("at beginning of list");
    	}
    	T val = this.prev.val;
    	this.prev = this.prev.prev;
    	this.pos = this.prev;
    	return val;
    } // previous()
    
    public boolean hasPrevious() {
    	return this.prev != null;
    } // hasPrevious()
    
    public void remove() throws UnsupportedOperationException {
         throw new UnsupportedOperationException("No way, no how");
    } // remove()
} // LinkedListIterator<T>
