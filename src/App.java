import interfaces.IGraph;
import share.Graph;
import utils.GraphTypes;

public class App {

  public static void main(String[] args) throws Exception {
    IGraph graph = Graph.createGraph(GraphTypes.MATRIX, true, true);

    if (graph == null) {
      System.out.println("Graph not created");
      return;
    }

    graph.addNode("A");
    graph.addNode("B");

    graph.addEdge("A", "B", 1);
    graph.addEdge("B", "A", 2);
    graph.addEdge("C", "D", 3);

    System.out.println(graph.isNeighbor("A", "B"));
    System.out.println(graph.isNeighbor("B", "A"));

    System.out.println("-----------------");

    graph.printGraph();

    graph.updateEdge("A", "B", 5);

    System.out.println("-----------------");

    graph.printGraph();
  }
}
