package share;

import interfaces.IGraph;
import structures.AdjacencyList;
import structures.AdjacencyMatrix;
import utils.GraphTypes;


/**
 * The Graph class represents a graph data structure.
 * It provides a factory method to create different types of graphs.
 */
public class Graph {
    /**
     * Creates a graph based on the specified type, weightedness, and directedness.
     *
     * @param type       the type of graph to create (LIST or MATRIX)
     * @param isWeighted true if the graph is weighted, false otherwise
     * @param isDirected true if the graph is directed, false otherwise
     * @return the created graph
     */
    public static IGraph createGraph(GraphTypes type, boolean isWeighted, boolean isDirected) {
        if (type == GraphTypes.LIST) {
            return new AdjacencyList(isWeighted, isDirected);
        } else if (type == GraphTypes.MATRIX) {
            return new AdjacencyMatrix(isWeighted, isDirected);
        }
        return null;
    }
}