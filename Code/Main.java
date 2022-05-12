import java.util.Map;

/**
 * Grupo: Bruno Baêta, Diogo Castro, Felipe Tadeu e João Quintão
 */
/**
 * Classe Main
 */
public class Main {
    public static void main(String[] args) {
        Grafo G = new Grafo("Grafo");

        G.grafoTeste();

        System.out.println("\nBusca em largura");

        for (Map.Entry<Integer,Integer> vertice : G.buscaEmLargura(0).entrySet()) {
            System.out.println("Vertex: " + vertice.getKey() + " Parent: " + vertice.getValue());
        }

        System.out.println("\nBusca em produndidade");

        for (Map.Entry<Integer,Integer> vertice : G.buscaEmProfundidade(0).entrySet()) {
            System.out.println("Vertex: " + vertice.getKey() + " Parent: " + vertice.getValue());
        }
    }
}