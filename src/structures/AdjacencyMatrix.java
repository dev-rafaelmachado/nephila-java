package structures;

import interfaces.IGraph;
import java.util.ArrayList;
import java.util.List;
import share.Edge;
import share.Node;

public class AdjacencyMatrix implements IGraph {

  private List<List<Integer>> matrix;
  private List<Node> nodes;
  private boolean isWeighted;
  private boolean isDirected;

  public AdjacencyMatrix(boolean isWeighted, boolean isDirected) {
    this.isWeighted = isWeighted;
    this.isDirected = isDirected;
    nodes = new ArrayList<>();
    matrix = new ArrayList<>();
  }

  protected List<Node> getNodes() {
    return nodes;
  }

  protected List<List<Integer>> getMatrix() {
    return matrix;
  }

  protected void eraseGraph() {
    nodes = new ArrayList<>();
    matrix = new ArrayList<>();
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
      List<Integer> blank = new ArrayList<Integer>();
      for (int i = 0; i < nodes.size() - 1; i++) {
        blank.add(null);
      }
      matrix.add(blank);
      for (int i = 0; i < nodes.size(); i++) {
        matrix.get(i).add(null);
      }
      return;
    }
    System.out.println("Node" + label + " already exists");
  }

  public void addEdge(String from, String to, int weight) {
    try {
      int fromIndex = getNodeIndex(from);
      int toIndex = getNodeIndex(to);
      int itWeight = isWeighted ? weight : 1;

      matrix.get(fromIndex).set(toIndex, itWeight);
      if (!isDirected) {
        matrix.get(toIndex).set(fromIndex, itWeight);
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
          matrix.get(i).remove(index);
        }
        matrix.remove(index);
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
      matrix.get(fromIndex).set(toIndex, null);
      if (!isDirected) {
        matrix.get(toIndex).set(fromIndex, null);
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
      matrix.get(fromIndex).set(toIndex, itWeight);
      if (!isDirected) {
        matrix.get(toIndex).set(fromIndex, itWeight);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public Integer getEdgeWeight(String from, String to) {
    try {
      int fromIndex = getNodeIndex(from);
      int toIndex = getNodeIndex(to);

      return matrix.get(fromIndex).get(toIndex);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public boolean isNeighbor(String from, String to) {
    try {
      int fromIndex = getNodeIndex(from);
      int toIndex = getNodeIndex(to);

      return matrix.get(fromIndex).get(toIndex) != null;
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
        if (matrix.get(index).get(i) != null) {
          edges.add(new Edge(new Node(label), matrix.get(index).get(i)));
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return edges;
  }

  public int getIndegre(String label) {
    try {
      int index = getNodeIndex(label);
      int indegre = 0;
      for (int i = 0; i < nodes.size(); i++) {
        if (matrix.get(i).get(index) != null) {
          indegre++;
        }
      }
      return indegre;
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
  }

  public int getOutdegre(String label) {
    try {
      int index = getNodeIndex(label);
      int outdegre = 0;
      for (int i = 0; i < nodes.size(); i++) {
        if (matrix.get(index).get(i) != null) {
          outdegre++;
        }
      }
      return outdegre;
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
  }

  public int getDegree(String label) {
    try {
      return getIndegre(label) + getOutdegre(label);
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
  }

  public List<List<Integer>> getWarshall() {
    List<List<Integer>> warshall = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < nodes.size(); j++) {
        row.add(matrix.get(i).get(j));
      }
      warshall.add(row);
    }

    for (int k = 0; k < nodes.size(); k++) {
      for (int i = 0; i < nodes.size(); i++) {
        for (int j = 0; j < nodes.size(); j++) {
          if (warshall.get(i).get(k) != null && warshall.get(k).get(j) != null) {
            if (warshall.get(i).get(j) == null) {
              warshall.get(i).set(j, warshall.get(i).get(k) + warshall.get(k).get(j));
            } else {
              warshall.get(i).set(j, Math.min(warshall.get(i).get(j), warshall.get(i).get(k) + warshall.get(k).get(j)));
            }
          }
        }
      }
    }
    return warshall;
  } 

  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("Nodes: ");
    for (int i = 0; i < nodes.size(); i++) {
      result
        .append(nodes.get(i).getLabel())
        .append("(")
        .append(i)
        .append("), ");
    }
    result.append("\nEdges:\n");
    for (int i = 0; i < nodes.size(); i++) {
      result.append(nodes.get(i).getLabel()).append(": ");
      for (int j = 0; j < nodes.size(); j++) {
        result.append(matrix.get(i).get(j)).append(" ");
      }
      result.append("\n");
    }

    return result.toString();
  }
}
