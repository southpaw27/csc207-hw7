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
         DoublyLinkedListIterator<Integer> ni = new DoublyLinkedListIterator<Integer>(numbers);
         numbers.printList(ni);
         DoublyLinkedListCursor<Integer> nCurs= new DoublyLinkedListCursor<Integer>(ni.next());
         printList(pen, numbers);

         // Append a few elements
         numbers.append(1);
         numbers.append(2);
         numbers.append(3);
         printList(pen, numbers);
         // And we're done
         pen.close();
     } // main(String[])
} // class Experiment
