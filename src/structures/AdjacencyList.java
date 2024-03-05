package structures;

import java.util.ArrayList;
import java.util.List;
import interfaces.IGraph;
import share.Edge;
import share.Node;

public class AdjacencyList implements IGraph {
    private List<Node> nodes; 
    private boolean isWeighted;
    private boolean isDirected;

    public AdjacencyList(boolean isWeighted, boolean isDirected) {
        nodes = new ArrayList<>();
        this.isWeighted = isWeighted;
        this.isDirected = isDirected;
    }

    private Node getNode(String label) {
        for (Node node : nodes) {
            if (node.getLabel().equals(label)) {
                return node;
            }
        }
        return null;
    }

    public void addNode(String label) {
        nodes.add(new Node(label));
    }

    public void addEdge(String from, String to, int weight) {
        Node fromNode = getNode(from);
        Node toNode = getNode(to);
        int itWeight = isWeighted ? weight : 1;
        if (fromNode != null && toNode != null) {
            fromNode.addNeighbor(toNode, itWeight);
            if(!isDirected) {
                toNode.addNeighbor(fromNode, itWeight);
            }
        }
    }

    public void removeNode(String label) {
        Node node = getNode(label);
        if (node != null) {
            nodes.remove(node);
            for (Node n : nodes) {
                n.removeNeighbor(node);
            }
        }
    }

    public void removeEdge(String from, String to) {
        Node fromNode = getNode(from);
        Node toNode = getNode(to);
        if (fromNode != null && toNode != null) {
            fromNode.removeNeighbor(toNode);
        }
    }

    public boolean isNeighbor(String from, String to) {
        Node fromNode = getNode(from);
        Node toNode = getNode(to);
        if (fromNode != null && toNode != null) {
            return fromNode.isNeighbor(toNode);
        }
        return false;
    }

    public List<Edge> getNeighbors(String label) {
        Node node = getNode(label);
        if (node != null) {
            return node.getNeighbors();
        }
        return null;
    }


}
