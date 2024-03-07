package interfaces;

import java.util.List;
import share.Edge;
import share.Node;

public interface INode {
    public String getLabel();
    public void setLabel(String label);
    public void addNeighbor(Node node, int weight);
    public void updateNeighbor(Node node, int weight);
    public void removeNeighbor(Node node);
    public boolean isNeighbor(Node node);
    public List<Edge> getNeighbors();
}