import interfaces.IGraph;
import share.Graph;

public class App {
    public static void main(String[] args) throws Exception {
        IGraph graph = Graph.createGraph("list", true, true);

        graph.addNode("A");
        graph.addNode("B");

        graph.addEdge("A", "B", 1);
        graph.addEdge("B", "A", 2);

        System.out.println(graph.isNeighbor("A", "B"));
        System.out.println(graph.isNeighbor("B", "A"));

        graph.getNeighbors("B").forEach(edge -> {
            System.out.println(edge.getNode().getLabel() + " - " + edge.getWeight());
        });
    }
}
