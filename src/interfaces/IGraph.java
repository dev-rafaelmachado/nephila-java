package interfaces;

import java.util.List;
import share.Edge;

/**
 * This interface represents a graph data structure.
 */
public interface IGraph {
  /**
   * Adds a node to the graph with the specified label.
   *
   * @param label the label of the node to be added
   */
  public void addNode(String label);

  /**
   * Adds an edge between two nodes in the graph with the specified labels and weight.
   *
   * @param from the label of the source node
   * @param to the label of the destination node
   * @param weight the weight of the edge
   */
  public void addEdge(String from, String to, int weight);

  /**
   * Removes a node from the graph with the specified label.
   *
   * @param label the label of the node to be removed
   */
  public void removeNode(String label);

  /**
   * Removes an edge between two nodes in the graph with the specified labels.
   *
   * @param from the label of the source node
   * @param to the label of the destination node
   */
  public void removeEdge(String from, String to);

  /**
   * Checks if two nodes in the graph are neighbors.
   *
   * @param from the label of the source node
   * @param to the label of the destination node
   * @return true if the nodes are neighbors, false otherwise
   */
  public boolean isNeighbor(String from, String to);

  /**
   * Returns a list of edges that are adjacent to the node with the specified label.
   *
   * @param label the label of the node
   * @return a list of adjacent edges
   */
  public List<Edge> getNeighbors(String label);

  /**
   * Update the weight of an edge between two nodes in the graph with the specified labels. if the edge does not exist, it will be created.
   * @param from the label of the source node
   * @param to the label of the destination node
   * @param weight the weight of the edge
   */
  public void updateEdge(String from, String to, int weight);

  /**
   * Get the weight of an edge between two nodes in the graph with the specified labels. if the edge does not exist, it will return null.
   */
  public Integer getEdgeWeight(String from, String to);

  /**
   * Get the indegre of a node in the graph with the specified label
   * @param label the label of the node
   * @return the indegre of the node
   */
  public int getIndegre(String label);

  /**
   * Get the outdegre of a node in the graph with the specified label
   * @param label the label of the node
   * @return the outdegre of the node
   */
  public int getOutdegre(String label);

  /**
   * Get degree of a node in the graph with the specified label
   * @param label the label of the node
   * @return the degree of the node
   */
  public int getDegree(String label);

  /**
   * Get the warshall matrix of the graph
   * @return the warshall matrix of the graph
   */
  public List<List<Integer>> getWarshall();

  /**
   * print the graph in a human-readable format
   */
  public String toString();
  /**
   * Save the graph in a file
   */
  // public void saveGraph(String filename);

  /**
   * returns a new matrix graph with the closure transitive of the graph (Warshall algorithm)
   */
  // public IGraph closureTransitive();
}
