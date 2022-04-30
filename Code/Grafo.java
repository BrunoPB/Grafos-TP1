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
