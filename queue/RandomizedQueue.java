// exchange node [N-1] with a random node in deque, so that all nodes will still be in
// order, and all null elements will be at the end.

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;

public class RandomizedQueue<Item> implements Iterable<Item> {
   private Item[] q; //array of items
   private int N; //size of the queue

   public RandomizedQueue() {                 // construct an empty randomized queue
     q = (Item[]) new Object[2];
     N = 0;
   }

   public boolean isEmpty() {                 // is the queue empty?
     return N == 0;
   }

   public int size() {                        // return the number of items on the queue
     return N;
   }

   public void enqueue(Item item) {           // add the item
     if (item == null) throw new NullPointerException();
     if (N == q.length) resize(2*q.length);
     q[N] = item;
     N++;
   }

   public Item dequeue() {                    // remove and return a random item
     if (isEmpty()) throw new NoSuchElementException("Queue underflow");
     int randPos = StdRandom.uniform(N);
     Item temp = q[randPos];
     q[randPos] = q[N-1];
     q[N-1] = null;
     N--;
     if (N > 0 && N == q.length/4) resize(q.length/2);
     return temp;
   }
   public Item sample() {                     // return (but do not remove) a random item
     // generate a random number and use it as the index
     if (isEmpty()) throw new NoSuchElementException("Queue underflow");
     int randPos = StdRandom.uniform(N);
     return q[randPos];
   }

   private void resize(int capacity) {
     assert capacity >= N;
     Item[] temp = (Item[]) new Object[capacity];
     int pos = 0;
     for (int i = 0; i < N; i++) {
       temp[i] = q[i];
     }
     q = temp;
    }

    public Iterator<Item> iterator() {
      return new QueueIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class QueueIterator implements Iterator<Item> {
      // lesson: be careful of shallow copy
      private int i;
      private int[] index;
      private Item temp;

      public QueueIterator() {
        index = new int[N];
        // need to create a new array for each iterator
        // because array is not immutable in Java
        for (int j = 0; j < N; j++) {
          index[j] = j;
        }
        StdRandom.shuffle(index);
      }

      public boolean hasNext() {
        return i < N;
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }

      public Item next() {
        if (!hasNext()) throw new NoSuchElementException();
        temp = q[index[i]];
        i++;
        return temp;
      }

    }

   // printOut() method is used for debugging purpose only
   /*
   public void printOut() {
     for (int i = 0; i < q.length; i++) {
       System.out.println(q[i]);
     }
   }
   */

   public static void main(String[] args) {   // unit testing
     RandomizedQueue<String> test = new RandomizedQueue<String>();

     test.enqueue("a");
     test.enqueue("b");
     test.enqueue("c");
     System.out.println("Item is: " + test.dequeue());
     test.enqueue("d");
     System.out.println("Item is: " + test.dequeue());
     System.out.println("Item is: " + test.dequeue());
     System.out.println("Item is: " + test.dequeue());
     test.enqueue("e");
     /*
     test.enqueue("f");
     test.enqueue("g");
     test.enqueue("h");
     test.enqueue("i");
     test.enqueue("j");
     */
     /*
     System.out.println("Item is: " + test.sample());
     //test.printOut();
     System.out.println("Item is: " + test.sample());
     //test.printOut();
     System.out.println("Item is: " + test.sample());

     //test.printOut();

     //test.enqueue("d");
     //test.enqueue("e");
     System.out.println("Item is: " + test.dequeue());
     System.out.println("Item is: " + test.dequeue());
     //System.out.println("Item is: " + test.dequeue());
     //test.enqueue("f");

     */

     /*
     for (int i = 0; i < 10; i++) {
       System.out.println("Item is: " + test.dequeue());
       //System.out.println("Item is: " + test.sample());
     }
     */


     //for (String s : test)
     //   System.out.println(s);

     /*
     System.out.println("Pause.");
     test.enqueue("g");
     test.enqueue("h");
     test.enqueue("i");
     test.enqueue("j");

     for (String s : test)
       System.out.println(s);
     */
   }
}
