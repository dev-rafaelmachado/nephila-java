// package implementation;

// import implementation.share.Pair;
// import implementation.utils.GraphGenerator;
// import interfaces.IGraph;
// import java.io.IOException;
// import java.util.List;
// import share.Graph;

// public class Main {

//   @SuppressWarnings("unused")
//   private static void generateGraphFromCSV(
//     String inputCSVFile,
//     String outputNETFile
//   ) {
//     try {
//       GraphGenerator.generateGraphFromCSV(inputCSVFile, outputNETFile);
//     } catch (IOException e) {
//       e.printStackTrace();
//     }
//   }

//   public static void main(String[] args) {
//     generateGraphFromCSV(
//       "src/assets/implementation/raw.csv",
//       "src/assets/implementation/graph.net"
//     );
//     IGraph graph = Graph.loadGraph("src/assets/implementation/graph.net");
//     Respondent respondent = new Respondent(graph);
//     System.out.println("Number of nodes: " + graph.getSize());
//     runAllAndSaveAnswersInTxt(respondent);
//   }

//   private static void runAllAndSaveAnswersInTxt(Respondent respondent) {
//     List<Pair<String, String>> result_1 = respondent.getTop10MostProductiveAuthorPairs();
//     String result_1_string = "";
//     for (Pair<String, String> pair : result_1) {
//       result_1_string +=
//         "Top (" +
//         ((int) result_1.indexOf(pair) + 1) +
//         "): " +
//         pair.getFirst() +
//         " - " +
//         pair.getSecond() +
//         "\n";
//     }
//     saveOnTxt(
//       "src/implementation/result/1_top10MostProductiveAuthorPairs.txt",
//       result_1_string
//     );

//     int result_2 = respondent.getNumberOfComponents();
//     saveOnTxt(
//       "src/implementation/result/2_numberOfComponents.txt",
//       String.valueOf(result_2)
//     );

//     // 4. Qual é a distribuição dos graus dos nós da rede? (Salvo em degree_distribution.png)
//     respondent.genDegreeDistribution();

//     List<String> result_5 = respondent.getTop10MostInfluentialAuthorsByDegree();
//     String result_5_string = "";
//     for (String pair : result_5) {
//       result_5_string +=
//         "Top (" + ((int) result_5.indexOf(pair) + 1) + "): " + pair + "\n";
//     }
//     saveOnTxt(
//       "src/implementation/result/3_top10MostInfluentialAuthorsByDegree.txt",
//       result_5_string
//     );

//     List<String> result_4 = respondent.getTop10MostInfluentialAuthorsByBetweenness();
//     String result_4_string = "";
//     for (String pair : result_4) {
//       result_4_string +=
//         "Top (" + ((int) result_4.indexOf(pair) + 1) + "): " + pair + "\n";
//     }
//     saveOnTxt(
//       "src/implementation/result/4_top10MostInfluentialAuthorsByBetweenness.txt",
//       result_4_string
//     );

//     List<String> result_6 = respondent.getTop10MostInfluentialAuthorsByCloseness();
//     String result_6_string = "";
//     for (String pair : result_6) {
//       result_6_string +=
//         "Top (" + ((int) result_6.indexOf(pair) + 1) + "): " + pair + "\n";
//     }
//     saveOnTxt(
//       "src/implementation/result/5_top10MostInfluentialAuthorsByCloseness.txt",
//       result_6_string
//     );

//     List<String> result_7 = respondent.getTop10MostInfluentialAuthorsByEccentricity();
//     String result_7_string = "";
//     for (String pair : result_7) {
//       result_7_string +=
//         "Top (" + ((int) result_7.indexOf(pair) + 1) + "): " + pair + "\n";
//     }
//     saveOnTxt(
//       "src/implementation/result/6_top10MostInfluentialAuthorsByEccentricity.txt",
//       result_7_string
//     );

//     Pair<Double, Double> result_8 = respondent.getDiameterAndRadius();
//     saveOnTxt(
//       "src/implementation/result/7_diameterAndRadius.txt",
//       result_8.toString()
//     );

//     List<Pair<String, String>> result_9 = respondent.getTop10MostRelevantEdgesByBetweenness();
//     String result_9_string = "";
//     for (Pair<String, String> pair : result_9) {
//       result_9_string +=
//         "Top (" +
//         ((int) result_9.indexOf(pair) + 1) +
//         "): " +
//         pair.getFirst() +
//         " - " +
//         pair.getSecond() +
//         "\n";
//     }
//     saveOnTxt(
//       "src/implementation/result/8_top10MostRelevantEdgesByBetweenness.txt",
//       result_9_string
//     );

//     double result_10 = respondent.getAverageGeodesicDistance();
//     saveOnTxt(
//       "src/implementation/result/9_averageGeodesicDistance.txt",
//       String.valueOf(result_10)
//     );

//     List<List<String>> result_11 = respondent.getTopAuthorsInTheFourMainCommunities();
//     String result_11_string = "";
//     for (List<String> list : result_11) {
//       result_11_string +=
//         "# Community " + (result_11.indexOf(list) + 1) + ":\n";
//       for (String author : list) {
//         result_11_string += author + "\n";
//       }
//     }
//     saveOnTxt(
//       "src/implementation/result/10_topAuthorsInTheFourMainCommunities.txt",
//       result_11_string
//     );
//   }

//   private static void saveOnTxt(String path, String content) {
//     try {
//       java.io.FileWriter myWriter = new java.io.FileWriter(path);
//       myWriter.write(content);
//       myWriter.close();
//     } catch (IOException e) {
//       e.printStackTrace();
//     }
//   }
// }

package implementation;

import implementation.benchmark.Runner;
import implementation.questions.Interviewer;
import implementation.questions.Respondent;
import implementation.share.Pair;
import implementation.utils.GraphGenerator;
import interfaces.IGraph;
import java.io.IOException;
import java.util.Optional;
import share.Graph;
import utils.GraphTypes;

public class Main {

  public static void main(String[] args) {
    String inputCSVFile = "src/implementation/assets/raw.csv";
    String outputNETFile = "src/implementation/assets/graph.net";

    Pair<GraphTypes, Optional<Integer>> typeAndLength = getGraphTypeAndLength();
    GraphTypes type = typeAndLength.getFirst();
    Optional<Integer> length = typeAndLength.getSecond();

    generateGraphFromCSV(inputCSVFile, outputNETFile, type, length);

    IGraph graph = Graph.loadGraph(outputNETFile);
    Respondent respondent = new Respondent(graph);
    Runner benchmark = new Runner(graph);

    System.out.println("Number of nodes: " + graph.getSize());
    System.out.println("Number of edges: " + graph.getEdgeSize());

    Interviewer.runAllAndSaveAnswers(respondent);
    benchmark.executeAndSaveAllTests(5);
  }

  @SuppressWarnings("unused")
  private static void generateGraphFromCSV(
    String inputCSVFile,
    String outputNETFile,
    GraphTypes type
  ) {
    generateGraphFromCSV(inputCSVFile, outputNETFile, type, Optional.empty());
  }

  // @SuppressWarnings("unused")
  private static void generateGraphFromCSV(
    String inputCSVFile,
    String outputNETFile,
    GraphTypes type,
    Optional<Integer> length
  ) {
    try {
      GraphGenerator.generateGraphFromCSV(
        inputCSVFile,
        outputNETFile,
        type,
        length
      );
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static Pair<GraphTypes, Optional<Integer>> getGraphTypeAndLength() {
    int optionOne = 0;
    System.out.println("Choose an structure to generate the graph:");
    System.out.println("1. Adjacency Matrix");
    System.out.println("2. Adjacency List");

    try {
      optionOne = System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }

    GraphTypes type = optionOne == 1 ? GraphTypes.MATRIX : GraphTypes.LIST;

    int optionTwo = 0;

    System.out.println(
      "Choose a length for the graph (Number of lines that will be read in CSV):"
    );

    try {
      optionTwo = System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Optional<Integer> length = Optional.of(optionTwo);

    return new Pair<GraphTypes, Optional<Integer>>(type, length);
  }
}
