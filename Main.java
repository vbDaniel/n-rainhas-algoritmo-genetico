import java.util.*;

public class Main {
    public static void main(String[] args) {
        int n = 40;       // número de rainhas
        int elite = 8;    // número de indivíduos elitizados
        int nGer = 2000;  // número de gerações
        int tamPop = 100; // tamanho da população (ajuste conforme desejar)

        IndFactory factory = new NRainhasIndFactory(n);
        List<Ind> populacao = new ArrayList<>();
        Random rand = new Random();

        // Inicializa população
        for (int i = 0; i < tamPop; i++) {
            populacao.add(factory.getInstance());
        }

        for (int ger = 1; ger <= nGer; ger++) {
            // Ordena população pelo número de colisões (menor é melhor)
            populacao.sort(Comparator.comparingDouble(Ind::getAvaliacao));

            // Imprime melhor indivíduo da geração
            Ind melhor = populacao.get(0);
            System.out.println("Geração: " + ger + " | Melhor: " + melhor + " | Avaliação: " + melhor.getAvaliacao());

            // Se resolveu (zero colisões), pode parar
            if (melhor.getAvaliacao() == 0) break;

            // Elitismo: mantém os melhores
            List<Ind> novaPop = new ArrayList<>(populacao.subList(0, elite));

            // Geração de novos indivíduos por recombinação e mutação
            while (novaPop.size() < tamPop) {
                // Seleção aleatória de pais
                Ind pai1 = populacao.get(rand.nextInt(elite));
                Ind pai2 = populacao.get(rand.nextInt(elite));
                List<Ind> filhos = pai1.recombinar(pai2);
                for (Ind filho : filhos) {
                    novaPop.add(filho.mutar());
                    if (novaPop.size() >= tamPop) break;
                }
            }
            populacao = novaPop;
        }
    }
}
