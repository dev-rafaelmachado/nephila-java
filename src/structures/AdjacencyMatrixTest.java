package structures;

import java.util.List;
import share.Node;

public class AdjacencyMatrixTest {

  private AdjacencyMatrix graph;

  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_RESET = "\u001B[0m";

  public AdjacencyMatrixTest(AdjacencyMatrix graph) {
    this.graph = graph;
  }

  public void runAll() {
    System.out.println(
      "it Should Add Node: " +
      (
        itShouldAddNode()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Remove Node: " +
      (
        itShouldRemoveNode()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Add Edge: " +
      (
        itShouldAddEdge()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Remove Edge: " +
      (
        itShouldRemoveEdge()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Update Edge: " +
      (
        itShouldUpdateEdge()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Get Edge Weight: " +
      (
        itShouldGetEdgeWeight()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Return True If Nodes Are Neighbors: " +
      (
        itShouldReturnTrueIfNodesAreNeighbors()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Return Neighbors: " +
      (
        itShouldReturnNeighbors()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Return Indegree: " +
      (
        itShouldReturnIndegre()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Return Outdegree: " +
      (
        itShouldReturnOutdegre()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );

    System.out.println(
      "it Should Return Degree: " +
      (
        itShouldReturnDegree()
          ? ANSI_GREEN + "PASSED" + ANSI_RESET
          : ANSI_RED + "FAILED" + ANSI_RESET
      )
    );
  }

  public boolean itShouldAddNode() {
    graph.addNode("A");
    List<Node> nodes = graph.getNodes();
    graph.eraseGraph();
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
    List<Node> nodes = graph.getNodes();
    graph.eraseGraph();
    for (Node node : nodes) {
      if (node.getLabel().equals("A")) {
        return false;
      }
    }
    return true;
  }

  public boolean itShouldAddEdge() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    List<List<Integer>> matrix = graph.getMatrix();
    graph.eraseGraph();
    return matrix.get(0).get(1) == 1;
  }

  public boolean itShouldRemoveEdge() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    graph.removeEdge("A", "B");
    List<List<Integer>> matrix = graph.getMatrix();
    graph.eraseGraph();
    return matrix.get(0).get(1) == null;
  }

  public boolean itShouldUpdateEdge() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    graph.addEdge("A", "B", 2);
    List<List<Integer>> matrix = graph.getMatrix();
    graph.eraseGraph();
    return matrix.get(0).get(1) == 2;
  }

  public boolean itShouldGetEdgeWeight() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    int weight = graph.getEdgeWeight("A", "B");
    graph.eraseGraph();
    return weight == 1;
  }

  public boolean itShouldReturnTrueIfNodesAreNeighbors() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addEdge("A", "B", 1);
    boolean isNeighbor = graph.isNeighbor("A", "B");
    graph.eraseGraph();
    return isNeighbor;
  }

  public boolean itShouldReturnNeighbors() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("A", "B", 1);
    graph.addEdge("A", "C", 1);
    List<share.Edge> edges = graph.getNeighbors("A");
    graph.eraseGraph();
    return edges.size() == 2;
  }

  public boolean itShouldReturnIndegre() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("B", "A", 1);
    graph.addEdge("C", "A", 1);
    int indegre = graph.getIndegre("A");
    graph.eraseGraph();
    return indegre == 2;
  }

  public boolean itShouldReturnOutdegre() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("A", "B", 1);
    graph.addEdge("A", "C", 1);
    int outdegre = graph.getOutdegre("A");
    graph.eraseGraph();
    return outdegre == 2;
  }

  public boolean itShouldReturnDegree() {
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");
    graph.addEdge("A", "B", 1);
    graph.addEdge("A", "C", 1);
    int degree = graph.getDegree("A");
    graph.eraseGraph();
    return degree == 2;
  }
}
