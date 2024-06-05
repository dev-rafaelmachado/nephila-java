import interfaces.IGraph;
import share.Graph;
import utils.GraphTypes;

public class App {

  public static IGraph genGraph() {
    System.out.println("Olá, o que deseja fazer?");
    System.out.println("1 - Carregar grafo de um arquivo");
    System.out.println("2 - Criar um grafo");

    int choose;

    do {
      choose = Integer.parseInt(System.console().readLine());
    } while (choose != 1 && choose != 2);

    if (choose == 1) {
      System.out.println("Digite o nome do arquivo: ");
      String filename = System.console().readLine();
      return Graph.loadGraph(filename);
    } else {
      System.out.println("Qual é a estrutura do grafo?");
      System.out.println("1 - Matrix de adjacência");
      System.out.println("2 - Lista de adjacência");

      int graphType;
      do {
        graphType = Integer.parseInt(System.console().readLine());
      } while (choose != 1 && choose != 2);

      System.out.println("O grafo é direcionado?");
      System.out.println("1 - Sim");
      System.out.println("2 - Não");

      int directed;
      do {
        directed = Integer.parseInt(System.console().readLine());
      } while (choose != 1 && choose != 2);

      System.out.println("O grafo é ponderado?");
      System.out.println("1 - Sim");
      System.out.println("2 - Não");

      int weighted;
      do {
        weighted = Integer.parseInt(System.console().readLine());
      } while (choose != 1 && choose != 2);

      switch (graphType) {
        case 1:
          return Graph.createGraph(
            GraphTypes.MATRIX,
            weighted == 1,
            directed == 1
          );
        case 2:
          return Graph.createGraph(
            GraphTypes.LIST,
            weighted == 1,
            directed == 1
          );
      }
    }
    return null;
  }

  public static void operations(IGraph graph) {
    while (true) {
      System.out.println("O que deseja fazer?");
      System.out.println("1 - Adicionar nó");
      System.out.println("2 - Adicionar aresta");
      System.out.println("3 - Remover nó");
      System.out.println("4 - Remover aresta");
      System.out.println("5 - Verificar se é vizinho de um nó");
      System.out.println("6 - Trazer os vizinhos de um nó");
      System.out.println("7 - Atualizar peso de uma aresta");
      System.out.println("8 -  Trazer o peso de um nó");
      System.out.println("9 -  Trazer o indegre de um nó");
      System.out.println("10 -  Trazer o outdegre de um nó");
      System.out.println("11 -  Trazer o grau de um nó");
      System.out.println("12 - Trazer a matriz de Warshall");
      System.out.println("13 - Fazer uma busca em largura");
      System.out.println("14 - Fazer uma busca em profundidade");
      System.out.println("15 - Verificar se é euleriano");
      System.out.println("16 - Trazer o caminho mais curto");
      System.out.println("17 - Trazer a arvore geradora minima");
      System.out.println("18 - Salvar grafo em um arquivo");
      System.out.println("19 - Gerar o gráfico de distribuição de graus");
      System.out.println("0 - Sair");

      int choose = Integer.parseInt(System.console().readLine());

      switch (choose) {
        case 1:
          System.out.println("Digite o label do nó: ");
          String label = System.console().readLine();
          graph.addNode(label);
          break;
        case 2:
          System.out.println("Digite o label do nó de origem: ");
          String from = System.console().readLine();
          System.out.println("Digite o label do nó de destino: ");
          String to = System.console().readLine();
          System.out.println("Digite o peso da aresta: ");
          int weight = Integer.parseInt(System.console().readLine());
          graph.addEdge(from, to, weight);
          break;
        case 3:
          System.out.println("Digite o label do nó: ");
          label = System.console().readLine();
          graph.removeNode(label);
          break;
        case 4:
          System.out.println("Digite o label do nó de origem: ");
          from = System.console().readLine();
          System.out.println("Digite o label do nó de destino: ");
          to = System.console().readLine();
          graph.removeEdge(from, to);
          break;
        case 5:
          System.out.println("Digite o label do nó de origem: ");
          from = System.console().readLine();
          System.out.println("Digite o label do nó de destino: ");
          to = System.console().readLine();
          System.out.println(graph.isNeighbor(from, to));
          break;
        case 6:
          System.out.println("Digite o label do nó: ");
          label = System.console().readLine();
          System.out.println(graph.getNeighbors(label));
          break;
        case 7:
          System.out.println("Digite o label do nó de origem: ");
          from = System.console().readLine();
          System.out.println("Digite o label do nó de destino: ");
          to = System.console().readLine();
          System.out.println("Digite o peso da aresta: ");
          weight = Integer.parseInt(System.console().readLine());
          graph.updateEdge(from, to, weight);
          break;
        case 8:
          System.out.println("Digite o label do nó de origem: ");
          from = System.console().readLine();
          System.out.println("Digite o label do nó de destino: ");
          to = System.console().readLine();
          System.out.println(graph.getEdgeWeight(from, to));
          break;
        case 9:
          System.out.println("Digite o label do nó: ");
          label = System.console().readLine();
          System.out.println(graph.getIndegre(label));
          break;
        case 10:
          System.out.println("Digite o label do nó: ");
          label = System.console().readLine();
          System.out.println(graph.getOutdegre(label));
          break;
        case 11:
          System.out.println("Digite o label do nó: ");
          label = System.console().readLine();
          System.out.println(graph.getDegree(label));
          break;
        case 12:
          System.out.println(graph.getWarshall());
          break;
        case 13:
          System.out.println("Digite o label do nó de origem: ");
          from = System.console().readLine();
          System.out.println("Digite o label do nó de destino: ");
          to = System.console().readLine();
          System.out.println(graph.bfs(from, to));
          break;
        case 14:
          System.out.println("Digite o label do nó de origem: ");
          from = System.console().readLine();
          System.out.println("Digite o label do nó de destino: ");
          to = System.console().readLine();
          System.out.println(graph.dfs(from, to));
          break;
        case 15:
          System.out.println(graph.isEulerian());
          break;
        case 16:
          System.out.println("Digite o label do nó de origem: ");
          from = System.console().readLine();
          System.out.println("Digite o label do nó de destino: ");
          to = System.console().readLine();
          System.out.println(graph.getDijkstra(from, to));
          break;
        case 17:
          System.out.println("Digite o label do nó de origem: ");
          from = System.console().readLine();
          System.out.println(graph.getPrim(from));
          break;
        case 18:
          System.out.println("Digite o nome do arquivo: ");
          String filename = System.console().readLine();
          graph.saveGraph(filename);
          break;
        case 19:
          System.out.println("Digite o nome do arquivo: ");
          String filenameChart = System.console().readLine();
          graph.degreeDistribution(filenameChart);
          break;
        case 0:
          return;
      }
    }
  }

  public static void main(String[] args) throws Exception {
    IGraph graph = genGraph();
    operations(graph);
  }
}
