//idea for timing: a node named Last ==> problem solved!

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.NullPointerException;

public class Deque<Item> implements Iterable<Item> {
  private int N; // size of the Deque
  private Node first; // First node of the linked list
  private Node last;

  private class Node {
    private Item item;
    private Node before;
    private Node next;
  }

  public Deque() {                           // construct an empty deque
    first = null;
    last = null;
    N = 0;
  }

  public boolean isEmpty() {                 // is the deque empty?
    //return first == null;
    return N == 0;
  }

  public int size() {                        // return the number of items on the deque
    return N;
  }

  public void addFirst(Item item) {          // add the item to the front
    if (item == null) throw new NullPointerException();

    Node oldfirst = first;
    first = new Node();
    first.before = null;
    first.item = item;
    first.next = oldfirst;
    if (oldfirst != null) {
      oldfirst.before = first;
    }
    if (isEmpty()) last = first;
    N++;
  }

  //addLast algorithm seems bad
  public void addLast(Item item) {           // add the item to the end
    // check if the deque is empty, if yes, use addFirst
    if (item == null) throw new NullPointerException();
    // if the deque is empty, use addFirst to avoid confusion
    if (this.isEmpty()) {
      this.addFirst(item);
      return;
    }
    Node n = new Node();
    n.item = item;
    n.next = null;
    n.before = last;
    last.next = n;
    last = n; // turn n into last
    N++;
  }

  public Item removeFirst() {                // remove and return the item from the front
    //check if the Deque is empty
    if (isEmpty()) throw new NoSuchElementException("Deque underflow");
    Item item = first.item;
    Node oldfirst = first;
    first = first.next;
    oldfirst.next = null;
    oldfirst.item = null;
    oldfirst = null;
    if (this.size() != 1) {
      first.before = null;
    }
    N--;
    return item;
  }

  // removeLast seems bad
  public Item removeLast() {                 // remove and return the item from the end
    //check if the deque is empty
    if (isEmpty()) throw new NoSuchElementException("Deque underflow");
    if (this.size() == 1) {
      last = null;
      return removeFirst();
    }
    Item item = last.item;
    Node oldlast = last;
    last = last.before;
    last.next = null;
    oldlast.before = null;
    oldlast.item = null;
    oldlast = null;
    N--;
    return item;
  }

  public Iterator<Item> iterator()  { return new ListIterator();  }

  // an iterator, doesn't implement remove() since it's optional
  private class ListIterator implements Iterator<Item> {
    private Node current = first;
    public boolean hasNext()  { return current != null;                     }
    public void remove()      { throw new UnsupportedOperationException();  }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  public static void main(String[] args) {   // unit testing
    Deque<String> test = new Deque<String>();

    test.addLast("1");
    test.addLast("2");
    System.out.println(test.removeLast());
    System.out.println(test.removeLast());
    test.addFirst("3");
    test.addFirst("4");
    System.out.println(test.removeFirst());
    System.out.println(test.removeFirst());
    test.addLast("5");
    System.out.println(test.removeLast());
    test.addFirst("6");
    System.out.println(test.removeFirst());

    /*
    for (int i = 0; i < 5; i++) {
      System.out.println(test.removeLast());
    }
    */
    /*
    for (String s : test)
       System.out.println(s);
    */

    /*
    for (int i = 0; i < 5; i++) {
      test.addFirst("champions");
      System.out.println(test.removeFirst());
    }
    */
  }
}
