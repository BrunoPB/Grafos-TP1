import java.util.Scanner;

/**
 * Grupo: Bruno Baêta, Diogo Castro, Felipe Tadeu e João Quintão
 */
/**
 * Classe Main
 */
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
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
                        String.format("1. Usar metodo sem heuristica\n2. Usar metodo com heuristica\n0. Sair\n"));
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
        in.close();
    }

    public static void runOne(int heuristica) {
        Scanner in = new Scanner(System.in);
        System.out.println("Digite o nome do arquivo que contem o grafo (inclua '.txt')");
        String txt = in.nextLine();
        Grafo G = new Grafo("./" + txt);
        System.out.println("Digite o valor da solução exata se houver ('0' caso não exista): ");
        int realSolution = in.nextInt();

        in.close();
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
        G.printarMatriz();
        // try {
        // System.out.println(G.solucao());
        // } catch (Exception e) {
        // System.err.println("Error");
        // }
    }
}