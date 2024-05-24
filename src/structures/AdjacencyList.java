package structures;

import interfaces.IGraph;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;
import share.Edge;
import share.Node;
import share.Queue.Queue;
import share.Tree.BinaryTree;

public class AdjacencyList implements IGraph {

  private static String currentPath = Paths.get("").toAbsolutePath().toString();

  private List<Node> nodes;
  private boolean isWeighted;
  private boolean isDirected;

  public AdjacencyList(boolean isWeighted, boolean isDirected) {
    nodes = new ArrayList<>();
    this.isWeighted = isWeighted;
    this.isDirected = isDirected;
  }

  private Node getNode(String label) {
    for (Node node : nodes) {
      if (node.getLabel().equals(label)) {
        return node;
      }
    }
    throw new IllegalArgumentException("Node " + label + " not found");
  }

  private Node hasNode(String label) {
    for (Node node : nodes) {
      if (node.getLabel().equals(label)) {
        return node;
      }
    }
    return null;
  }

  private List<List<Integer>> getGraphMatrix() {
    List<List<Integer>> matrix = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < nodes.size(); j++) {
        row.add(null);
      }
      matrix.add(row);
    }

    for (int i = 0; i < nodes.size(); i++) {
      Node node = nodes.get(i);
      for (Edge edge : node.getNeighbors()) {
        int j = nodes.indexOf(edge.getNode());
        matrix.get(i).set(j, edge.getWeight());
      }
    }

    return matrix;
  }

  public void addNode(String label) {
    boolean exists = hasNode(label) != null;
    if (!exists) {
      nodes.add(new Node(label));
      return;
    }
    System.out.println("Node " + label + " already exists");
  }

  public void addEdge(String from, String to, int weight) {
    try {
      Node fromNode = getNode(from);
      Node toNode = getNode(to);
      int itWeight = isWeighted ? weight : 1;
      if (fromNode != null && toNode != null) {
        fromNode.addNeighbor(toNode, itWeight);
        if (!isDirected) {
          toNode.addNeighbor(fromNode, itWeight);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void removeNode(String label) {
    try {
      Node node = getNode(label);
      if (node != null) {
        nodes.remove(node);
        for (Node n : nodes) {
          n.removeNeighbor(node);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void removeEdge(String from, String to) {
    try {
      Node fromNode = getNode(from);
      Node toNode = getNode(to);
      if (fromNode != null && toNode != null) {
        fromNode.removeNeighbor(toNode);
        if (!isDirected) {
          toNode.removeNeighbor(fromNode);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void updateEdge(String from, String to, int weight) {
    try {
      Node fromNode = hasNode(from);
      Node toNode = hasNode(to);
      int itWeight = isWeighted ? weight : 1;

      if (fromNode == null) {
        addNode(from);
        fromNode = getNode(from);
      }
      if (toNode == null) {
        addNode(to);
        toNode = getNode(to);
      }
      fromNode.updateNeighbor(toNode, itWeight);
      if (!isDirected) {
        toNode.updateNeighbor(fromNode, itWeight);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public Integer getEdgeWeight(String from, String to) {
    try {
      Node fromNode = getNode(from);
      Node toNode = getNode(to);
      if (fromNode != null && toNode != null) {
        for (Edge edge : fromNode.getNeighbors()) {
          if (edge.getNode().equals(toNode)) {
            return edge.getWeight();
          }
        }
      }
      return null;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public boolean isNeighbor(String from, String to) {
    try {
      Node fromNode = getNode(from);
      Node toNode = getNode(to);
      if (fromNode != null && toNode != null) {
        return fromNode.isNeighbor(toNode);
      }
      return false;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public List<Edge> getNeighbors(String label) {
    try {
      Node node = getNode(label);
      if (node != null) {
        return node.getNeighbors();
      }
      return null;
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }

  public int getIndegre(String label) {
    try {
      Node node = getNode(label);
      int indegre = 0;
      for (Node n : nodes) {
        for (Edge edge : n.getNeighbors()) {
          if (edge.getNode().equals(node)) {
            indegre++;
          }
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
      Node node = getNode(label);
      return node.getNeighbors().size();
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
    List<List<Integer>> matrix = getGraphMatrix();

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
            int newWeight =
              warshall.getEdgeWeight(
                nodes.get(i).getLabel(),
                nodes.get(k).getLabel()
              ) +
              warshall.getEdgeWeight(
                nodes.get(k).getLabel(),
                nodes.get(j).getLabel()
              );
            if (
              warshall.getEdgeWeight(
                nodes.get(i).getLabel(),
                nodes.get(j).getLabel()
              ) ==
              null ||
              newWeight <
              warshall.getEdgeWeight(
                nodes.get(i).getLabel(),
                nodes.get(j).getLabel()
              )
            ) {
              warshall.updateEdge(
                nodes.get(i).getLabel(),
                nodes.get(j).getLabel(),
                newWeight
              );
            }
          }
        }
      }
    }

    return warshall;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Node node : nodes) {
      sb.append(node.getLabel()).append(": ");
      for (Edge edge : node.getNeighbors()) {
        sb.append(edge.getNode().getLabel());
        if (isWeighted) {
          sb.append("(").append(edge.getWeight()).append(")");
        }
        sb.append(", ");
      }
      sb.append("\n");
    }
    return sb.toString();
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
          for (Edge edge : node.getNeighbors()) {
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
          for (Edge edge : node.getNeighbors()) {
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

  public List<String> getDijkstra(String start, String end) {
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
        Node currentNode = getClosestNode(unvisitedNodes);
        unvisitedNodes.remove(currentNode);
        for (Edge edge : currentNode.getNeighbors()) {
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
      }
    }

    return path;
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
        for (Edge edge : currentNode.getNeighbors()) {
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
      writer.write("% representation=list");
      writer.newLine();
      writer.write("*Vertices " + nodes.size());
      writer.newLine();
      for (int i = 0; i < nodes.size(); i++) {
        writer.write(i + " " + nodes.get(i).getLabel());
        writer.newLine();
      }
      writer.write("*arcs");
      writer.newLine();
      for (Node node : nodes) {
        for (Edge edge : node.getNeighbors()) {
          writer.write(
            nodes.indexOf(node) +
            " " +
            nodes.indexOf(edge.getNode()) +
            " " +
            edge.getWeight()
          );
          writer.newLine();
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
}
