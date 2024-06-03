
### Perguntas

1. Quais pares de autores são os mais produtivos dentro da rede? Elenque os 10 pares de autores mais produtivos da rede.
**Método:** Get Edge Betweenness Centrality (all -> top10)
**O que representa:** Os pares de pesquisadores que mais publicam juntos

2. Quantas componentes o grafo possui? O que isso representa?
**Método:** Get Components
**O que representa:** Comunidades de pesquisadores e/ou pessoas que pesquisam um mesmo assunto 

3. Qual é a distribuição dos graus dos nós da rede? Essa distribuição demonstra comportamento de uma rede complexa?
**Método:** Degree Distribuition
**Resposta:**: ?

4. Quais são os 10 autores mais influentes perante a métrica de centralidade de grau? O que essa métrica representa nesse contexto?
**Método:** Degree Centrality (all -> top10)
**O que representa:** Os pesquisadores que mais publicam com outros pesquisadores

5. Quais são os 10 autores mais influentes perante a métrica de centralidade de intermediação? O que essa métrica representa nesse contexto?
**Método:** Betweenness Centrality (all -> top10)
**O que representa:** Os pesquisadores que mais intermeiam a comunicação entre outros pesquisadores

6. Quais são os 10 autores mais influentes perante a métrica de centralidade de proximidade? O que essa métrica representa nesse contexto?
**Método:** Closeness Centrality (all -> top10)
**O que representa:** Os pesquisadores que estão mais próximos de todos os outros pesquisadores, ou seja, os que tem a menor distância média para todos os outros pesquisadores

7. Quais são os 10 autores mais influentes perante a métrica de centralidade de excentricidade? O que essa métrica representa nesse contexto?
**Método:** Eccentricity (all -> top10)
**O que representa:** Os pesquisadores que estão mais distantes dos outros pesquisadores

8. Calcule o diâmetro e o raio do grafo. O que essas métricas representam nesse contexto?
**Método:** Diameter, Radius
**O que representa:** O diâmetro é a maior distância entre dois pesquisadores e o raio...

9. Quais são as 10 arestas mais relevantes no grafo perante a métrica de centralidade de intermediação? Dentre essas arestas, há algum comportamento que se destaca?
**Método:** Edge Betweenness Centrality (edges -> top10)

10.  Qual é a média das distâncias geodésicas da maior componente do grafo? O que essa métrica representa nesse contexto?
**Método:** GetComponents -> Average Geodesic Distance
**O que representa:** A média das distâncias entre os pesquisadores da maior comunidade

11.  Dentro do grafo, encontre a componente com a maior quantidade de vértices. Dentro desta componente, execute o algoritmo de Girvan-Newman e encontre as quatro principais comunidades. Para cada comunidade, identifique e discuta os autores mais significativos de acordo com as métricas que julgar mais adequado.