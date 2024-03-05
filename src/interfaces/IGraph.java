package interfaces;

import java.util.List;
import share.Edge;

public interface IGraph {
    public void addNode(String label);
    public void addEdge(String from, String to, int weight);
    public void removeNode(String label);
    public void removeEdge(String from, String to);
    public boolean isNeighbor(String from, String to);
    public List<Edge> getNeighbors(String label);
}
