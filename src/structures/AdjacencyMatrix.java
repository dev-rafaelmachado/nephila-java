package structures;

import interfaces.IGraph;
import java.util.ArrayList;
import java.util.List;
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
    throw new IllegalArgumentException("Node " + label + " not found");
  }

  private int hasNode(String label) {
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).getLabel().equals(label)) {
        return i;
      }
    }
    return -1;
  }

  public void addNode(String label) {
    boolean hasNode = false;
    for (Node node : nodes) {
      if (node.getLabel().equals(label)) {
        hasNode = true;
        break;
      }
    }
    if (!hasNode) {
      nodes.add(new Node(label));
      return;
    }
    System.out.println("Node" + label + " already exists");
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

  public void updateEdge(String from, String to, int weight) {
    try {
      int fromIndex = hasNode(from);
      int toIndex = hasNode(to);
      int itWeight = isWeighted ? weight : 1;

      if (fromIndex == -1) {
        addNode(from);
        fromIndex = getNodeIndex(from);
      }
      if (toIndex == -1) {
        addNode(to);
        toIndex = getNodeIndex(to);
      }
      matrix[fromIndex][toIndex] = itWeight;
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public Integer getEdgeWeight(String from, String to) {
    try {
      int fromIndex = getNodeIndex(from);
      int toIndex = getNodeIndex(to);
      return matrix[fromIndex][toIndex];
    } catch (Exception e) {
      System.out.println(e);
      return null;
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

  public void printGraph() {
    System.out.print("Nodes: ");
    for (int i = 0; i < nodes.size(); i++) {
      System.out.print(nodes.get(i).getLabel() + "(" + i + "), ");
    }

    System.out.println("\nEdges:");
    for (int i = 0; i < nodes.size(); i++) {
      System.out.print(nodes.get(i).getLabel() + ": ");
      for (int j = 0; j < nodes.size(); j++) {
        System.out.print(matrix[i][j] + " | ");
      }
      System.out.println();
    }
  }
}
