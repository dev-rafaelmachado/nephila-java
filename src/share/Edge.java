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

    public void setNode(Node node) {
        this.node = node;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
