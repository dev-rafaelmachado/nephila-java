import interfaces.IGraph;
import share.Graph;

public class App {

  public static void main(String[] args) throws Exception {
    IGraph graph = Graph.loadGraph("/src/assets/graph.net");
    System.out.println(graph);

    // IGraph graph = Graph.createGraph(GraphTypes.LIST, false, false);
    // graph.addNode("A");
    // graph.addNode("B");
    // graph.addNode("C");

    // graph.addEdge("A", "B", 1);
    // graph.addEdge("B", "C", 1);
    // graph.addEdge("C", "A", 1);

    // graph.saveGraph(currentPath + "/src/assets/save.net");
  }
}
