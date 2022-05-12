import java.util.*;

/**
 * Classe que representa uma estrutura de Grafo
 */
public class Grafo {
    // Propriedades
    private int[][] mat; // Matriz de Adjacência do Grafo
    private int raio; // Raio do Grafo
    private int diametro; // Diâmetro do Grafo
    private int k; // Número de Centros do Grafo

    // Construtores
    public Grafo(String nomeTxt) {
        carregarGrafo(nomeTxt);
        calcularRaioEDiametro(this.mat);
    }

    // Métodos
    /**
     * Método para carregar as informações do .txt e tranforma-las na matriz de
     * adjacência, assim como o número de centros do Grafo
     * 
     * @param nomeTxt Nome do .txt que será lido
     */
    private void carregarGrafo(String nomeTxt) {
        // TODO: Criar método para ler .txt e gerar matriz adjacente
    }

    /**
     * Metodo para criar grafo para testes
     */
    public void grafoTeste(){
        int grafo[][] = {{-1, 3, 2,-1,-1,-1},
                         { 3,-1, 4, 2, 3,-1},
                         { 2, 4,-1,-1,-1, 8},
                         {-1, 2,-1,-1, 7,-1},
                         {-1, 3,-1, 7,-1, 4},
                         {-1,-1, 8,-1, 4,-1}
                        };

        mat = grafo;        
    }

    /**
     * Método para calcular o valor do raio e do diâmetro do Grafo
     * 
     * @param mat Matriz de adjacência do grafo
     */
    private void calcularRaioEDiametro(int[][] mat) {
        // TODO: Criar método para calcular o raio
    }

    /**
     * Método para calcular a excentricidade de um vértice específico
     * 
     * @param vertice Número do vértice para se encontrar a excentricidade
     * @return Retorna um int que representa a excentricidade do vértice
     */
    // public int calcularExcentricidade(int vertice) {
    //     // TODO: Criar método para calcular excentricidade de um vértice específico
    // }

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
        // Elementos visitados
        Set<Integer> visited = new HashSet<Integer>();

        // BFS
        parents.put(verticeInicio, -1);
        while (!Q.isEmpty()) {
            int v = Q.remove();
            if (!visited.contains(v)) {
                visited.add(v);
                for (int vertice : obtainSuccessors(v)) {
                    if (!visited.contains(vertice)) {
                        Q.add(vertice);
                        parents.put(vertice, v);
                    }
                }
            }
        }

        return parents;
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
     * Método que irá solucionar aproximadamente o problema dos k-centros sem
     * heurística
     * 
     * @return Vetor com a posição ideal para os centros
     */
    public Set<Double> solucao() {
        Set<Double> centros = new HashSet<Double>();
        double[] somas = new double[mat.length];

        for (int i = 0; i < mat.length; i++) {
            int soma = 0;
            for (int j = 0; j < mat[i].length; j++) {
                soma += mat[i][j];
            }
            somas[i] = soma;
        }

        for (int i = 0; i < k; i++) {
            int h = menorIndex(somas);
            centros.add(somas[h]);
            somas[h] = Double.POSITIVE_INFINITY;
        }

        return centros;
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
     * Método que irá solucionar o problema dos k-centros utilizando uma heurística
     * 
     * @return Vetor com a posição ideal para os centros
     */
    // public Set<Double> solucaoHeuristica() {
    //     // TODO: Criar método com heurística
    // }

    // Getters
    public int[][] getMat() {
        return this.mat;
    }

    public int getRaio() {
        return this.raio;
    }

    public int getDiametro() {
        return this.diametro;
    }

    public int getK() {
        return this.k;
    }

}
