package implementation.share;

public class Pair<T, J> {

  private T first;
  private J second;

  public Pair(T first, J second) {
    this.first = first;
    this.second = second;
  }

  public T getFirst() {
    return first;
  }

  public J getSecond() {
    return second;
  }

  public void setFirst(T first) {
    this.first = first;
  }

  public void setSecond(J second) {
    this.second = second;
  }

  public String toString() {
    return "(" + first + ", " + second + ")";
  }
}
