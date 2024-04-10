## Nephila - Java

This is a Java implementation of the Nephila project. The Nephila project is a simple graph lib that allows you to create and manipulate graphs.

### Methods
- `addNode(String label)`: Adiciona um nó ao gráfico com o rótulo especificado.
- `addEdge(String from, String to, int weight)`: Adiciona uma aresta entre dois nós no gráfico com os rótulos especificados e peso.
- `removeNode(String label)`: Remove um nó do gráfico com o rótulo especificado.
- `removeEdge(String from, String to)`: Remove uma aresta entre dois nós no gráfico com os rótulos especificados.
- `isNeighbor(String from, String to)`: Verifica se dois nós no gráfico são vizinhos.
- `getNeighbors(String label)`: Retorna uma lista de arestas que são adjacentes ao nó com o rótulo especificado.
- `updateEdge(String from, String to, int weight)`: Atualiza o peso de uma aresta entre dois nós no gráfico com os rótulos especificados. Se a aresta não existir, ela será criada.
- `getEdgeWeight(String from, String to)`: Obtém o peso de uma aresta entre dois nós no gráfico com os rótulos especificados. Se a aresta não existir, retornará null.
- `getIndegree(String label)`: Obtém o grau de entrada de um nó no gráfico com o rótulo especificado.
- `getOutdegree(String label)`: Obtém o grau de saída de um nó no gráfico com o rótulo especificado.
- `getDegree(String label)`: Obtém o grau de um nó no gráfico com o rótulo especificado.
- `getWarshall()`: Obtém a matriz de Warshall do gráfico.
- `toString()`: Imprime o gráfico em um formato legível por humanos.
- `dfs(String start, String end)`: Obtém o caminho DFS de início ao fim.
- `bfs(String start, String end)`: Obtém o caminho BFS de início ao fim.
- `isEulerian()`: Verifica se o gráfico é euleriano.
- `getDijkstra(String start, String end)`: Obtém o caminho de Dijkstra de início ao fim.
- `getPrim(String start)`: Obtém a árvore de Prim (na forma de gráfico).
- `saveGraph(String filename)`: Salva o gráfico em um arquivo.
- `degreeDistribution(String filename)`: Obtém a distribuição de grau do gráfico e salva em um arquivo.
