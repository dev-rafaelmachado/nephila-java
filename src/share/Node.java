package share;

import java.util.ArrayList;
import java.util.List;
import interfaces.INode;

public class Node implements INode {
    private String label;
    private List<Edge> neighbors;
    private int distance;
    private List<Node> shortestPath;

    public Node(String label) {
        this.label = label;
        this.neighbors = new ArrayList<>();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void addNeighbor(Node node, int weight) {
        neighbors.add(new Edge(node, weight));
    }

    public void updateNeighbor(Node node, int weight) {
        for (Edge edge : neighbors) {
            if (edge.getNode().equals(node)) {
                edge.setWeight(weight);
                break;
            }
        }
    }

    public void removeNeighbor(Node node) {
        for (Edge edge : neighbors) {
            if (edge.getNode().equals(node)) {
                neighbors.remove(edge);
                break;
            }
        }
    }

    public boolean isNeighbor(Node node) {
        for (Edge edge : neighbors) {
            if (edge.getNode().equals(node)) {
                return true;
            }
        }
        return false;
    }

    public List<Edge> getNeighbors() {
        return neighbors;
    }
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public List<Node> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Node> shortestPath) {
        this.shortestPath = shortestPath;
    }
}
