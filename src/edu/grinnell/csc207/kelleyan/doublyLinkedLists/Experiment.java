package edu.grinnell.csc207.kelleyan.doublyLinkedLists;

import java.io.PrintWriter;

/**
 * A simple set of experiments to make sure that our amazing LinkedList
 * class works fine.
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
     } // printList(PrintWriter, LinkedList<Object>)

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
         
         //printList(pen, numbers);

         // Append a few elements
         numbers.append(1);
         numbers.append(2);
         numbers.append(3);
         //printList(pen, numbers);
         
         DoublyLinkedListIterator<Integer> ni = new DoublyLinkedListIterator<Integer>(numbers);
         
         ni.next();
         DoublyLinkedListCursor<Integer> nCurs1= new DoublyLinkedListCursor<Integer>(ni.pos);
         pen.println(numbers.get(nCurs1).toString());
         
         printList(pen, numbers);
         numbers.insert(new Integer(99), nCurs1);
         printList(pen, numbers);
         ni.next();
         
         DoublyLinkedListCursor<Integer> nCurs2= new DoublyLinkedListCursor<Integer>(ni.pos);
         
         printList(pen, (DoublyLinkedList<Integer>) numbers.subList(nCurs1, nCurs2));
//         numbers.advance(nCurs1);
//         pen.println(numbers.get(nCurs1));
//         pen.println(numbers.get(nCurs2).toString());
//         numbers.delete(nCurs2);
 //        numbers.swap(nCurs1, nCurs2);
//         printList(pen, numbers);
         // And we're done
         pen.close();
     } // main(String[])
} // class Experiment
