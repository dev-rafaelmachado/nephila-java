package structures;

import interfaces.IGraph;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;
import share.Edge;
import share.ExternalEdge;
import share.Node;
import share.PathWithWeight;
import share.Queue.Queue;
import share.Tree.BinaryTree;
import share.Tree.BinaryTreeNode;

public class AdjacencyMatrix implements IGraph {

  private static String currentPath = Paths.get("").toAbsolutePath().toString();

  private List<List<Integer>> matrix;
  private List<Node> nodes;
  private boolean isWeighted;
  private boolean isDirected;

  public AdjacencyMatrix(boolean isWeighted, boolean isDirected) {
    this.isWeighted = isWeighted;
    this.isDirected = isDirected;
    nodes = new ArrayList<>();
    matrix = new ArrayList<>();
  }

  public int getSize() {
    return nodes.size();
  }

  public int getEdgeSize() {
    int edges = 0;
    for (Node node : nodes) {
      edges += node.getNeighbors().size();
    }
    return edges;
  }

  private Node getNode(String label) {
    for (Node node : nodes) {
      if (node.getLabel().equals(label)) {
        return node;
      }
    }
    throw new IllegalArgumentException("Node " + label + " not found");
  }

  private int getNodeIndex(String label) {
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).getLabel().equals(label)) {
        return i;
      }
    }
    throw new IllegalArgumentException("Node " + label + " not found");
  }

  private int hasNode(String label) {
    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).getLabel().equals(label)) {
        return i;
      }
    }
    return -1;
  }

  public void addNode(String label) {
    boolean hasNode = false;
    for (Node node : nodes) {
      if (node.getLabel().equals(label)) {
        hasNode = true;
        break;
      }
    }
    if (!hasNode) {
      nodes.add(new Node(label));
      List<Integer> blank = new ArrayList<Integer>();
      for (int i = 0; i < nodes.size() - 1; i++) {
        blank.add(null);
      }
      matrix.add(blank);
      for (int i = 0; i < nodes.size(); i++) {
        matrix.get(i).add(null);
      }
      return;
    }
    System.out.println("Node" + label + " already exists");
  }

  public void addEdge(String from, String to, int weight) {
    try {
      int fromIndex = getNodeIndex(from);
      int toIndex = getNodeIndex(to);
      int itWeight = isWeighted ? weight : 1;

      matrix.get(fromIndex).set(toIndex, itWeight);
      if (!isDirected) {
        matrix.get(toIndex).set(fromIndex, itWeight);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void removeNode(String label) {
    try {
      int index = getNodeIndex(label);
      if (index != -1) {
        for (int i = 0; i < nodes.size(); i++) {
          matrix.get(i).remove(index);
        }
        matrix.remove(index);
        nodes.remove(index);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void removeEdge(String from, String to) {
    try {
      int fromIndex = getNodeIndex(from);
      int toIndex = getNodeIndex(to);
      matrix.get(fromIndex).set(toIndex, null);
      if (!isDirected) {
        matrix.get(toIndex).set(fromIndex, null);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void updateEdge(String from, String to, int weight) {
    try {
      int fromIndex = hasNode(from);
      int toIndex = hasNode(to);
      int itWeight = isWeighted ? weight : 1;

      if (fromIndex == -1) {
        addNode(from);
        fromIndex = getNodeIndex(from);
      }
      if (toIndex == -1) {
        addNode(to);
        toIndex = getNodeIndex(to);
      }
      matrix.get(fromIndex).set(toIndex, itWeight);
      if (!isDirected) {
        matrix.get(toIndex).set(fromIndex, itWeight);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public Integer getEdgeWeight(String from, String to) {
    try {
      int fromIndex = getNodeIndex(from);
      int toIndex = getNodeIndex(to);

      return matrix.get(fromIndex).get(toIndex);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public boolean isNeighbor(String from, String to) {
    try {
      int fromIndex = getNodeIndex(from);
      int toIndex = getNodeIndex(to);

      return matrix.get(fromIndex).get(toIndex) != null;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public List<Edge> getNeighbors(String label) {
    List<Edge> edges = new ArrayList<>();
    try {
      int index = getNodeIndex(label);
      for (int i = 0; i < nodes.size(); i++) {
        if (matrix.get(index).get(i) != null) {
          edges.add(new Edge(nodes.get(i), matrix.get(index).get(i)));
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
    return edges;
  }

  public int getIndegre(String label) {
    try {
      int index = getNodeIndex(label);
      int indegre = 0;
      for (int i = 0; i < nodes.size(); i++) {
        if (matrix.get(i).get(index) != null) {
          indegre++;
        }
      }
      return indegre;
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
  }

  public int getOutdegre(String label) {
    try {
      int index = getNodeIndex(label);
      int outdegre = 0;
      for (int i = 0; i < nodes.size(); i++) {
        if (matrix.get(index).get(i) != null) {
          outdegre++;
        }
      }
      return outdegre;
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
  }

  public int getDegree(String label) {
    try {
      if (isDirected) return getIndegre(label) + getOutdegre(label);
      return getIndegre(label);
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
  }

  public AdjacencyMatrix getWarshall() {
    AdjacencyMatrix warshall = new AdjacencyMatrix(false, true);

    for (int i = 0; i < nodes.size(); i++) {
      for (int j = 0; j < nodes.size(); j++) {
        if (matrix.get(i).get(j) != null) {
          warshall.updateEdge(
            nodes.get(i).getLabel(),
            nodes.get(j).getLabel(),
            1
          );
        }
      }
    }

    for (int k = 0; k < nodes.size(); k++) {
      for (int i = 0; i < nodes.size(); i++) {
        for (int j = 0; j < nodes.size(); j++) {
          if (
            warshall.getEdgeWeight(
              nodes.get(i).getLabel(),
              nodes.get(k).getLabel()
            ) !=
            null &&
            warshall.getEdgeWeight(
              nodes.get(k).getLabel(),
              nodes.get(j).getLabel()
            ) !=
            null
          ) {
            if (
              warshall.getEdgeWeight(
                nodes.get(i).getLabel(),
                nodes.get(j).getLabel()
              ) ==
              null
            ) {
              warshall.addEdge(
                nodes.get(i).getLabel(),
                nodes.get(j).getLabel(),
                1
              );
            } else {
              warshall.updateEdge(
                nodes.get(i).getLabel(),
                nodes.get(j).getLabel(),
                Math.min(
                  warshall.getEdgeWeight(
                    nodes.get(i).getLabel(),
                    nodes.get(j).getLabel()
                  ),
                  warshall.getEdgeWeight(
                    nodes.get(i).getLabel(),
                    nodes.get(k).getLabel()
                  ) +
                  warshall.getEdgeWeight(
                    nodes.get(k).getLabel(),
                    nodes.get(j).getLabel()
                  )
                )
              );
            }
          }
        }
      }
    }

    return warshall;
  }

  public String toString() {
    StringBuilder result = new StringBuilder();

    result.append("Nodes: ");
    for (int i = 0; i < nodes.size(); i++) {
      result
        .append(nodes.get(i).getLabel())
        .append("(")
        .append(i)
        .append("), ");
    }
    result.append("\nEdges:\n");
    for (int i = 0; i < nodes.size(); i++) {
      result.append(nodes.get(i).getLabel()).append(": ");
      for (int j = 0; j < nodes.size(); j++) {
        result.append(matrix.get(i).get(j)).append(" ");
      }
      result.append("\n");
    }

    return result.toString();
  }

  public BinaryTree dfs(String start, String end) {
    BinaryTree tree = new BinaryTree();
    List<Node> visitedNodes = new ArrayList<>();
    Node startNode = getNode(start);

    if (startNode != null) {
      Stack<Node> stack = new Stack<>();
      stack.push(startNode);
      while (!stack.isEmpty()) {
        Node node = stack.pop();
        if (!tree.hasNode(node.getLabel())) {
          tree.insert(node.getLabel());
          if (node.getLabel().equals(end)) {
            break;
          }
          for (Edge edge : getNeighbors(node.getLabel())) {
            if (!visitedNodes.contains(edge.getNode())) {
              stack.push(edge.getNode());
            }
          }
        }
      }
    }
    return tree;
  }

  public BinaryTree bfs(String start, String end) {
    BinaryTree tree = new BinaryTree();
    List<Node> visitedNodes = new ArrayList<>();

    Node startNode = getNode(start);
    if (startNode != null) {
      Queue<Node> queue = new Queue<>();
      queue.enqueue(startNode);
      while (!queue.isEmpty()) {
        Node node = queue.dequeue();
        if (!tree.hasNode(node.getLabel())) {
          tree.insert(node.getLabel());
          visitedNodes.add(node);
          if (node.getLabel().equals(end)) {
            break;
          }
          for (Edge edge : getNeighbors(node.getLabel())) {
            if (!visitedNodes.contains(edge.getNode())) {
              queue.enqueue(edge.getNode());
            }
          }
        }
      }
    }
    return tree;
  }

  public boolean isEulerian() {
    BinaryTree tree = dfs(nodes.get(0).getLabel(), null);
    if (tree.returnAllNodes().size() != nodes.size()) {
      return false;
    }

    if (this.isDirected) {
      int baseIndegre = getIndegre(nodes.get(0).getLabel());
      int baseOutdegre = getOutdegre(nodes.get(0).getLabel());

      for (Node node : nodes) {
        if (
          getIndegre(node.getLabel()) != baseIndegre ||
          getOutdegre(node.getLabel()) != baseOutdegre
        ) {
          return false;
        }
      }
    }

    if (!this.isDirected) {
      for (Node node : nodes) {
        if (getDegree(node.getLabel()) % 2 != 0) {
          return false;
        }
      }
    }
    return true;
  }

  private Node getClosestNode(List<Node> unvisitedNodes) {
    Node closestNode = unvisitedNodes.get(0);
    for (Node node : unvisitedNodes) {
      if (node.getDistance() < closestNode.getDistance()) {
        closestNode = node;
      }
    }
    return closestNode;
  }

  private Node getFarthestNode(List<Node> unvisitedNodes) {
    Node farthestNode = unvisitedNodes.get(0);
    for (Node node : unvisitedNodes) {
      if (node.getDistance() > farthestNode.getDistance()) {
        farthestNode = node;
      }
    }
    return farthestNode;
  }

  public PathWithWeight getDijkstra(String start, String end) {
    return getDijkstra(start, end, Optional.empty());
  }

  public PathWithWeight getDijkstra(
    String start,
    String end,
    Optional<Boolean> reverseWeightOpt
  ) {
    boolean reverseWeight = reverseWeightOpt.orElse(false);
    List<String> path = new ArrayList<>();
    List<Node> visitedNodes = new ArrayList<>();
    List<Node> unvisitedNodes = new ArrayList<>();
    Node startNode = getNode(start);
    Node endNode = getNode(end);

    if (startNode != null && endNode != null) {
      for (Node node : nodes) {
        if (node.equals(startNode)) {
          node.setDistance(0);
        } else {
          node.setDistance(Integer.MAX_VALUE);
        }
        node.setShortestPath(new ArrayList<>());
        unvisitedNodes.add(node);
      }

      while (!unvisitedNodes.isEmpty()) {
        Node currentNode = reverseWeight
          ? getFarthestNode(unvisitedNodes)
          : getClosestNode(unvisitedNodes);
        if (currentNode.getDistance() == Integer.MAX_VALUE) {
          break;
        }
        unvisitedNodes.remove(currentNode);
        for (Edge edge : getNeighbors(currentNode.getLabel())) {
          Node neighbor = edge.getNode();
          if (!visitedNodes.contains(neighbor)) {
            int newDistance = currentNode.getDistance() + edge.getWeight();
            if (newDistance < neighbor.getDistance()) {
              neighbor.setDistance(newDistance);
              List<Node> shortestPath = new ArrayList<>(
                currentNode.getShortestPath()
              );
              shortestPath.add(currentNode);
              neighbor.setShortestPath(shortestPath);
            }
          }
        }
        visitedNodes.add(currentNode);
      }

      if (endNode.getDistance() < Integer.MAX_VALUE) {
        for (Node node : endNode.getShortestPath()) {
          path.add(node.getLabel());
        }
        path.add(endNode.getLabel());
        return new PathWithWeight(path, endNode.getDistance());
      }
    }

    return new PathWithWeight(path, -1);
  }

  public IGraph getPrim(String start) {
    BinaryTree tree = dfs(nodes.get(0).getLabel(), null);
    if (tree.returnAllNodes().size() != nodes.size()) {
      return null;
    }

    List<Node> visitedNodes = new ArrayList<>();

    IGraph prim = new AdjacencyList(true, false);
    for (Node node : nodes) {
      prim.addNode(node.getLabel());
    }

    Node startNode = getNode(start);
    visitedNodes.add(startNode);

    while (visitedNodes.size() < nodes.size()) {
      Edge minEdge = new Edge(null, Integer.MAX_VALUE);
      Node minNode = null;

      for (Node currentNode : visitedNodes) {
        for (Edge edge : getNeighbors(currentNode.getLabel())) {
          if (
            !visitedNodes.contains(edge.getNode()) &&
            edge.getWeight() < minEdge.getWeight()
          ) {
            minEdge = edge;
            minNode = currentNode;
          }
        }
      }

      if (minEdge.getNode() != null) {
        visitedNodes.add(minEdge.getNode());
        prim.addEdge(
          minNode.getLabel(),
          minEdge.getNode().getLabel(),
          minEdge.getWeight()
        );
      }
    }

    return prim;
  }

  public void saveGraph(String filename) {
    try (
      BufferedWriter writer = new BufferedWriter(
        new FileWriter(currentPath + "/" + filename)
      )
    ) {
      writer.write("% directed=" + isDirected);
      writer.newLine();
      writer.write("% weighted=" + isWeighted);
      writer.newLine();
      writer.write("% representation=matrix");
      writer.newLine();
      writer.write("*Vertices " + nodes.size());
      writer.newLine();
      for (int i = 0; i < nodes.size(); i++) {
        writer.write(i + 1 + " " + nodes.get(i).getLabel());
        writer.newLine();
      }
      writer.write("*arcs");
      writer.newLine();
      for (int i = 0; i < nodes.size(); i++) {
        for (int j = 0; j < nodes.size(); j++) {
          if (matrix.get(i).get(j) != null) {
            writer.write((i + 1) + " " + (j + 1));
            writer.newLine();
          }
        }
      }
    } catch (IOException e) {
      System.err.println("Error writing to file: " + e.getMessage());
    }
  }

  public void degreeDistribution(String filename) {
    Map<Integer, Integer> degreeDistribution = new HashMap<>();
    for (Node node : nodes) {
      int degree = getDegree(node.getLabel());
      degreeDistribution.put(
        degree,
        degreeDistribution.getOrDefault(degree, 0) + 1
      );
    }

    List<Integer> xData = new ArrayList<>(degreeDistribution.keySet());
    List<Integer> yData = new ArrayList<>(degreeDistribution.values());

    CategoryChart chart = new CategoryChartBuilder()
      .width(800)
      .height(600)
      .title("Degree Distribution")
      .xAxisTitle("Degree")
      .yAxisTitle("Frequency")
      .build();

    chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
    chart.getStyler().setAvailableSpaceFill(.96);
    chart.getStyler().setOverlapped(true);

    chart.addSeries("Degree Distribution", xData, yData);

    try {
      BitmapEncoder.saveBitmap(
        chart,
        currentPath + "/" + filename,
        BitmapEncoder.BitmapFormat.PNG
      );
    } catch (IOException e) {
      System.err.println("Error saving chart to file: " + e.getMessage());
    }
  }

  // --- TDE 2

  private List<Node> binaryTreeNodeToNode(
    List<BinaryTreeNode> binaryTreeNodes
  ) {
    List<Node> nodes = new ArrayList<>();
    for (BinaryTreeNode binaryTreeNode : binaryTreeNodes) {
      nodes.add(getNode(binaryTreeNode.getData()));
    }
    return nodes;
  }

  public List<IGraph> getConnectedComponents() {
    if (isDirected) {
      throw new IllegalArgumentException(
        "This method is only for undirected graphs"
      );
    }
    List<IGraph> connectedComponents = new ArrayList<>();
    List<Node> visitedNodes = new ArrayList<>();

    for (Node node : nodes) {
      if (!visitedNodes.contains(node)) {
        BinaryTree tree = dfs(node.getLabel(), null);
        List<Node> connectedComponent = binaryTreeNodeToNode(
          tree.returnAllNodes()
        );
        AdjacencyList component = new AdjacencyList(isWeighted, isDirected);
        for (Node n : connectedComponent) {
          component.addNode(n.getLabel());
          for (Edge edge : n.getNeighbors()) {
            component.updateEdge(
              n.getLabel(),
              edge.getNode().getLabel(),
              edge.getWeight()
            );
          }
        }
        connectedComponents.add(component);
        visitedNodes.addAll(connectedComponent);
      }
    }
    return connectedComponents;
  }

  private AdjacencyMatrix transposeGraph() {
    AdjacencyMatrix transposed = new AdjacencyMatrix(
      this.isWeighted,
      this.isDirected
    );
    for (int i = 0; i < this.nodes.size(); i++) {
      for (int j = 0; j < this.nodes.size(); j++) {
        if (this.matrix.get(j).get(i) != null) {
          transposed.updateEdge(
            this.nodes.get(i).getLabel(),
            this.nodes.get(j).getLabel(),
            this.matrix.get(j).get(i)
          );
        }
      }
    }
    return transposed;
  }

  private void fillOrder(Node node, List<Node> visited, Stack<Node> stack) {
    visited.add(node);
    for (Edge edge : getNeighbors(node.getLabel())) {
      if (!visited.contains(edge.getNode())) {
        fillOrder(edge.getNode(), visited, stack);
      }
    }
    stack.push(node);
  }

  public List<IGraph> getStronglyConnectedComponents() {
    if (!isDirected) {
      throw new IllegalArgumentException(
        "This method is only for directed graphs"
      );
    }

    Stack<Node> stack = new Stack<>();
    List<Node> visited = new ArrayList<>();

    for (Node node : nodes) {
      if (!visited.contains(node)) {
        fillOrder(node, visited, stack);
      }
    }

    AdjacencyMatrix transposed = transposeGraph();
    List<IGraph> stronglyConnectedComponents = new ArrayList<>();
    visited.clear();

    while (!stack.isEmpty()) {
      Node node = stack.pop();
      if (!visited.contains(node)) {
        BinaryTree tree = transposed.dfs(node.getLabel(), null);
        List<Node> stronglyConnectedComponent = binaryTreeNodeToNode(
          tree.returnAllNodes()
        );
        AdjacencyList component = new AdjacencyList(isWeighted, isDirected);
        for (Node n : stronglyConnectedComponent) {
          component.addNode(n.getLabel());
          for (Edge edge : n.getNeighbors()) {
            component.updateEdge(
              n.getLabel(),
              edge.getNode().getLabel(),
              edge.getWeight()
            );
          }
        }
        stronglyConnectedComponents.add(component);
        visited.addAll(stronglyConnectedComponent);
      }
    }

    return stronglyConnectedComponents;
  }

  public Double getDegreeCentrality(String label) {
    return (double) getDegree(label) / (nodes.size() - 1);
  }

  public Map<String, Double> getDegreeCentralityOfAllNodes() {
    Map<String, Double> degreeCentrality = new HashMap<>();
    for (Node node : nodes) {
      degreeCentrality.put(
        node.getLabel(),
        getDegreeCentrality(node.getLabel())
      );
    }
    return degreeCentrality;
  }

  public Double getBetweennessCentrality(String label) {
    return getBetweennessCentrality(label, Optional.empty());
  }

  public Double getBetweennessCentrality(
    String label,
    Optional<Boolean> reverseWeightOpt
  ) {
    Double betweennessCentrality = 0.0;

    for (Node sourceNode : nodes) {
      for (Node targetNode : nodes) {
        if (
          !sourceNode.getLabel().equals(targetNode.getLabel()) &&
          !sourceNode.getLabel().equals(label) &&
          !targetNode.getLabel().equals(label)
        ) {
          List<String> shortestPath = getDijkstra(
            sourceNode.getLabel(),
            targetNode.getLabel(),
            reverseWeightOpt
          )
            .getPath();

          if (!shortestPath.isEmpty()) {
            if (shortestPath.contains(label)) {
              betweennessCentrality++;
            }
          }
        }
      }
    }

    return betweennessCentrality;
  }

  public Map<String, Double> getBetweennessCentralityOfAllNodes() {
    return getBetweennessCentralityOfAllNodes(Optional.empty());
  }

  public Map<String, Double> getBetweennessCentralityOfAllNodes(
    Optional<Boolean> reverseWeightOpt
  ) {
    Map<String, Double> betweennessCentrality = new HashMap<>();
    for (Node node : nodes) {
      betweennessCentrality.put(
        node.getLabel(),
        getBetweennessCentrality(node.getLabel(), reverseWeightOpt)
      );
    }
    return betweennessCentrality;
  }

  public Double getClosenessCentrality(String label) {
    return getClosenessCentrality(label, Optional.empty());
  }

  public Double getClosenessCentrality(
    String label,
    Optional<Boolean> reverseWeightOpt
  ) {
    Double sum = 0.0;
    for (Node node : nodes) {
      double shortestPath = getDijkstra(
        node.getLabel(),
        label,
        reverseWeightOpt
      )
        .getTotalWeight();
      if (shortestPath != -1) {
        sum += shortestPath;
      }
    }

    return (nodes.size() - 1) / sum;
  }

  public Map<String, Double> getClosenessCentralityOfAllNodes() {
    return getClosenessCentralityOfAllNodes(Optional.empty());
  }

  public Map<String, Double> getClosenessCentralityOfAllNodes(
    Optional<Boolean> reverseWeightOpt
  ) {
    Map<String, Double> closenessCentrality = new HashMap<>();
    for (Node node : nodes) {
      closenessCentrality.put(
        node.getLabel(),
        getClosenessCentrality(node.getLabel(), reverseWeightOpt)
      );
    }
    return closenessCentrality;
  }

  public Double getEccentricity(String label) {
    return getEccentricity(label, Optional.empty());
  }

  public Double getEccentricity(
    String label,
    Optional<Boolean> reverseWeightOpt
  ) {
    BinaryTree tree = dfs(nodes.get(0).getLabel(), null);
    if (tree.returnAllNodes().size() != nodes.size()) {
      throw new IllegalArgumentException("The graph is not connected");
    }

    Double eccentricity = 0.0;
    for (Node node : nodes) {
      double shortestPath = getDijkstra(
        node.getLabel(),
        label,
        reverseWeightOpt
      )
        .getTotalWeight();
      if (shortestPath != -1) {
        eccentricity = Math.max(eccentricity, (double) shortestPath);
      }
    }

    return eccentricity;
  }

  public Map<String, Double> getEccentricityOfAllNodes() {
    return getEccentricityOfAllNodes(Optional.empty());
  }

  public Map<String, Double> getEccentricityOfAllNodes(
    Optional<Boolean> reverseWeightOpt
  ) {
    Map<String, Double> eccentricity = new HashMap<>();
    for (Node node : nodes) {
      eccentricity.put(
        node.getLabel(),
        getEccentricity(node.getLabel(), reverseWeightOpt)
      );
    }
    return eccentricity;
  }

  public Double getRadius() {
    return getRadius(Optional.empty());
  }

  public Double getRadius(Optional<Boolean> reverseWeightOpt) {
    Double radius = Double.MAX_VALUE;
    Map<String, Double> eccentricity = getEccentricityOfAllNodes(
      reverseWeightOpt
    );
    for (Double value : eccentricity.values()) {
      radius = Math.min(radius, value);
    }
    return radius;
  }

  public Double getDiameter() {
    return getDiameter(Optional.empty());
  }

  public Double getDiameter(Optional<Boolean> reverseWeightOpt) {
    Double diameter = 0.0;
    Map<String, Double> eccentricity = getEccentricityOfAllNodes(
      reverseWeightOpt
    );
    for (Double value : eccentricity.values()) {
      diameter = Math.max(diameter, value);
    }
    return diameter;
  }

  public Double getEdgeBetweenness(String from, String to) {
    return getEdgeBetweenness(from, to, Optional.empty());
  }

  public Double getEdgeBetweenness(
    String from,
    String to,
    Optional<Boolean> reverseWeightOpt
  ) {
    Double edgeBetweenness = 0.0;

    for (Node sourceNode : nodes) {
      for (Node targetNode : nodes) {
        if (!sourceNode.getLabel().equals(targetNode.getLabel())) {
          List<String> shortestPath = getDijkstra(
            sourceNode.getLabel(),
            targetNode.getLabel(),
            reverseWeightOpt
          )
            .getPath();

          if (!shortestPath.isEmpty()) {
            if (shortestPath.contains(from) && shortestPath.contains(to)) {
              edgeBetweenness++;
            }
          }
        }
      }
    }

    return edgeBetweenness;
  }

  public Map<ExternalEdge, Double> getEdgeBetweennessOfAllEdges() {
    return getEdgeBetweennessOfAllEdges(Optional.empty());
  }

  public Map<ExternalEdge, Double> getEdgeBetweennessOfAllEdges(
    Optional<Boolean> reverseWeightOpt
  ) {
    Map<ExternalEdge, Double> edgeBetweenness = new HashMap<>();
    Set<ExternalEdge> processedEdges = new HashSet<>();
    for (Node node : nodes) {
      for (Edge edge : node.getNeighbors()) {
        ExternalEdge externalEdge = new ExternalEdge(
          node,
          edge.getNode(),
          edge.getWeight()
        );
        if (!processedEdges.contains(externalEdge)) {
          double betweenness = getEdgeBetweenness(
            node.getLabel(),
            edge.getNode().getLabel(),
            reverseWeightOpt
          );
          edgeBetweenness.put(externalEdge, betweenness);
          processedEdges.add(externalEdge);
        }
      }
    }

    return edgeBetweenness;
  }

  public List<IGraph> getCommunities(int k) {
    return getCommunities(k, Optional.empty());
  }

  public List<IGraph> getCommunities(
    int k,
    Optional<Boolean> reverseWeightOpt
  ) {
    if (isDirected) {
      throw new IllegalArgumentException(
        "This method is only for undirected graphs"
      );
    }

    IGraph copyGraph = new AdjacencyList(isWeighted, isDirected);
    for (Node node : nodes) {
      for (Edge edge : node.getNeighbors()) {
        copyGraph.updateEdge(
          node.getLabel(),
          edge.getNode().getLabel(),
          edge.getWeight()
        );
      }
    }

    List<IGraph> communities = new ArrayList<>();

    while (communities.size() < k) {
      Map<ExternalEdge, Double> edgeBetweenness = copyGraph.getEdgeBetweennessOfAllEdges(
        reverseWeightOpt
      );

      ExternalEdge edgeToRemove = null;
      double maxBetweenness = 0.0;
      for (Map.Entry<ExternalEdge, Double> entry : edgeBetweenness.entrySet()) {
        if (entry.getValue() > maxBetweenness) {
          maxBetweenness = entry.getValue();
          edgeToRemove = entry.getKey();
        }
      }

      if (edgeToRemove != null) {
        copyGraph.removeEdge(
          edgeToRemove.getFromNode().getLabel(),
          edgeToRemove.getToNode().getLabel()
        );
      } else {
        break;
      }

      List<IGraph> connectedComponents = copyGraph.getConnectedComponents();

      if (connectedComponents.size() > communities.size()) {
        communities = connectedComponents;
      }
    }

    return communities;
  }

  public Double getAverageGeodesicDistance() {
    return getAverageGeodesicDistance(Optional.empty());
  }

  public Double getAverageGeodesicDistance(Optional<Boolean> reverseWeightOpt) {
    Double sum = 0.0;
    int count = 0;
    for (Node node : nodes) {
      for (Node targetNode : nodes) {
        if (!node.getLabel().equals(targetNode.getLabel())) {
          double shortestPath = getDijkstra(
            node.getLabel(),
            targetNode.getLabel(),
            reverseWeightOpt
          )
            .getTotalWeight();
          if (shortestPath != -1) {
            sum += shortestPath;
            count++;
          }
        }
      }
    }

    if (isDirected) {
      return sum / (count * (count - 1));
    }
    return sum / (count * (count - 1) / 2);
  }
}
