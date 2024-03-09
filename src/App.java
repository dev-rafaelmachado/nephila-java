import interfaces.IGraph;
import share.Graph;
import structures.AdjacencyList;
import structures.AdjacencyListTest;
import structures.AdjacencyMatrix;
import structures.AdjacencyMatrixTest;
import utils.GraphTypes;

public class App {

  public static void main(String[] args) throws Exception {
    IGraph graph = Graph.createGraph(GraphTypes.LIST, true, true);
    if (graph == null) {
      System.out.println("Graph not created");
      return;
    }

    System.out.println("Adjacency List Tests:");
    AdjacencyListTest testList = new AdjacencyListTest((AdjacencyList) graph);
    testList.runAll();

    System.out.println("\n\n");

    graph = Graph.createGraph(GraphTypes.MATRIX, true, true);

    if (graph == null) {
      System.out.println("Graph not created");
      return;
    }

    System.out.println("Adjacency Matrix Tests:");
    AdjacencyMatrixTest test = new AdjacencyMatrixTest((AdjacencyMatrix) graph);
    test.runAll();
  }
}
