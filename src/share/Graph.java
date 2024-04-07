package share;

import interfaces.IGraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import structures.AdjacencyList;
import structures.AdjacencyMatrix;
import utils.GraphTypes;

/**
 * The Graph class represents a graph data structure.
 * It provides a factory method to create different types of graphs.
 */
public class Graph {

  private static String currentPath = Paths.get("").toAbsolutePath().toString();

  /**
   * Creates a graph based on the specified type, weightedness, and directedness.
   *
   * @param type       the type of graph to create (LIST or MATRIX)
   * @param isWeighted true if the graph is weighted, false otherwise
   * @param isDirected true if the graph is directed, false otherwise
   * @return the created graph
   */

  public static IGraph createGraph(
    GraphTypes type,
    boolean isWeighted,
    boolean isDirected
  ) {
    if (type == GraphTypes.LIST) {
      return new AdjacencyList(isWeighted, isDirected);
    } else if (type == GraphTypes.MATRIX) {
      return new AdjacencyMatrix(isWeighted, isDirected);
    }
    return null;
  }

  public static IGraph loadGraph(String filename) {
    boolean directed = false;
    boolean weighted = false;
    String representation = "";

    IGraph graph = null;

    try (
      BufferedReader br = new BufferedReader(
        new FileReader(currentPath + filename)
      )
    ) {
      String line;
      List<String> nodes = new ArrayList<>();
      while ((line = br.readLine()) != null) {
        if (line.startsWith("%")) {
          if (line.contains("directed=true")) {
            directed = true;
          } else if (line.contains("weighted=true")) {
            weighted = true;
          } else if (line.contains("representation=")) {
            representation = line.split("=")[1];
          }
        } else if (line.startsWith("*Vertices")) {
          graph =
            createGraph(
              GraphTypes.valueOf(representation.toUpperCase()),
              weighted,
              directed
            );
          int numNodes = Integer.parseInt(line.split(" ")[1]);
          for (int i = 0; i < numNodes; i++) {
            String[] vertex = br.readLine().split(" ");
            nodes.add(vertex[1]);
            graph.addNode(vertex[1]);
          }
        } else if (line.startsWith("*arcs")) {
          while ((line = br.readLine()) != null) {
            String[] edge = line.split(" ");
            int itWeighted;
            if (edge[2] != null) {
              itWeighted = Integer.parseInt(edge[2]);
            } else {
              itWeighted = 1;
            }
            graph.addEdge(
              nodes.get(Integer.parseInt(edge[0])),
              nodes.get(Integer.parseInt(edge[1])),
              itWeighted
            );
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return graph;
  }
}
