package share;

public class Edge {

  private Node node;
  private int weight;

  public Edge(Node node, int weight) {
    this.node = node;
    this.weight = weight;
  }

  public Node getNode() {
    return node;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    return node.getLabel() + " (" + weight + ")";
  }
}
