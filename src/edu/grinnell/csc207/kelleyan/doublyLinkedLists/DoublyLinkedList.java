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
    	// mutations needed?
    	
    } // DoublyLinkedList

    // Internal methods
    boolean isEmpty() {
    	return (this.front == this.dummy);
    } // isEmpty()
    
    // ITERABLE METHODS
    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedListIterator<T>(this);
    } // iterator()

    // LISTOF METHODS
    public void insert(T val, Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	// Special case: Empty list /// how do we check if the cursor is valid??????
    	if (this.isEmpty()){
    		this.dummy.next = new Node<T>(val);
    		this.dummy.next.prev = this.dummy;
    		this.front = this.dummy.next;
    		this.back = this.front;
    	}else{
    		Node<T> temp = dllc.pos;
    		Node<T> newNode = new Node<T>(val);
    		newNode.next = dllc.pos;
    		newNode.prev = dllc.pos.prev;

    		
    		dllc.pos.prev.next = newNode;
    		dllc.pos.prev = newNode;
    	} //// When checking these look forwards and backwards
    } // insert(T, Cursor)

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
    	// Normal case: Nonempty list
    	else {
    		this.front.prev = new Node<T>(val);
    		this.front.prev.next = this.front;
    		this.front = this.front.prev;
    		this.dummy.next = this.front;
    		this.front.prev = this.dummy;
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
        return new DoublyLinkedListCursor<T>(this.front);
    } // front()
// maybe we need to check for the next element's existence??????
    
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
    } // get(Cursor)

    public T getPrev(Cursor c) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	dllc.pos = dllc.pos.prev;
    	return dllc.pos.val;
    } // getPrev(Cursor)

    public boolean hasNext(Cursor c) {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
        return (dllc.pos.next != null);
    } // hasNext(Cursor)

    public boolean hasPrev(Cursor c) {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
        return (dllc.pos.prev != this.dummy);
    } // hasPrev(Cursor)

    public void swap(Cursor c1, Cursor c2) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c1;
    	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) c2;
    	T val = dllc.pos.val;
    	dllc.pos.val = dllc2.pos.val;
    	dllc2.pos.val = val; 
    } // swap(Cursor, Cursor)
    
    public boolean search(Cursor c, Predicate<T> pred) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c;
    	do {
    		if (pred.test(this.get(c))) {
    			return true;
    		} else {
    			this.advance(dllc);
    		}
    	} while (this.hasNext(dllc)); // do while
    	if (pred.test(this.get(c))) {
    		return true;
    	} else {
    	return false;
    	}
    } // search(Cursor, Predicate<T>)
    
    public ListOf<T> select(Predicate<T> pred) throws Exception {
    	DoublyLinkedListIterator<T> it = new DoublyLinkedListIterator<T>(this);
    	DoublyLinkedList<T> sel = new DoublyLinkedList<T>();

    	while (it.hasNext()) {
    		T test = it.next(); 
    		if (pred.test(test)) { 
    			sel.append(test);
    		}
    	}
    	return sel;
    } // select(Predicate<T>)
     
    // one test done, pretty sure it works
    public ListOf<T> subList(Cursor start, Cursor end) throws Exception {
        DoublyLinkedList<T> sub = new DoublyLinkedList<T>();
        
        DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) start;
    	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) end;
    	
        sub.front = dllc.pos;
        sub.back = dllc2.pos;
        
        sub.front.prev = sub.dummy;
        sub.dummy.next = sub.front;
        
        sub.back.next = null;
        
        return sub;
    } // subList(Cursor, Cursor)

    public boolean precedes(Cursor c1, Cursor c2) throws Exception {
    	DoublyLinkedListCursor<T> dllc = (DoublyLinkedListCursor<T>) c1;
    	DoublyLinkedListCursor<T> dllc2 = (DoublyLinkedListCursor<T>) c2;
    	Predicate<T> equalsc1 = new Equals<T>(dllc.pos.val);
    	
    	while (!equalsc1.test(dllc2.pos.val)) {
    		if (dllc2.pos == this.dummy) {
    			return false;
    		} else {
    			this.retreat(dllc2);
    		} // else
    	} // while
    	return true;
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
