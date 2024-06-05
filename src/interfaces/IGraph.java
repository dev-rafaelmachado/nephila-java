package interfaces;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import share.Edge;
import share.ExternalEdge;
import share.PathWithWeight;
import share.Tree.BinaryTree;
import structures.AdjacencyMatrix;
import utils.GraphTypes;

/**
 * This interface represents a graph data structure.
 */
public interface IGraph {
  /**
   * @return Get the length of nodes list of the graph.
   */
  public int getSize();

  /**
   * @return Get the length of edges list of the graph.
   */
  public int getEdgeSize();

  public GraphTypes getType();

  /**
   * Adds a node to the graph with the specified label.
   *
   * @param label the label of the node to be added
   */
  public void addNode(String label);

  /**
   * Adds an edge between two nodes in the graph with the specified labels and
   * weight.
   *
   * @param from   the label of the source node
   * @param to     the label of the destination node
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
   * @param to   the label of the destination node
   */
  public void removeEdge(String from, String to);

  /**
   * Checks if two nodes in the graph are neighbors.
   *
   * @param from the label of the source node
   * @param to   the label of the destination node
   * @return true if the nodes are neighbors, false otherwise
   */
  public boolean isNeighbor(String from, String to);

  /**
   * Returns a list of edges that are adjacent to the node with the specified
   * label.
   *
   * @param label the label of the node
   * @return a list of adjacent edges
   */
  public List<Edge> getNeighbors(String label);

  /**
   * Update the weight of an edge between two nodes in the graph with the
   * specified labels. if the edge does not exist, it will be created.
   *
   * @param from   the label of the source node
   * @param to     the label of the destination node
   * @param weight the weight of the edge
   */
  public void updateEdge(String from, String to, int weight);

  /**
   * Get the weight of an edge between two nodes in the graph with the specified
   * labels. if the edge does not exist, it will return null.
   */
  public Integer getEdgeWeight(String from, String to);

  /**
   * Get the indegre of a node in the graph with the specified label
   *
   * @param label the label of the node
   * @return the indegre of the node
   */
  public int getIndegre(String label);

  /**
   * Get the outdegre of a node in the graph with the specified label
   *
   * @param label the label of the node
   * @return the outdegre of the node
   */
  public int getOutdegre(String label);

  /**
   * Get degree of a node in the graph with the specified label
   *
   * @param label the label of the node
   * @return the degree of the node
   */
  public int getDegree(String label);

  /**
   * Get the warshall matrix of the graph
   *
   * @return the warshall matrix of the graph
   */
  public AdjacencyMatrix getWarshall();

  /**
   * print the graph in a human-readable format
   */
  public String toString();

  /**
   * Get the DFS path from start to end
   *
   * @param start
   * @param end
   * @return the DFS path from start to end
   */
  public BinaryTree dfs(String start, String end);

  /**
   * Get the BFS path from start to end
   *
   * @param start
   * @param end
   * @return the BFS path from start to end
   */
  public BinaryTree bfs(String start, String end);

  /**
   * Check if the graph is connected
   *
   * @return true if the graph is connected, false otherwise
   */
  public boolean isEulerian();

  /**
   * Get the Dijkstra path from start to end
   *
   * @param start
   * @param end
   * @param reverseWeightOpt
   * @return the Dijkstra path from start to end
   */
  public PathWithWeight getDijkstra(
    String start,
    String end,
    Optional<Boolean> reverseWeightOpt
  );

  /**
   * Get the Dijkstra path from start to end
   *
   * @param start
   * @param end
   * @return the Dijkstra path from start to end
   */
  public PathWithWeight getDijkstra(String start, String end);

  /**
   * Get the Prim tree (in graph form)
   *
   * @param start
   * @return the Prim tree (in graph form)
   */
  public IGraph getPrim(String start);

  /**
   * save the graph in a file
   *
   * @param filename
   */
  public void saveGraph(String filename);

  /**
   * Get the degree distribution of the graph and save it in a file
   * @param filename
   */
  public void degreeDistribution(String filename);

  /**
   * Get the connected components of the graph
   * @return the connected components of the graph as a list of lists of nodes
   */
  List<IGraph> getConnectedComponents();

  /**
   * Get the strongly connected components of the graph
   * @return the strongly connected components of the graph as a list of lists of nodes
   */
  List<IGraph> getStronglyConnectedComponents();

  /**
   * Get the degree centrality of a node in the graph with the specified label
   *
   * @param label the label of the node
   * @return the degree centrality of the node
   */
  Double getDegreeCentrality(String label);

  /**
   * Get the degree centrality of all nodes in the graph
   *
   * @return the degree centrality of all nodes in the graph
   */
  Map<String, Double> getDegreeCentralityOfAllNodes();

  /**
   * Get the Betweenness centrality of a node in the graph with the specified label
   *
   * @param label the label of the node
   * @param reverseWeightOpt
   * @return the closeness centrality of the node
   */
  Double getBetweennessCentrality(
    String label,
    Optional<Boolean> reverseWeightOpt
  );

  /**
   * Get the Betweenness centrality of a node in the graph with the specified label
   *
   * @param label the label of the node
   * @return the closeness centrality of the node
   */
  Double getBetweennessCentrality(String label);

  /**
   * Get the Betweenness centrality of all nodes in the graph
   *
   * @return the closeness centrality of all nodes in the graph
   * @param reverseWeightOpt
   */
  Map<String, Double> getBetweennessCentralityOfAllNodes(
    Optional<Boolean> reverseWeightOpt
  );

  /**
   * Get the Betweenness centrality of all nodes in the graph
   *
   * @return the closeness centrality of all nodes in the graph
   */
  Map<String, Double> getBetweennessCentralityOfAllNodes();

  /**
   * Get the Closeness centrality of a node in the graph with the specified label
   * @param label the label of the node
   * @param reverseWeightOpt
   * @return the closeness centrality of the node
   */
  Double getClosenessCentrality(
    String label,
    Optional<Boolean> reverseWeightOpt
  );

  /**
   * Get the Closeness centrality of a node in the graph with the specified label
   * @param label the label of the node
   * @return the closeness centrality of the node
   */
  Double getClosenessCentrality(String label);

  /**
   * Get the Closeness centrality of all nodes in the graph
   * @return the closeness centrality of all nodes in the graph
   * @param reverseWeightOpt
   */
  Map<String, Double> getClosenessCentralityOfAllNodes(
    Optional<Boolean> reverseWeightOpt
  );

  /**
   * Get the Closeness centrality of all nodes in the graph
   * @return the closeness centrality of all nodes in the graph
   */
  Map<String, Double> getClosenessCentralityOfAllNodes();

  /**
   * Get the Eccentricity of a node in the graph with the specified label
   *
   * @throws IllegalArgumentException if the graph is not connected
   * @param label the label of the node
   * @param reverseWeightOpt
   * @return the eccentricity of the node
   */
  Double getEccentricity(String label, Optional<Boolean> reverseWeightOpt);

  /**
   * Get the Eccentricity of a node in the graph with the specified label
   *
   * @throws IllegalArgumentException if the graph is not connected
   * @param label the label of the node
   * @return the eccentricity of the node
   */
  Double getEccentricity(String label);

  /**
   * Get the Eccentricity of all nodes in the graph
   * @param reverseWeightOpt
   * @throws IllegalArgumentException if the graph is not connected
   * @return the eccentricity of all nodes in the graph
   */
  Map<String, Double> getEccentricityOfAllNodes(
    Optional<Boolean> reverseWeightOpt
  );

  /**
   * Get the Eccentricity of all nodes in the graph
   * @throws IllegalArgumentException if the graph is not connected
   * @return the eccentricity of all nodes in the graph
   */
  Map<String, Double> getEccentricityOfAllNodes();

  /**
   * Get the Diameter of the graph
   * @param reverseWeightOpt
   * @throws IllegalArgumentException if the graph is not connected
   * @return the diameter of the graph
   */
  Double getDiameter(Optional<Boolean> reverseWeightOpt);

  /**
   * Get the Diameter of the graph
   * @throws IllegalArgumentException if the graph is not connected
   * @return the diameter of the graph
   */
  Double getDiameter();

  /**
   * Get the Radius of the graph
   * @param reverseWeightOpt
   * @throws IllegalArgumentException if the graph is not connected
   * @return the radius of the graph
   */
  Double getRadius(Optional<Boolean> reverseWeightOpt);

  /**
   * Get the Radius of the graph
   *
   * @throws IllegalArgumentException if the graph is not connected
   * @return the radius of the graph
   */
  Double getRadius();

  /**
   * Get the edge betweenness of an edge in the graph with the specified labels
   *
   * @param from the label of the source node
   * @param to the label of the destination node
   * @param reverseWeightOpt
   * @return the edge betweenness of the edge
   */
  Double getEdgeBetweenness(
    String from,
    String to,
    Optional<Boolean> reverseWeightOpt
  );

  /**
   * Get the edge betweenness of an edge in the graph with the specified labels
   *
   * @param from the label of the source node
   * @param to the label of the destination node
   * @return the edge betweenness of the edge
   */
  Double getEdgeBetweenness(String from, String to);

  /**
   * Get the edge betweenness of all edges in the graph
   * @param reverseWeightOpt
   * @return the edge betweenness of all edges in the graph
   */
  Map<ExternalEdge, Double> getEdgeBetweennessOfAllEdges(
    Optional<Boolean> reverseWeightOpt
  );

  /**
   * Get the edge betweenness of all edges in the graph
   * @return the edge betweenness of all edges in the graph
   */
  Map<ExternalEdge, Double> getEdgeBetweennessOfAllEdges();

  /**
   * Get the communities of the graph using Girvan-Newman algorithm
   *
   * @param k the number of communities
   * @param reverseWeightOpt
   * @return the communities of the graph
   */
  List<IGraph> getCommunities(int k, Optional<Boolean> reverseWeightOpt);

  /**
   * Get the communities of the graph using Girvan-Newman algorithm
   *
   * @param k the number of communities
   * @return the communities of the graph
   */
  List<IGraph> getCommunities(int k);

  /**
   * Get the average geodesic distance of the graph
   * @param reverseWeightOpt
   * @return the average geodesic distance of the graph
   */
  Double getAverageGeodesicDistance(Optional<Boolean> reverseWeightOpt);

  /**
   * Get the average geodesic distance of the graph
   * @return the average geodesic distance of the graph
   */
  Double getAverageGeodesicDistance();

  public List<ExternalEdge> getWeightedOfAllEdges();
}
