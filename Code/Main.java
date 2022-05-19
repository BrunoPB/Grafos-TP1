import java.util.Scanner;

/**
 * Grupo: Bruno Baêta, Diogo Castro, Felipe Tadeu e João Quintão
 */
/**
 * Classe Main
 */
public class Main {
    public static Scanner in = new Scanner(System.in);

    public static void main(String[] args) {
        boolean quit = false;
        while (!quit) {
            System.out
                    .print(String
                            .format("Menu:\n1. Ler arquivo de grafo especifico\n2. Ler todos os arquivos\n3. Debug\n0. Sair\n"));
            int r1 = in.nextInt();
            if (r1 == 3) {
                debug();
            } else if (r1 != 1 && r1 != 2) {
                quit = true;
            } else {
                System.out.print(
                        String.format("1. Usar metodo exato\n2. Usar metodo aproximado\n0. Sair\n"));
                int r2 = in.nextInt();
                if (r1 != 1 && r1 != 2) {
                    quit = true;
                } else {
                    if (r1 == 1) {
                        runOne(r2);
                    } else {
                        runAll(r2);
                    }
                }
            }
        }
    }

    public static void runOne(int aprox) {
        System.out.println("Digite o nome do arquivo que contem o grafo (inclua '.txt')");
        in.nextLine();
        String txt = in.nextLine();
        Grafo G = new Grafo("./" + txt);

        int result;
        try {
            if (aprox == 2) {
                System.out.println("Digite o valor da solucao exata se houver ('-1' caso nao exista): ");
                int realSolution = in.nextInt();
                result = G.solucaoHeuristica();
                System.out.println("Solucao aproximada: " + result);
                if (realSolution > -1) {
                    double percentage = ((result * 100) / realSolution) - 100;
                    System.out.println(
                            "Porcentagem de erro em relacao a solucao de " + realSolution + ": " + percentage + "%");
                }
            } else {
                result = G.solucaoBruta();
                System.out.println("Solucao exata: " + result);
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }

    public static void runAll(int heuristica) {
        int nArquivos = 40;
        for (int i = 1; i < nArquivos + 1; i++) {
            String txt = "./Dados/pmed" + i + ".txt";
            Grafo G = new Grafo(txt);
            // Calcular soluções
        }
    }

    public static void debug() {
        Grafo G = new Grafo("GrafoTeste.txt");
        try {
            System.out.println(G.solucaoBruta());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}