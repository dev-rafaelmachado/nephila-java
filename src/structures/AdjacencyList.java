package structures;

import interfaces.IGraph;
import java.util.ArrayList;
import java.util.List;
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

  protected List<Node> getListNodes() {
    return nodes;
  }

  protected void eraseNodes() {
    nodes = new ArrayList<>();
  }

  private Node getNode(String label) {
    for (Node node : nodes) {
      if (node.getLabel().equals(label)) {
        return node;
      }
    }
    throw new IllegalArgumentException("Node " + label + " not found");
  }

  private Node hasNode(String label) {
    for (Node node : nodes) {
      if (node.getLabel().equals(label)) {
        return node;
      }
    }
    return null;
  }

  public void addNode(String label) {
    boolean exists = hasNode(label) != null;
    if (!exists) {
      nodes.add(new Node(label));
      return;
    }
    System.out.println("Node " + label + " already exists");
  }

  public void addEdge(String from, String to, int weight) {
    try {
      Node fromNode = getNode(from);
      Node toNode = getNode(to);
      int itWeight = isWeighted ? weight : 1;
      if (fromNode != null && toNode != null) {
        fromNode.addNeighbor(toNode, itWeight);
        if (!isDirected) {
          toNode.addNeighbor(fromNode, itWeight);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void removeNode(String label) {
    try {
      Node node = getNode(label);
      if (node != null) {
        nodes.remove(node);
        for (Node n : nodes) {
          n.removeNeighbor(node);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void removeEdge(String from, String to) {
    try {
      Node fromNode = getNode(from);
      Node toNode = getNode(to);
      if (fromNode != null && toNode != null) {
        fromNode.removeNeighbor(toNode);
        if (!isDirected) {
          toNode.removeNeighbor(fromNode);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void updateEdge(String from, String to, int weight) {
    try {
      Node fromNode = hasNode(from);
      Node toNode = hasNode(to);
      int itWeight = isWeighted ? weight : 1;
      if (fromNode == null) {
        addNode(from);
        fromNode = getNode(from);
      }
      if (toNode == null) {
        addNode(to);
        toNode = getNode(to);
      }
      fromNode.updateNeighbor(toNode, itWeight);
      if (!isDirected) {
        toNode.updateNeighbor(fromNode, itWeight);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public Integer getEdgeWeight(String from, String to) {
    try {
      Node fromNode = getNode(from);
      Node toNode = getNode(to);
      if (fromNode != null && toNode != null) {
        for (Edge edge : fromNode.getNeighbors()) {
          if (edge.getNode().equals(toNode)) {
            return edge.getWeight();
          }
        }
      }
      return null;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public boolean isNeighbor(String from, String to) {
    try {
      Node fromNode = getNode(from);
      Node toNode = getNode(to);
      if (fromNode != null && toNode != null) {
        return fromNode.isNeighbor(toNode);
      }
      return false;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public List<Edge> getNeighbors(String label) {
    try {
      Node node = getNode(label);
      if (node != null) {
        return node.getNeighbors();
      }
      return null;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public int getIndegre(String label) {
    try {
      Node node = getNode(label);
      int indegre = 0;
      for (Node n : nodes) {
        for (Edge edge : n.getNeighbors()) {
          if (edge.getNode().equals(node)) {
            indegre++;
          }
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
      Node node = getNode(label);
      return node.getNeighbors().size();
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

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node node : nodes) {
      sb.append(node.getLabel()).append(": ");
      for (Edge edge : node.getNeighbors()) {
        sb.append(edge.getNode().getLabel());
        if (isWeighted) {
          sb.append("(").append(edge.getWeight()).append(")");
        }
        sb.append(", ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
