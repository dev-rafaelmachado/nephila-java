package share;

public class ExternalEdge {

  private Node fromNode;
  private Node toNode;
  private int weight;

  public ExternalEdge(Node fromNode, Node toNode, int weight) {
    this.fromNode = fromNode;
    this.toNode = toNode;
    this.weight = weight;
  }

  public Node getFromNode() {
    return fromNode;
  }

  public Node getToNode() {
    return toNode;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  @Override
  public String toString() {
    return (
      fromNode.getLabel() + " -> " + toNode.getLabel() + " (" + weight + ")"
    );
  }
}
