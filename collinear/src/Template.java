import java.util.Arrays;

public class Template {
  private final int x;
  private final int y;
  private double slopeRef;

  public Point(int x, int y) {
      this.x = x;
      this.y = y;
  }

  public void draw() {
      StdDraw.point(x, y);
  }
  public static void main(String[] args) {

  }



}
