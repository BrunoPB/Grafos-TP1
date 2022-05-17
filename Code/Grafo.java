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
        int auxVetI, auxVetII;
        int vertice;
        try {
            String arq = leitor(nomeTxt);
            Scanner s = new Scanner(arq);

            vertice = s.nextInt();
            s.nextInt();
            this.k = s.nextInt();
            this.mat = new int[vertice + 1][vertice + 1];

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

            while (s.hasNext()) {
                auxVetI = s.nextInt();
                auxVetII = s.nextInt();
                int valorAresta = s.nextInt();
                this.mat[auxVetII][auxVetI] = this.mat[auxVetI][auxVetII] = valorAresta;
            }

            completarComDijkstra();

            s.close();
        } catch (Exception e) {
            System.out.println("ERROR");
        }
    }

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

    private static void completarComDijkstra() {

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
    // // TODO: Criar método para calcular excentricidade de um vértice específico
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
     * Método que irá solucionar aproximadamente o problema dos k-centros sem
     * heurística
     * 
     * @return Vetor com a posição ideal para os centros
     */
    public int solucao() {
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
            // TODO: Calcular solução usando o Dijkstra do Diogo
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
     * Método que irá solucionar o problema dos k-centros utilizando uma heurística
     * 
     * @return Vetor com a posição ideal para os centros
     */
    // public int solucaoHeuristica() {
    // TODO: Criar método com heurística
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

    // MÉTODOS DE TESTE TODO: Apagar depois
    /**
     * Metodo para criar grafo para testes
     */
    public void grafoTeste() {
        int grafo[][] = { { -1, 3, 2, -1, -1, -1 },
                { 3, -1, 4, 2, 3, -1 },
                { 2, 4, -1, -1, -1, 8 },
                { -1, 2, -1, -1, 7, -1 },
                { -1, 3, -1, 7, -1, 4 },
                { -1, -1, 8, -1, 4, -1 }
        };

        mat = grafo;
    }

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
