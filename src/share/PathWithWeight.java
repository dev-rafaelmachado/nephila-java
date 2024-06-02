package share;

import java.util.List;

public class PathWithWeight {

  private List<String> path;
  private double totalWeight;

  public PathWithWeight(List<String> path, int totalWeight) {
    this.path = path;
    this.totalWeight = totalWeight;
  }

  public List<String> getPath() {
    return path;
  }

  public double getTotalWeight() {
    return totalWeight;
  }
}
