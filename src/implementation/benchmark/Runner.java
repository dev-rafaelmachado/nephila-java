package implementation.benchmark;

import implementation.share.Pair;
import implementation.utils.FileUtils;
import interfaces.IGraph;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import share.Graph;

public class Runner {

  private IGraph graph;
  private Timer timer = new Timer();

  public Runner(IGraph graph) {
    this.graph = graph;
  }

  public Long runInsertAllNodesTest() {
    List<String> nodes = new ArrayList<>();
    List<String> data = Graph.readFile("src/implementation/assets/graph.net");
    IGraph myGraph = Graph.createGraph(this.graph.getType(), false, false);
    Long time = timer.measureTime(() -> Graph.insertNodes(data, myGraph, nodes)
    );

    System.out.println("Number of nodes: " + myGraph.getSize());

    System.out.println("time: " + time);

    return time;
  }

  public Long runComponentsIdentificationTest() {
    Long time = timer.measureTime(() -> graph.getConnectedComponents());

    System.out.println(
      "Number of components: " + graph.getConnectedComponents().size()
    );

    System.out.println("time: " + time);

    return time;
  }

  public Long runDegreeDistributionTest() {
    Long time = timer.measureTime(() -> graph.degreeDistribution(""));

    System.out.println("time: " + time);

    return time;
  }

  public Long runDegreeCentralityTest() {
    Long time = timer.measureTime(() -> graph.getDegreeCentralityOfAllNodes());

    System.out.println("time: " + time);

    return time;
  }

  public Long runBetweennessCentralityTest() {
    Long time = timer.measureTime(() ->
      graph.getBetweennessCentralityOfAllNodes(Optional.of(true))
    );

    System.out.println("time: " + time);

    return time;
  }

  public Long runClosenessCentralityTest() {
    Long time = timer.measureTime(() ->
      graph.getClosenessCentralityOfAllNodes(Optional.of(true))
    );

    System.out.println("time: " + time);

    return time;
  }

  public Long runEccentricityTest() {
    List<IGraph> resultComponents = graph.getConnectedComponents();
    IGraph BiggerComponent = resultComponents.get(0);
    for (IGraph component : resultComponents) {
      if (component.getSize() > BiggerComponent.getSize()) {
        BiggerComponent = component;
      }
    }

    final IGraph finalBiggerComponent = BiggerComponent;

    Long time = timer.measureTime(() ->
      finalBiggerComponent.getEccentricityOfAllNodes(Optional.of(true))
    );

    System.out.println("time: " + time);

    return time;
  }

  public Long runDiameterTest() {
    List<IGraph> resultComponents = graph.getConnectedComponents();
    IGraph BiggerComponent = resultComponents.get(0);
    for (IGraph component : resultComponents) {
      if (component.getSize() > BiggerComponent.getSize()) {
        BiggerComponent = component;
      }
    }

    final IGraph finalBiggerComponent = BiggerComponent;

    Long time = timer.measureTime(() ->
      finalBiggerComponent.getDiameter(Optional.of(true))
    );

    System.out.println("time: " + time);

    return time;
  }

  public Long runRadiusTest() {
    List<IGraph> resultComponents = graph.getConnectedComponents();
    IGraph BiggerComponent = resultComponents.get(0);
    for (IGraph component : resultComponents) {
      if (component.getSize() > BiggerComponent.getSize()) {
        BiggerComponent = component;
      }
    }

    final IGraph finalBiggerComponent = BiggerComponent;

    Long time = timer.measureTime(() ->
      finalBiggerComponent.getRadius(Optional.of(true))
    );

    System.out.println("time: " + time);

    return time;
  }

  public Long runGeodesicDistanceTest() {
    Long time = timer.measureTime(() -> graph.getAverageGeodesicDistance());

    System.out.println("time: " + time);

    return time;
  }

  public Long runCommunitiesIdentificationTest() {
    List<IGraph> resultComponents = graph.getConnectedComponents();
    IGraph BiggerComponent = resultComponents.get(0);
    for (IGraph component : resultComponents) {
      if (component.getSize() > BiggerComponent.getSize()) {
        BiggerComponent = component;
      }
    }

    final IGraph finalBiggerComponent = BiggerComponent;

    Long time = timer.measureTime(() -> finalBiggerComponent.getCommunities(4));

    System.out.println("time: " + time);

    return time;
  }

  public Pair<Long, Long> getAverageTimeAndStandardDeviation(
    int times,
    Supplier<Long> test
  ) {
    Long totalTime = 0L;
    List<Long> timesList = new ArrayList<>();
    for (int i = 0; i < times; i++) {
      long result = test.get();
      totalTime += result;
      timesList.add(result);
    }

    Long averageTime = totalTime / times;
    Long standardDeviation = calculateStandardDeviation(timesList, averageTime);

    return new Pair<>(averageTime, standardDeviation);
  }

  private Long calculateStandardDeviation(List<Long> times, Long averageTime) {
    Long sum = 0L;
    for (Long time : times) {
      sum += (long) Math.pow(time - averageTime, 2);
    }

    return (long) Math.sqrt(sum / times.size());
  }

  // ---

  public void executeAndSaveAllTests(int times) {
    Pair<Long, Long> insertAllNodes = getAverageTimeAndStandardDeviation(
      times,
      this::runInsertAllNodesTest
    );
    Pair<Long, Long> componentsIdentification = getAverageTimeAndStandardDeviation(
      times,
      this::runComponentsIdentificationTest
    );
    Pair<Long, Long> degreeDistribution = getAverageTimeAndStandardDeviation(
      times,
      this::runDegreeDistributionTest
    );
    Pair<Long, Long> degreeCentrality = getAverageTimeAndStandardDeviation(
      times,
      this::runDegreeCentralityTest
    );
    // Pair<Long, Long> betweennessCentrality = getAverageTimeAndStandardDeviation(
    //   times,
    //   this::runBetweennessCentralityTest
    // );
    Pair<Long, Long> closenessCentrality = getAverageTimeAndStandardDeviation(
      times,
      this::runClosenessCentralityTest
    );
    Pair<Long, Long> eccentricity = getAverageTimeAndStandardDeviation(
      times,
      this::runEccentricityTest
    );
    Pair<Long, Long> diameter = getAverageTimeAndStandardDeviation(
      times,
      this::runDiameterTest
    );
    Pair<Long, Long> radius = getAverageTimeAndStandardDeviation(
      times,
      this::runRadiusTest
    );
    Pair<Long, Long> geodesicDistance = getAverageTimeAndStandardDeviation(
      times,
      this::runGeodesicDistanceTest
    );
    // Pair<Long, Long> communitiesIdentification = getAverageTimeAndStandardDeviation(
    //   times,
    //   this::runCommunitiesIdentificationTest
    // );

    saveOnTxt(
      "Insert All Nodes",
      insertAllNodes,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/1_insertAllNodes.txt"
    );

    saveOnTxt(
      "Components Identification",
      componentsIdentification,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/2_componentsIdentification.txt"
    );

    saveOnTxt(
      "Degree Distribution",
      degreeDistribution,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/3_degreeDistribution.txt"
    );

    saveOnTxt(
      "Degree Centrality",
      degreeCentrality,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/4_degreeCentrality.txt"
    );

    // saveOnTxt(
    //   "Betweenness Centrality",
    //   betweennessCentrality,
    //   "src/implementation/benchmark/result/" +
    //   graph.getType().toString().toLowerCase() +
    //   "/5_betweennessCentrality.txt"
    // );

    saveOnTxt(
      "Closeness Centrality",
      closenessCentrality,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/6_closenessCentrality.txt"
    );

    saveOnTxt(
      "Eccentricity",
      eccentricity,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/7_eccentricity.txt"
    );

    saveOnTxt(
      "Diameter",
      diameter,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/8_diameter.txt"
    );

    saveOnTxt(
      "Radius",
      radius,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/9_radius.txt"
    );

    saveOnTxt(
      "Geodesic Distance",
      geodesicDistance,
      "src/implementation/benchmark/result/" +
      graph.getType().toString().toLowerCase() +
      "/10_geodesicDistance.txt"
    );

    // saveOnTxt(
    //   "Communities Identification",
    //   communitiesIdentification,
    //   "src/implementation/benchmark/result/" +
    //   graph.getType().toString().toLowerCase() +
    //   "/11_communitiesIdentification.txt"
    // );

    System.out.println("All tests executed and saved successfully!");
  }

  private void saveOnTxt(String title, Pair<Long, Long> content, String path) {
    StringBuilder sb = new StringBuilder();
    sb.append("# " + title).append("\n");
    sb.append("Average: " + content.getFirst()).append("\n");
    sb.append("Standard Deviation: " + content.getSecond()).append("\n");

    FileUtils.saveToFile(path, sb.toString());
  }
}
