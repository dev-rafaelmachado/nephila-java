package share;

import interfaces.IGraph;
import structures.AdjacencyList;
import structures.AdjacencyMatrix;

public class Graph {
    public static IGraph createGraph(String type, boolean isWeighted, boolean isDirected) {
        if (type.equals("list")) {
            return new AdjacencyList(isWeighted, isDirected);
        } else if (type.equals("matrix")) {
            return new AdjacencyMatrix(isWeighted, isDirected);
        }
        return null;
    }
}