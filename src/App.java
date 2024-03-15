import interfaces.IGraph;
import share.Graph;
import structures.AdjacencyList;
import structures.AdjacencyListTest;
import structures.AdjacencyMatrix;
import structures.AdjacencyMatrixTest;
import utils.GraphTypes;

public class App {

  private static void testGraph(IGraph graph, String type) {
    if (type.equals("list")) {
      System.out.println("Adjacency List Tests:");
      AdjacencyListTest testList = new AdjacencyListTest((AdjacencyList) graph);
      testList.runAll();
    } else {
      System.out.println("Adjacency Matrix Tests:");
      AdjacencyMatrixTest test = new AdjacencyMatrixTest((AdjacencyMatrix) graph);
      test.runAll();
    }
  }

  public static void main(String[] args) throws Exception {
    IGraph graph = Graph.createGraph(GraphTypes.MATRIX, true, true);
    testGraph(graph, "matrix");
  
    graph.addNode("A");
    graph.addNode("B");
    graph.addNode("C");

    graph.addEdge("A", "B", 1);
    graph.addEdge("B", "C", 1);

    System.out.println(graph);

    System.out.println(graph.getWarshall());
  }
}
