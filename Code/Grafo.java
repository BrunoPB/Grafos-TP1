import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Classe que representa uma estrutura de Grafo
 */
public class Grafo {
    // Propriedades
    private int[][] mat; // Matriz de Adjacência do Grafo
    private int k; // Número de Centros do Grafo

    // Construtores
    public Grafo(String nomeTxt) {
        carregarGrafo(nomeTxt);
    }

    // Métodos
    /**
     * Método para carregar as informações do .txt e tranforma-las na matriz de
     * adjacência, assim como o número de centros do Grafo
     * 
     * @param nomeTxt Nome do .txt que será lido
     */
    private void carregarGrafo(String nomeTxt) {
        int auxVetI, auxVetII;
        int vertice;
        try {
            String arq = leitor(nomeTxt);
            Scanner s = new Scanner(arq);

            vertice = s.nextInt();
            s.nextInt();
            this.k = s.nextInt();
            this.mat = new int[vertice][vertice];

            // Completa a matriz com -1 e com 0 caso seja o mesmo vertice
            for (int c = 0; c < this.mat.length; c++) {
                for (int z = 0; z < this.mat[c].length; z++) {
                    if (c == z) {
                        this.mat[c][z] = 0;
                    } else {
                        this.mat[c][z] = -1;
                    }
                }
            }

            // Preenche a matriz com os devidos valores
            while (s.hasNext()) {
                auxVetI = s.nextInt();
                auxVetII = s.nextInt();
                int valorAresta = s.nextInt();
                this.mat[auxVetII][auxVetI] = this.mat[auxVetI][auxVetII] = valorAresta;
            }

            for (int i = 0; i < this.mat.length; i++) {
                completarComDijkstra(i);
            }

            s.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Método para ler .txt
     * 
     * @param nomeTxt Nome do arquivo .txt
     * @return Retorna uma string com tudo que está escrito no arquivo
     * @throws IOException
     */
    private static String leitor(String nomeTxt) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(nomeTxt));
        String linha = "";
        String aux = "";
        boolean quitWhile = false;
        while (!quitWhile) {
            if (aux != null) {
                linha += aux + "\n";
            } else {
                quitWhile = true;
            }
            if (!quitWhile) {
                aux = buffRead.readLine();
            }
        }
        buffRead.close();
        return linha;
    }

    /**
     * Método que completa o grafo com valores faltantes
     */
    private void completarComDijkstra(int root) {
        int dist[] = new int[mat.length]; // Distance from root array
        int pred[] = new int[mat.length]; // Predecessor array
        ArrayList<Integer> corte = new ArrayList<Integer>(); // Elementos visitados

        // Inicializar distancias e predecessores
        for (int v = 0; v < mat.length; v++) {
            dist[v] = 65535;
            pred[v] = -1;
        }
        dist[root] = 0;
        corte.add(root);

        for (int i = 1; i < mat.length; i++) {
            int edge[] = menorAresta(obterArestaDeCorte(corte));
            // edge[0] = v, edge[1] = w, edge[2] = custo

            dist[edge[1]] = dist[edge[0]] + edge[2];
            pred[edge[1]] = edge[0];
            corte.add(edge[1]);

            // Atribuindo valor a matriz do Grafo
            if (mat[root][edge[1]] > dist[edge[1]] || mat[root][edge[1]] == -1) {
                mat[root][edge[1]] = mat[edge[1]][root] = dist[edge[1]];
            }
        }
    }

    /**
     * Método para calcular a excentricidade de um vértice específico
     * 
     * @param vertice Número do vértice para se encontrar a excentricidade
     * @return Retorna um int que representa a excentricidade do vértice
     */
    public int calcularExcentricidade(int root) {
        int dist[] = new int[mat.length]; // Distance from root array
        int pred[] = new int[mat.length]; // Predecessor array
        ArrayList<Integer> corte = new ArrayList<Integer>(); // Elementos visitados

        // Inicializar distancias e predecessores
        for (int v = 0; v < mat.length; v++) {
            dist[v] = 65535;
            pred[v] = -1;
        }
        dist[root] = 0;
        corte.add(root);

        for (int i = 1; i < mat.length; i++) {
            int edge[] = menorAresta(obterArestaDeCorte(corte));
            // edge[0] = v, edge[1] = w, edge[2] = custo
            dist[edge[1]] = dist[edge[0]] + edge[2];
            pred[edge[1]] = edge[0];
            corte.add(edge[1]);
        }

        return 0;
    }

    /**
     * Retorna as arestas de corte
     * 
     * @param corte
     * @return Arestas de corte
     */
    public ArrayList<int[]> obterArestaDeCorte(ArrayList<Integer> corte) {
        ArrayList<int[]> arestasCorte = new ArrayList<int[]>(); // array de [v1,v2,c]

        for (int vertice : corte) {
            for (int verticeFora = 0; verticeFora < mat.length; verticeFora++) {
                if (mat[vertice][verticeFora] > 0 && !corte.contains(verticeFora)) {
                    int triplet[] = { vertice, verticeFora, mat[vertice][verticeFora] };
                    arestasCorte.add(triplet);
                }
            }
        }

        return arestasCorte;
    }

    /**
     * Retorna a menor aresta de arestas recebidas
     * 
     * @param arr
     * @return
     */
    public int[] menorAresta(ArrayList<int[]> arr) {
        int result[] = arr.get(0);
        for (int[] triplet : arr) {
            if (result[2] > triplet[2]) {
                result = triplet;
            }
        }
        return result;
    }

    /**
     * Busca em Largura
     * 
     * @param verticeInicio Vértice onde irá iniciar a busca em largura
     * @return Caminho da busca em Largura
     */
    public Map<Integer, Integer> buscaEmLargura(int verticeInicio) {
        // Fila de vertices
        Queue<Integer> Q = new LinkedList<Integer>();
        Q.add(verticeInicio);
        // Dicionario de pais
        Map<Integer, Integer> parents = new HashMap<Integer, Integer>();
        // Dicionario de niveis
        Map<Integer, Integer> levels = new HashMap<Integer, Integer>();
        // Elementos visitados
        Set<Integer> visited = new HashSet<Integer>();

        // BFS
        parents.put(verticeInicio, -1);
        levels.put(verticeInicio, 0);
        while (!Q.isEmpty()) {
            int v = Q.remove();
            if (!visited.contains(verticeInicio)) {
                visited.add(verticeInicio);
            }
            Set<Integer> suc = obtainSuccessors(v);
            for (int vertice : suc) {
                if (!visited.contains(vertice)) {
                    visited.add(vertice);
                    Q.add(vertice);
                    parents.put(vertice, v);
                    levels.put(vertice, levels.get(v) + 1);
                }
            }
        }
        return levels;
    }

    /**
     * Busca em Profundidade
     * 
     * @param verticeInicio Vértice onde irá iniciar a busca em Profundidade
     * @return Caminho da busca em Profundidade
     */
    public Map<Integer, Integer> buscaEmProfundidade(int verticeInicio) {
        // Pilha de vertices
        Stack<Integer> S = new Stack<Integer>();
        S.push(verticeInicio);
        // Dicionario de pais
        Map<Integer, Integer> parents = new HashMap<Integer, Integer>();
        // Elementos visitados
        Set<Integer> visited = new HashSet<Integer>();

        // DFS
        parents.put(verticeInicio, -1);
        while (!S.empty()) {
            int v = S.pop();
            if (!visited.contains(v)) {
                visited.add(v);
                for (int vertice : obtainSuccessors(v)) {
                    if (!visited.contains(vertice)) {
                        S.push(vertice);
                        parents.put(vertice, v);
                    }
                }
            }
        }

        return parents;
    }

    /**
     * Retorna os sucessores do vértice
     * 
     * @param vertice
     * @return Sucessores
     */
    public Set<Integer> obtainSuccessors(int vertice) {
        Set<Integer> sucessores = new HashSet<Integer>();
        for (int i = 0; i < mat.length; i++) {
            if (mat[vertice][i] > 0) {
                sucessores.add(i);
            }
        }
        return sucessores;
    }

    /**
     * Método que irá solucionar aproximadamente o problema dos k-centros com
     * heurística
     * 
     * @return Vetor com a posição ideal para os centros
     */
    public int solucaoHeuristica() throws Exception {
        Set<Integer> centros = new HashSet<Integer>();
        double[] somas = new double[mat.length];
        int solucao = 0;

        // Soma o valor das arestas de cada vértice e armazena as somas em somas
        for (int i = 0; i < mat.length; i++) {
            int soma = 0;
            for (int j = 0; j < mat[i].length; j++) {
                soma += mat[i][j];
            }
            somas[i] = soma;
        }

        // Pega os k-centros (k vértices com a menor soma) e amazena em centros
        for (int i = 0; i < k; i++) {
            int h = menorIndex(somas);
            centros.add(h);
            somas[h] = Double.POSITIVE_INFINITY;
        }

        // Calcula qual o maior raio em relação aos centros
        for (int i = 0; i < mat.length; i++) {
            int dist = distanciaAteCentro(i, centros);
            if (dist > solucao) {
                solucao = dist;
            }
        }

        return solucao;
    }

    /**
     * Método para encontrar o index do menor valor de um array
     * 
     * @param array
     * @return int com o valor do index
     */
    private int menorIndex(double[] array) {
        if (array.length == 0)
            return -1;

        int index = 0;
        double min = array[index];

        for (int i = 1; i < array.length; i++) {
            if (array[i] <= min) {
                min = array[i];
                index = i;
            }
        }
        return index;
    }

    /**
     * Método que calcula a distancia de um vértice até o centro mais próximo dele
     * 
     * @param vertice Vértice inicial
     * @param centros Conjunto de centros do grafo
     * @return int correspondente a distancia de um vértice ao centro mais próximo
     */
    private int distanciaAteCentro(int vertice, Set<Integer> centros) throws Exception {
        if (centros.contains(vertice)) {
            return 0;
        }

        int dist[] = new int[mat.length]; // Distance from root array
        int pred[] = new int[mat.length]; // Predecessor array
        ArrayList<Integer> corte = new ArrayList<Integer>(); // Elementos visitados

        // Inicializar distancias e predecessores
        for (int v = 0; v < mat.length; v++) {
            dist[v] = 65535;
            pred[v] = -1;
        }
        dist[vertice] = 0;
        corte.add(vertice);

        for (int i = 0; i < mat.length; i++) {
            int edge[] = menorAresta(obterArestaDeCorte(corte));
            // edge[0] = v, edge[1] = w, edge[2] = custo

            if (centros.contains(edge[1])) {
                System.out.println("VERTICE PROXIMO: " + edge[0]);
                System.out.println("CENTRO: " + edge[1]);
                System.out.println();
                return dist[edge[0]] + edge[2];
            }

            dist[edge[1]] = dist[edge[0]] + edge[2];
            pred[edge[1]] = edge[0];
            corte.add(edge[1]);
        }

        throw new Exception("Centros não encontrados");
    }

    /**
     * Método que irá solucionar o problema dos k-centros usando força bruta
     * 
     * @return Inteiro com o valor do maior raio em relação aos centros
     */
    // public int solucaoBruta() {
    // TODO: Criar método bruto
    // }

    // Getters
    public int[][] getMat() {
        return this.mat;
    }

    public int getK() {
        return this.k;
    }

    // MÉTODOS DE TESTE TODO: Apagar depois

    /**
     * Método para mostrar o estado atual da matriz
     */
    public void printarMatriz() {
        for (int c = 0; c < this.mat.length; c++) {
            for (int z = 0; z < this.mat[c].length; z++) {
                System.out.print(String.format("%d\t", mat[c][z]));
            }
            System.out.println();
        }
    }

}
