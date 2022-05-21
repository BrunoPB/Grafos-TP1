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
                this.mat[auxVetII - 1][auxVetI - 1] = this.mat[auxVetI - 1][auxVetII - 1] = valorAresta;
            }

            for (int i = 0; i < this.mat.length; i++) {
                completarComDijkstra(i);
            }

            s.close();
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    /**
     * Método para calcular o valor do raio e do diâmetro do Grafo
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
     * 
     * @param root Vertice que será completado
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
            int edge[] = menorAresta(obterArestaDeCorte(corte), dist);
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
     * Retorna as arestas de corte
     * 
     * @param corte
     * @return Arestas de corte
     */
    public ArrayList<int[]> obterArestaDeCorte(ArrayList<Integer> corte) {
        ArrayList<int[]> arestasCorte = new ArrayList<int[]>(); // array de [v1,v2,c]

        for (int vertice : corte) {
            for (int aresta = 0; aresta < mat.length; aresta++) {
                if (mat[vertice][aresta] > 0 && !corte.contains(aresta)) {
                    int triplet[] = { vertice, aresta, mat[vertice][aresta] };
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
    public int[] menorAresta(ArrayList<int[]> arr, int[] dist) {
        int result[] = arr.get(0);
        for (int[] triplet : arr) {
            if ((result[2] + dist[result[0]]) > (triplet[2] + dist[triplet[0]])) {
                result = triplet;
            }
        }
        return result;
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
        solucao = calcularSolucao(centros);

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
            int edge[] = menorAresta(obterArestaDeCorte(corte), dist);
            // edge[0] = v, edge[1] = w, edge[2] = custo

            if (centros.contains(edge[1])) {
                return dist[edge[0]] + edge[2];
            }

            dist[edge[1]] = dist[edge[0]] + edge[2];
            pred[edge[1]] = edge[0];
            corte.add(edge[1]);
        }

        throw new Exception("Centros não encontrados");
    }

    /**
     * Método para calcular qual o maior raio em relação aos centros
     * 
     * @param centros Centros do vértices
     * @return Retorna um inteiro com a solução
     * @throws Exception
     */
    private int calcularSolucao(Set<Integer> centros) throws Exception {
        int solucao = 0;
        for (int i = 0; i < mat.length; i++) {
            int dist = distanciaAteCentro(i, centros);
            if (dist > solucao) {
                solucao = dist;
            }
        }
        return solucao;
    }

    /**
     * Método que irá solucionar o problema dos k-centros usando força bruta
     * 
     * @return Inteiro com o valor do maior raio em relação aos centros
     */
    public int solucaoBruta() throws Exception {
        int solucao = Integer.MAX_VALUE;
        Set<Integer> centros = new HashSet<Integer>();
        int rep = (int) Math.pow(2, this.mat.length);
        for (int i = 0; i < rep; i++) {
            centros = combinacaoBruta(rep, i);
            if (centros.size() > 0) {
                int s = calcularSolucao(centros);
                if (s < solucao) {
                    solucao = s;
                }
            }
        }
        return solucao;
    }

    /**
     * Método para calcular cada combinação diferente possível
     * 
     * @param rep Numero de combinações diferentes
     * @param i   Variavel de iteração entre combinações
     * @return Uma possível combinação
     */
    private Set<Integer> combinacaoBruta(int rep, int i) {
        String code = Integer.toBinaryString(rep + i).substring(1);
        Set<Integer> combination = new HashSet<Integer>();
        String b = "";
        int counter = 0;
        for (int j = 0; j < code.length(); j++) {
            if (code.charAt(j) == '1') {
                b += j;
                counter++;
            }
        }
        if (counter == this.k) {
            for (int j = 0; j < b.length(); j++) {
                combination.add(Integer.parseInt("" + b.charAt(j)));
            }
        }
        return combination;
    }

    // Getters
    public int[][] getMat() {
        return this.mat;
    }

    public int getK() {
        return this.k;
    }
}