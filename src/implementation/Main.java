package implementation;

import implementation.utils.GraphGenerator;
import interfaces.IGraph;
import java.io.IOException;
import share.Graph;

public class Main {

  @SuppressWarnings("unused")
  private static void generateGraphFromCSV(
    String inputCSVFile,
    String outputNETFile
  ) {
    try {
      GraphGenerator.generateGraphFromCSV(inputCSVFile, outputNETFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    // generateGraphFromCSV(
    //   "src/assets/implementation/raw.csv",
    //   "src/assets/implementation/graph.net"
    // );
    IGraph graph = Graph.loadGraph("src/assets/implementation/graph.net");
    Respondent respondent = new Respondent(graph);
    System.out.println("Number of nodes: " + graph.getSize());

    System.out.println(respondent.getTop10MostInfluentialAuthorsByCloseness());
  }
}
