package structures;

import java.util.List;
import java.util.ArrayList;
import interfaces.IGraph;
import share.Edge;
import share.Node;

public class AdjacencyMatrix implements IGraph {
    private int[][] matrix;
    private List<Node> nodes;

    private boolean isWeighted;
    private boolean isDirected;

    public AdjacencyMatrix(boolean isWeighted, boolean isDirected) {
        this.isWeighted = isWeighted;
        this.isDirected = isDirected;
        nodes = new ArrayList<>();
        matrix = new int[nodes.size()][nodes.size()];
    }

    private int getNodeIndex(String label) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getLabel().equals(label)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Node not found");
    }

    public void addNode(String label) {
        nodes.add(new Node(label));
    }

    public void addEdge(String from, String to, int weight) {
        try {
            int fromIndex = getNodeIndex(from);
            int toIndex = getNodeIndex(to);
            int itWeight = isWeighted ? weight : 1;
            matrix[fromIndex][toIndex] = itWeight;
            if (!isDirected) {
                matrix[toIndex][fromIndex] = weight;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeNode(String label) {
        try {
            int index = getNodeIndex(label);
            if (index != -1) {
                for (int i = 0; i < nodes.size(); i++) {
                    matrix[i][index] = 0;
                    matrix[index][i] = 0;
                }
                nodes.remove(index);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void removeEdge(String from, String to) {
        try {
            int fromIndex = getNodeIndex(from);
            int toIndex = getNodeIndex(to);
            matrix[fromIndex][toIndex] = 0;
            if (!isDirected) {
                matrix[toIndex][fromIndex] = 0;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean isNeighbor(String from, String to) {
        try {
            int fromIndex = getNodeIndex(from);
            int toIndex = getNodeIndex(to);
            return matrix[fromIndex][toIndex] != 0;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Edge> getNeighbors(String label) {
        List<Edge> edges = new ArrayList<>();
        try {
            int index = getNodeIndex(label);
            for (int i = 0; i < nodes.size(); i++) {
                if (matrix[index][i] != 0) {
                    edges.add(new Edge(nodes.get(i), matrix[index][i]));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return edges;
    }

}
