package structures;

import java.util.List;
import share.Edge;
import share.Node;

public class AdjacencyListTest {

  private AdjacencyList graph;

  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RESET = "\u001B[0m";

  public AdjacencyListTest(AdjacencyList graph) {
    this.graph = graph;
  }

  public void runAll() {
    System.out.println(
      "itShouldAddNode: " +
      (
        itShouldAddNode()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldRemoveNode: " +
      (
        itShouldRemoveNode()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldAddEdge: " +
      (
        itShouldAddEdge()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldRemoveEdge: " +
      (
        itShouldRemoveEdge()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldUpdateEdge: " +
      (
        itShouldUpdateEdge()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldIsNeighbor: " +
      (
        itShouldIsNeighbor()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldGetNeighbors: " +
      (
        itShouldGetNeighbors()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldGetIndegre: " +
      (
        itShouldGetIndegre()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldGetOutdegre: " +
      (
        itShouldGetOutdegre()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldGetDegree: " +
      (
        itShouldGetDegree()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "itShouldGetWeight: " +
      (
        itShouldGetWeight()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );
  }

  public boolean itShouldAddNode() {
    graph.addNode("A");
    List<Node> nodes = graph.getListNodes();
    graph.eraseNodes();

    for (Node node : nodes) {
      if (node.getLabel().equals("A")) {
        return true;
      }
    }

    return false;
  }

  public boolean itShouldRemoveNode() {
    graph.addNode("A");
    graph.removeNode("A");
    List<Node> nodes = graph.getListNodes();
    graph.eraseNodes();

    return nodes.size() == 0;
  }

  public boolean itShouldAddEdge() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    List<Node> nodes = graph.getListNodes();
    graph.eraseNodes();

    for (Node node : nodes) {
      if (node.getLabel().equals("A")) {
        for (Edge edge : node.getNeighbors()) {
          if (edge.getNode().getLabel().equals("B")) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public boolean itShouldRemoveEdge() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    graph.removeEdge("A", "B");
    List<Node> nodes = graph.getListNodes();
    graph.eraseNodes();

    for (Node node : nodes) {
      if (node.getLabel().equals("A")) {
        return node.getNeighbors().size() == 0;
      }
    }

    return false;
  }

  public boolean itShouldUpdateEdge() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    graph.updateEdge("A", "B", 5);
    List<Node> nodes = graph.getListNodes();
    graph.eraseNodes();

    for (Node node : nodes) {
      if (node.getLabel().equals("A")) {
        for (Edge edge : node.getNeighbors()) {
          if (edge.getNode().getLabel().equals("B") && edge.getWeight() == 5) {
            return true;
          }
        }
      }
    }

    return false;
  }

  public boolean itShouldIsNeighbor() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    graph.addEdge("B", "A", 2);
    boolean isNeighbor = graph.isNeighbor("A", "B");
    graph.eraseNodes();
    return isNeighbor;
  }

  public boolean itShouldGetNeighbors() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("A", "B", 1);
    graph.addEdge("A", "C", 2);
    List<Edge> neighbors = graph.getNeighbors("A");
    graph.eraseNodes();

    for (Edge edge : neighbors) {
      if (edge.getNode().getLabel().equals("B") && edge.getWeight() == 1) {
        return true;
      }
      if (edge.getNode().getLabel().equals("C") && edge.getWeight() == 2) {
        return true;
      }
    }

    return false;
  }

  public boolean itShouldGetIndegre() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("A", "B", 1);
    graph.addEdge("A", "C", 2);
    int indegre = graph.getIndegre("A");
    graph.eraseNodes();
    return indegre == 0;
  }

  public boolean itShouldGetOutdegre() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("A", "B", 1);
    graph.addEdge("A", "C", 2);
    int outdegre = graph.getOutdegre("A");
    graph.eraseNodes();
    return outdegre == 2;
  }

  public boolean itShouldGetDegree() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("A", "B", 1);
    graph.addEdge("A", "C", 2);
    int degree = graph.getDegree("A");
    graph.eraseNodes();
    return degree == 2;
  }

  public boolean itShouldGetWeight() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    int weight = graph.getEdgeWeight("A", "B");
    graph.eraseNodes();
    return weight == 1;
  }
}
