package implementation.utils;

import java.io.*;
import java.util.*;

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

  public static void generateGraphFromCSV(String inputFile, String outputFile)
    throws IOException {
    Graph graph = new Graph();
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    String line = reader.readLine(); // skip header
    int index = 0;

    while ((line = reader.readLine()) != null) {
      String[] fields = line.split(",");
      List<String> names = new ArrayList<>();
      for (int i = 2; i < fields.length; i++) {
        String rawName = fields[i];
        rawName = rawName.replaceAll("[\\[\\]\\(\\)\\.,'\"]", "");
        rawName = rawName.replace(" ", "_");
        names.add(rawName);
      }

      if (names.size() < 2) continue;

      for (int i = 0; i < names.size(); i++) {
        Author author = graph.authors.get(names.get(i));
        if (author == null) {
          author = new Author(names.get(i), index++);
          graph.authors.put(names.get(i), author);
        }
        for (int j = i + 1; j < names.size(); j++) {
          Author coauthor = graph.authors.get(names.get(j));
          if (coauthor == null) {
            coauthor = new Author(names.get(j), index++);
            graph.authors.put(names.get(j), coauthor);
          }
          graph.addEdge(author, coauthor);
        }
      }
    }
    reader.close();

    BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
    writer.write(
      "% directed=false\n% weighted=true\n% representation=list\n*Vertices " +
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

  public static void main(String[] args) {
    try {
      generateGraphFromCSV("input.csv", "output.net");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
