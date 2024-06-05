package implementation.utils;

import java.io.*;
import java.util.*;
import utils.GraphTypes;

public class GraphGenerator {

  static class Author {

    String name;
    int index;

    Author(String name, int index) {
      this.name = name;
      this.index = index;
    }
  }

  static class Edge {

    Author source;
    Author target;
    int weight;

    Edge(Author source, Author target) {
      this.source = source;
      this.target = target;
      this.weight = 1;
    }

    void increaseWeight() {
      this.weight++;
    }
  }

  static class Graph {

    Map<String, Author> authors = new LinkedHashMap<>();
    Map<String, Edge> edges = new HashMap<>();

    void addAuthor(String name, int index) {
      if (!authors.containsKey(name)) {
        authors.put(name, new Author(name, index));
      }
    }

    void addEdge(Author source, Author target) {
      String key = source.index + "-" + target.index;
      Edge edge = edges.get(key);
      if (edge == null) {
        edge = new Edge(source, target);
        edges.put(key, edge);
      } else {
        edge.increaseWeight();
      }
    }
  }

  public static void generateGraphFromCSV(
    String inputFile,
    String outputFile,
    GraphTypes type
  ) throws IOException {
    generateGraphFromCSV(inputFile, outputFile, type, Optional.empty());
  }

  public static void generateGraphFromCSV(
    String inputFile,
    String outputFile,
    GraphTypes type,
    Optional<Integer> length
  ) throws IOException {
    Graph graph = new Graph();
    List<String[]> data = readCSV(inputFile, length);
    insertVertices(data, graph);
    insertEdges(data, graph);

    writeGraphToFile(graph, outputFile, type);
  }

  public static List<String[]> readCSV(String inputFile) throws IOException {
    return readCSV(inputFile, Optional.empty());
  }

  public static List<String[]> readCSV(
    String inputFile,
    Optional<Integer> length
  ) throws IOException {
    List<String[]> data = new ArrayList<>();
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    String line = reader.readLine(); // skip header

    int count = 0;
    while ((line = reader.readLine()) != null) {
      if (length.isPresent() && count >= length.get()) {
        break;
      }
      String[] fields = line.split(",");
      data.add(fields);
      count++;
    }
    reader.close();
    return data;
  }

  public static void insertVertices(List<String[]> data, Graph graph) {
    int index = 0;
    for (String[] fields : data) {
      for (int i = 2; i < fields.length; i++) {
        if (fields[i].charAt(0) == ' ') fields[i] = fields[i].substring(1);
        String rawName =
          fields[i].replaceAll("[\\[\\]\\(\\)\\.,'\"]", "").replace(" ", "_");
        if (!graph.authors.containsKey(rawName)) {
          graph.addAuthor(rawName, index++);
        }
      }
    }
  }

  public static void insertEdges(List<String[]> data, Graph graph) {
    for (String[] fields : data) {
      List<String> names = new ArrayList<>();
      for (int i = 2; i < fields.length; i++) {
        // if string has a blank space in the index 0, remove it
        if (fields[i].charAt(0) == ' ') fields[i] = fields[i].substring(1);
        String rawName =
          fields[i].replaceAll("[\\[\\]\\(\\)\\.,'\"]", "").replace(" ", "_");
        names.add(rawName);
      }

      if (names.size() < 2) continue;

      for (int i = 0; i < names.size(); i++) {
        Author author = graph.authors.get(names.get(i));
        for (int j = i + 1; j < names.size(); j++) {
          Author coauthor = graph.authors.get(names.get(j));
          graph.addEdge(author, coauthor);
        }
      }
    }
  }

  private static void writeGraphToFile(
    Graph graph,
    String outputFile,
    GraphTypes type
  ) throws IOException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
    writer.write(
      "% directed=false\n% weighted=true\n% representation=" +
      type.toString().toLowerCase() +
      "\n*Vertices " +
      graph.authors.size() +
      "\n"
    );

    int count = 0;
    for (Author author : graph.authors.values()) {
      writer.write(count + " " + author.name + "\n");
      count++;
    }
    writer.write("*arcs\n");
    for (Edge edge : graph.edges.values()) {
      writer.write(
        edge.source.index + " " + edge.target.index + " " + edge.weight + "\n"
      );
    }
    System.out.println("Graph generated successfully!");
    writer.close();
  }
}
