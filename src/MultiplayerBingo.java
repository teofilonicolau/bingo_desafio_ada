import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MultiplayerBingo {

    private static String[] jogadores;  // Adicionando uma variável de classe para armazenar os jogadores

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem-vindo ao Multiplayer Bingo!");
        System.out.println("Opções de comando: ");
        System.out.println("1. Iniciar jogo");
        System.out.println("2. Sair");

        int opcao = scanner.nextInt();

        if (opcao == 1) {
            jogadores = obterJogadores();  // Inicializando a variável de classe
            jogarBingo();
        } else {
            System.out.println("Jogo encerrado. Até mais!");
        }
    }

    private static void jogarBingo() {
        Scanner scanner = new Scanner(System.in);

        String[][] cartelas = gerarCartelas(jogadores.length);
        int[] numerosSorteados = new int[75];
        Arrays.fill(numerosSorteados, -1);
        int rodada = 1;

        while (!bingo(cartelas)) {
            System.out.println("Rodada " + rodada);
            exibirCartelas(cartelas);

            System.out.println("Opções de comando: ");
            System.out.println("1. Sorteio automático");
            System.out.println("2. Sorteio manual");

            int opcao = scanner.nextInt();

            if (opcao == 1) {
                sortearAutomatico(numerosSorteados);
            } else if (opcao == 2) {
                sortearManual(numerosSorteados);
            } else {
                System.out.println("Comando inválido. Tente novamente.");
                continue;
            }

            exibirResultados(rodada, jogadores, cartelas, numerosSorteados);
            rodada++;
        }

        exibirEstatisticas(rodada - 1, jogadores, cartelas, numerosSorteados);
    }

    private static String[] obterJogadores() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Informe os nicknames dos jogadores separados por hífen:");
        String jogadoresInput = scanner.nextLine();
        return jogadoresInput.split("-");
    }

    private static String[][] gerarCartelas(int numJogadores) {
        String[][] cartelas = new String[numJogadores][5];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < numJogadores; i++) {
            System.out.println("Gerar cartela para " + jogadores[i]);
            System.out.println("Opções de comando: ");
            System.out.println("1. Cartela automática");
            System.out.println("2. Cartela manual");
            int opcao = scanner.nextInt();

            if (opcao == 1) {
                cartelas[i] = gerarCartelaAutomatica();
            } else if (opcao == 2) {
                cartelas[i] = gerarCartelaManual();
            } else {
                System.out.println("Comando inválido. Tente novamente.");
                i--;
            }
        }
        return cartelas;
    }

    private static String[] gerarCartelaAutomatica() {
        String[] cartela = new String[5];

        for (int i = 0; i < 5; i++) {
            StringBuilder linha = new StringBuilder();
            Random random = new Random();

            for (int j = 0; j < 5; j++) {
                int numero = random.nextInt(15) + 1 + (j * 15);
                linha.append(numero).append(",");
            }

            cartela[i] = linha.substring(0, linha.length() - 1);
        }

        return cartela;
    }

    private static String[] gerarCartelaManual() {
        String[] cartela = new String[5];
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 5; i++) {
            System.out.println("Digite os números da linha " + (i + 1) + " separados por vírgula:");
            cartela[i] = scanner.nextLine();
        }

        return cartela;
    }

    private static void exibirCartelas(String[][] cartelas) {
        for (int i = 0; i < cartelas.length; i++) {
            System.out.println("Cartela do jogador " + (i + 1) + ":");
            for (int j = 0; j < 5; j++) {
                System.out.println(cartelas[i][j]);
            }
            System.out.println();
        }
    }

    private static void sortearAutomatico(int[] numerosSorteados) {
        Random random = new Random();
        int numeroSorteado;

        do {
            numeroSorteado = random.nextInt(75) + 1;
        } while (numerosSorteados[numeroSorteado - 1] != -1);

        numerosSorteados[numeroSorteado - 1] = 1;
    }

    private static void sortearManual(int[] numerosSorteados) {
        Scanner scanner = new Scanner(System.in);
        int numeroSorteado;

        do {
            System.out.println("Digite o número sorteado:");
            numeroSorteado = scanner.nextInt();
            if (numeroSorteado < 1 || numeroSorteado > 75) {
                System.out.println("Número inválido. Digite novamente.");
            } else if (numerosSorteados[numeroSorteado - 1] != -1) {
                System.out.println("Número já sorteado. Digite novamente.");
            }
        } while (numeroSorteado < 1 || numeroSorteado > 75 || numerosSorteados[numeroSorteado - 1] != -1);

        numerosSorteados[numeroSorteado - 1] = 1;
    }

    private static boolean verificarCartela(String[] cartela, int[] numerosSorteados) {
        for (int i = 0; i < cartela.length; i++) {
            String[] numerosCartela = cartela[i].split(",");
            for (String numero : numerosCartela) {
                int num = Integer.parseInt(numero);
                if (numerosSorteados[num - 1] == -1) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void exibirResultados(int rodada, String[] jogadores, String[][] cartelas, int[] numerosSorteados) {
        System.out.println("Resultados da Rodada " + rodada + ":");

        for (int i = 0; i < jogadores.length; i++) {
            System.out.println("Jogador " + (i + 1) + " - " + jogadores[i] + ":");
            System.out.println("Cartela:");
            for (int j = 0; j < cartelas[i].length; j++) {
                System.out.println(cartelas[i][j]);
            }
            System.out.println("Bingo: " + (verificarCartela(cartelas[i], numerosSorteados) ? "Sim" : "Não"));
            System.out.println();
        }
    }

    private static void exibirEstatisticas(int rodadas, String[] jogadores, String[][] cartelas, int[] numerosSorteados) {
        System.out.println("Fim do jogo! Estatísticas finais:");

        for (int i = 0; i < jogadores.length; i++) {
            System.out.println("Jogador " + (i + 1) + " - " + jogadores[i] + ":");
            System.out.println("Cartela:");
            for (int j = 0; j < cartelas[i].length; j++) {
                System.out.println(cartelas[i][j]);
            }
            System.out.println("Bingo: " + (verificarCartela(cartelas[i], numerosSorteados) ? "Sim" : "Não"));
            System.out.println();
        }

        System.out.println("Número total de rodadas: " + rodadas);
    }

    private static boolean bingo(String[][] cartelas) {
        for (String[] cartela : cartelas) {
            if (!Arrays.asList(cartela).contains("-1")) {
                return true;
            }
        }
        return false;
    }
}
