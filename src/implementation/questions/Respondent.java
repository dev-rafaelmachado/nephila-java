package implementation.questions;

import implementation.share.Pair;
import interfaces.IGraph;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import share.ExternalEdge;

public class Respondent {

  private IGraph graph;

  public Respondent(IGraph graph) {
    this.graph = graph;
  }

  /**
   *  1. Quais pares de autores são os mais produtivos dentro da rede? Elenque os 10 pares de autores mais produtivos da rede.
   */
  public List<Pair<Pair<String, String>, Integer>> getTop10MostProductiveAuthorPairs() {
    // Get the most weighted edges
    List<ExternalEdge> result = graph.getWeightedOfAllEdges();

    List<Pair<Pair<String, String>, Integer>> top10Pairs = new ArrayList<>();

    result.sort(Comparator.comparing(ExternalEdge::getWeight).reversed());

    for (int i = 0; i < Math.min(result.size(), 10); i++) {
      ExternalEdge edge = result.get(i);
      top10Pairs.add(
        new Pair<>(
          new Pair<>(
            edge.getFromNode().getLabel(),
            edge.getToNode().getLabel()
          ),
          edge.getWeight()
        )
      );
    }

    return top10Pairs;
  }

  /**
   * 2. Quantas componentes o grafo possui? O que isso representa?
   */
  public int getNumberOfComponents() {
    return graph.getConnectedComponents().size();
  }

  /**
   * 3. Qual é a distribuição dos graus dos nós da rede? Essa distribuição demonstra comportamento de uma rede complexa?
   */
  public void genDegreeDistribution() {
    graph.degreeDistribution(
      "src/implementation/questions/result/3_degreeDistribution.png"
    );
  }

  /**
   *4.  Quais são os 10 autores mais influentes perante a métrica de centralidade de grau? O que essa métrica representa nesse contexto?
   */
  public List<Pair<String, Double>> getTop10MostInfluentialAuthorsByDegree() {
    Map<String, Double> result = graph.getDegreeCentralityOfAllNodes();

    List<Map.Entry<String, Double>> entryList = new ArrayList<>(
      result.entrySet()
    );

    entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    List<Pair<String, Double>> top10Authors = new ArrayList<>();

    for (int i = 0; i < Math.min(entryList.size(), 10); i++) {
      top10Authors.add(
        new Pair<>(entryList.get(i).getKey(), entryList.get(i).getValue())
      );
    }

    return top10Authors;
  }

  /**
   * 5. Quais são os 10 autores mais influentes perante a métrica de centralidade de intermediação? O que essa métrica representa nesse contexto?
   */
  public List<Pair<String, Double>> getTop10MostInfluentialAuthorsByBetweenness() {
    Map<String, Double> result = graph.getBetweennessCentralityOfAllNodes(
      Optional.of(true)
    );

    List<Map.Entry<String, Double>> entryList = new ArrayList<>(
      result.entrySet()
    );

    entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    List<Pair<String, Double>> top10Authors = new ArrayList<>();
    for (int i = 0; i < Math.min(entryList.size(), 10); i++) {
      top10Authors.add(
        new Pair<>(entryList.get(i).getKey(), entryList.get(i).getValue())
      );
    }

    return top10Authors;
  }

  /**
   * 6. Quais são os 10 autores mais influentes perante a métrica de centralidade de proximidade? O que essa métrica representa nesse contexto?
   */
  public List<Pair<String, Double>> getTop10MostInfluentialAuthorsByCloseness() {
    Map<String, Double> result = graph.getClosenessCentralityOfAllNodes(
      Optional.of(true)
    );
    List<Map.Entry<String, Double>> entryList = new ArrayList<>(
      result.entrySet()
    );

    entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    List<Pair<String, Double>> top10Authors = new ArrayList<>();
    for (int i = 0; i < Math.min(entryList.size(), 10); i++) {
      top10Authors.add(
        new Pair<>(entryList.get(i).getKey(), entryList.get(i).getValue())
      );
    }

    return top10Authors;
  }

  /**
   * 7. Quais são os 10 autores mais influentes perante a métrica de centralidade de excentricidade? O que essa métrica representa nesse contexto?
   */
  public List<Pair<String, Double>> getTop10MostInfluentialAuthorsByEccentricity() {
    // Since the graph is not strongly connected, we take the largest component
    List<IGraph> resultComponents = graph.getConnectedComponents();
    IGraph BiggerComponent = resultComponents.get(0);
    for (IGraph component : resultComponents) {
      if (component.getSize() > BiggerComponent.getSize()) {
        BiggerComponent = component;
      }
    }

    Map<String, Double> result = BiggerComponent.getEccentricityOfAllNodes(
      Optional.of(true)
    );

    List<Map.Entry<String, Double>> entryList = new ArrayList<>(
      result.entrySet()
    );

    entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    List<Pair<String, Double>> top10Authors = new ArrayList<>();
    for (int i = 0; i < Math.min(entryList.size(), 10); i++) {
      top10Authors.add(
        new Pair<>(entryList.get(i).getKey(), entryList.get(i).getValue())
      );
    }

    return top10Authors;
  }

  /**
   * 8. Calcule o diâmetro e o raio do grafo. O que essas métricas representam nesse contexto?
   */
  public Pair<Double, Double> getDiameterAndRadius() {
    // Since the graph is not strongly connected, we take the largest component
    List<IGraph> resultComponents = graph.getConnectedComponents();
    IGraph BiggerComponent = resultComponents.get(0);
    for (IGraph component : resultComponents) {
      if (component.getSize() > BiggerComponent.getSize()) {
        BiggerComponent = component;
      }
    }

    double diameter = BiggerComponent.getDiameter(Optional.of(true));
    double radius = BiggerComponent.getRadius(Optional.of(true));

    return new Pair<>(diameter, radius);
  }

  /**
   * 9. Quais são as 10 arestas mais relevantes no grafo perante a métrica de centralidade de intermediação? Dentre essas arestas, há algum comportamento que se destaca?
   */
  public List<Pair<Pair<String, String>, Double>> getTop10MostRelevantEdgesByBetweenness() {
    Map<ExternalEdge, Double> result = graph.getEdgeBetweennessOfAllEdges(
      Optional.of(true)
    );
    List<Map.Entry<ExternalEdge, Double>> entryList = new ArrayList<>(
      result.entrySet()
    );

    entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

    List<Pair<Pair<String, String>, Double>> top10Edges = new ArrayList<>();
    for (int i = 0; i < Math.min(entryList.size(), 10); i++) {
      ExternalEdge edge = entryList.get(i).getKey();
      top10Edges.add(
        new Pair<>(
          new Pair<>(
            edge.getFromNode().getLabel(),
            edge.getToNode().getLabel()
          ),
          entryList.get(i).getValue()
        )
      );
    }

    return top10Edges;
  }

  /**
   * 10. Qual é a média das distâncias geodésicas da maior componente do grafo? O que essa métrica representa nesse contexto?
   */
  public double getAverageGeodesicDistance() {
    List<IGraph> components = graph.getConnectedComponents();
    IGraph largestComponent = components.get(0);
    for (IGraph component : components) {
      if (component.getSize() > largestComponent.getSize()) {
        largestComponent = component;
      }
    }

    return largestComponent.getAverageGeodesicDistance();
  }

  /**
   * 11.  Dentro do grafo, encontre a componente com a maior quantidade de vértices. Dentro desta componente, execute o algoritmo de Girvan-Newman e encontre as quatro principais comunidades. Para cada comunidade, identifique e discuta os autores mais significativos de acordo com as métricas que julgar mais adequado.
   */
  public List<List<Pair<String, Double>>> getTopAuthorsInTheFourMainCommunities() {
    List<IGraph> communities = graph.getCommunities(4);

    communities.sort(Comparator.comparing(IGraph::getSize).reversed());
    communities = communities.subList(0, 4);

    List<List<Pair<String, Double>>> topAuthorsInCommunities = new ArrayList<>();

    for (IGraph community : communities) {
      Map<String, Double> result = community.getDegreeCentralityOfAllNodes();

      List<Map.Entry<String, Double>> entryList = new ArrayList<>(
        result.entrySet()
      );

      entryList.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

      List<Pair<String, Double>> topAuthors = new ArrayList<>();
      for (int i = 0; i < Math.min(entryList.size(), 10); i++) {
        topAuthors.add(
          new Pair<>(entryList.get(i).getKey(), entryList.get(i).getValue())
        );
      }

      topAuthorsInCommunities.add(topAuthors);
    }

    return topAuthorsInCommunities;
  }
}
