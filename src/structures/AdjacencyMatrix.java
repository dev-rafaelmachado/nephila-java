package structures;

import interfaces.IGraph;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import share.Edge;
import share.Node;
import share.Queue.Queue;
import share.Tree.BinaryTree;

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

  protected List<Node> getNodes() {
    return nodes;
  }

  protected List<List<Integer>> getMatrix() {
    return matrix;
  }

  protected void eraseGraph() {
    nodes = new ArrayList<>();
    matrix = new ArrayList<>();
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
      return getIndegre(label) + getOutdegre(label);
    } catch (Exception e) {
      System.out.println(e);
      return -1;
    }
  }

  public List<List<Integer>> getWarshall() {
    List<List<Integer>> warshall = new ArrayList<>();
    for (int i = 0; i < nodes.size(); i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < nodes.size(); j++) {
        row.add(matrix.get(i).get(j));
      }
      warshall.add(row);
    }

    for (int k = 0; k < nodes.size(); k++) {
      for (int i = 0; i < nodes.size(); i++) {
        for (int j = 0; j < nodes.size(); j++) {
          if (
            warshall.get(i).get(k) != null && warshall.get(k).get(j) != null
          ) {
            if (warshall.get(i).get(j) == null) {
              warshall
                .get(i)
                .set(j, warshall.get(i).get(k) + warshall.get(k).get(j));
            } else {
              warshall
                .get(i)
                .set(
                  j,
                  Math.min(
                    warshall.get(i).get(j),
                    warshall.get(i).get(k) + warshall.get(k).get(j)
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

      for (Node node : endNode.getShortestPath()) {
        path.add(node.getLabel());
      }
      path.add(endNode.getLabel());
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
        new FileWriter(currentPath + filename)
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
}
