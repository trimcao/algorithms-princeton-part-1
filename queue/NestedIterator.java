public class NestedIterator {
  public static void main (String[] args) {
    RandomizedQueue<String> d = new RandomizedQueue();
    /*
    d.enqueue("a");
    d.enqueue("b");
    d.enqueue("c");
    d.enqueue("d");
    d.enqueue("e");
    */
    /*
    d.enqueue("f");
    d.enqueue("g");
    d.enqueue("h");
    d.enqueue("i");
    d.enqueue("j");
    */
    for (String x : d) {
      StdOut.println("> " + x);
      for (String s : d)
        StdOut.print(s + ", ");
      StdOut.println("-------");
    }
  }
}
