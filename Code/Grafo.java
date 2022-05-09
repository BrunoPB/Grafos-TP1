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
    public int calcularExcentricidade(int vertice) {
        // TODO: Criar método para calcular excentricidade de um vértice específico
    }

    /**
     * Busca em Largura
     * 
     * @param verticeInicio Vértice onde irá iniciar a busca em largura
     * @param verticeFinal  Vértice objetivo onde irá terminar a busca
     * @return Caminho da busca em Largura
     */
    public int[][] buscaEmLargura(int verticeInicio, int verticeFinal) {
        // TODO: Criar método de busca em largura
    }

    /**
     * Busca em Profundidade
     * 
     * @param verticeInicio Vértice onde irá iniciar a busca em Profundidade
     * @param verticeFinal  Vértice objetivo onde irá terminar a busca
     * @return Caminho da busca em Profundidade
     */
    public int[][] buscaEmProfundidade(int verticeInicio, int verticeFinal) {
        // TODO: Criar método de busca em profundidade
    }

    /**
     * Método que irá solucionar o problema dos k-centros sem heurística
     * 
     * @return Vetor com a posição ideal para os centros
     */
    public int[] solucao() {
        // TODO: Criar método sem heurística
    }

    /**
     * Método que irá solucionar o problema dos k-centros utilizando uma heurística
     * 
     * @return Vetor com a posição ideal para os centros
     */
    public int[] solucaoHeuristica() {
        // TODO: Criar método com heurística
    }

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
