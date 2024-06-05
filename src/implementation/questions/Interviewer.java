package implementation.questions;

import implementation.share.Pair;
import implementation.utils.FileUtils;
import java.util.List;

public class Interviewer {

  public static void runAllAndSaveAnswers(Respondent respondent) {
    // 1.
    saveTop10MostProductiveAuthorPairs(respondent);
    // 2.
    saveNumberOfComponents(respondent);
    // 3.
    respondent.genDegreeDistribution();
    // 4.
    saveTop10MostInfluentialAuthorsByDegree(respondent);
    // 5.
    saveTop10MostInfluentialAuthorsByBetweenness(respondent);
    // 6.
    saveTop10MostInfluentialAuthorsByCloseness(respondent);
    // 7.
    saveTop10MostInfluentialAuthorsByEccentricity(respondent);
    // 8.
    saveDiameterAndRadius(respondent);
    // 9.
    saveTop10MostRelevantEdgesByBetweenness(respondent);
    // 10.
    saveAverageGeodesicDistance(respondent);
    // 11.
    saveTopAuthorsInTheFourMainCommunities(respondent);
  }

  public static void saveTop10MostProductiveAuthorPairs(Respondent respondent) {
    List<Pair<Pair<String, String>, Integer>> result = respondent.getTop10MostProductiveAuthorPairs();
    String content = formatPairsOfPairs(result);
    FileUtils.saveToFile(
      "src/implementation/questions/result/1_top10MostProductiveAuthorPairs.txt",
      content
    );
  }

  public static void saveNumberOfComponents(Respondent respondent) {
    int result = respondent.getNumberOfComponents();
    FileUtils.saveToFile(
      "src/implementation/questions/result/2_numberOfComponents.txt",
      String.valueOf(result)
    );
  }

  public static void saveTop10MostInfluentialAuthorsByDegree(
    Respondent respondent
  ) {
    List<Pair<String, Double>> result = respondent.getTop10MostInfluentialAuthorsByDegree();
    String content = formatPairs(result);
    FileUtils.saveToFile(
      "src/implementation/questions/result/4_top10MostInfluentialAuthorsByDegree.txt",
      content
    );
  }

  public static void saveTop10MostInfluentialAuthorsByBetweenness(
    Respondent respondent
  ) {
    List<Pair<String, Double>> result = respondent.getTop10MostInfluentialAuthorsByBetweenness();
    String content = formatPairs(result);
    FileUtils.saveToFile(
      "src/implementation/questions/result/5_top10MostInfluentialAuthorsByBetweenness.txt",
      content
    );
  }

  public static void saveTop10MostInfluentialAuthorsByCloseness(
    Respondent respondent
  ) {
    List<Pair<String, Double>> result = respondent.getTop10MostInfluentialAuthorsByCloseness();
    String content = formatPairs(result);
    FileUtils.saveToFile(
      "src/implementation/questions/result/6_top10MostInfluentialAuthorsByCloseness.txt",
      content
    );
  }

  public static void saveTop10MostInfluentialAuthorsByEccentricity(
    Respondent respondent
  ) {
    List<Pair<String, Double>> result = respondent.getTop10MostInfluentialAuthorsByEccentricity();
    String content = formatPairs(result);
    FileUtils.saveToFile(
      "src/implementation/questions/result/7_top10MostInfluentialAuthorsByEccentricity.txt",
      content
    );
  }

  public static void saveDiameterAndRadius(Respondent respondent) {
    Pair<Double, Double> result = respondent.getDiameterAndRadius();
    FileUtils.saveToFile(
      "src/implementation/questions/result/8_diameterAndRadius.txt",
      result.toString()
    );
  }

  public static void saveTop10MostRelevantEdgesByBetweenness(
    Respondent respondent
  ) {
    List<Pair<Pair<String, String>, Double>> result = respondent.getTop10MostRelevantEdgesByBetweenness();
    String content = formatPairsOfPairs(result);
    FileUtils.saveToFile(
      "src/implementation/questions/result/9_top10MostRelevantEdgesByBetweenness.txt",
      content
    );
  }

  public static void saveAverageGeodesicDistance(Respondent respondent) {
    double result = respondent.getAverageGeodesicDistance();
    FileUtils.saveToFile(
      "src/implementation/questions/result/10_averageGeodesicDistance.txt",
      String.valueOf(result)
    );
  }

  public static void saveTopAuthorsInTheFourMainCommunities(
    Respondent respondent
  ) {
    List<List<Pair<String, Double>>> result = respondent.getTopAuthorsInTheFourMainCommunities();
    StringBuilder content = new StringBuilder();
    for (int i = 0; i < result.size(); i++) {
      content.append("# Community ").append(i + 1).append(":\n");
      content.append(formatPairs(result.get(i)));
    }
    FileUtils.saveToFile(
      "src/implementation/questions/result/11_topAuthorsInTheFourMainCommunities.txt",
      content.toString()
    );
  }

  public static <F, S> String formatPairs(List<Pair<F, S>> pairs) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < pairs.size(); i++) {
      Pair<F, S> pair = pairs.get(i);
      sb
        .append("Top (")
        .append(i + 1)
        .append("): ")
        .append(pair.getFirst())
        .append(" - ")
        .append(pair.getSecond())
        .append("\n");
    }
    return sb.toString();
  }

  public static <F, S, D> String formatPairsOfPairs(
    List<Pair<Pair<F, S>, D>> pairs
  ) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < pairs.size(); i++) {
      Pair<Pair<F, S>, D> pair = pairs.get(i);
      sb
        .append("Top (")
        .append(i + 1)
        .append("): ")
        .append(pair.getFirst().getFirst())
        .append(" - ")
        .append(pair.getFirst().getSecond())
        .append(" >> ")
        .append(pair.getSecond())
        .append("\n");
    }
    return sb.toString();
  }

  public static String formatList(List<String> list) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      sb
        .append("Top (")
        .append(i + 1)
        .append("): ")
        .append(list.get(i))
        .append("\n");
    }
    return sb.toString();
  }
}
