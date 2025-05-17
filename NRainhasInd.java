import java.util.*;

public class NRainhasInd implements Ind {
    private int[] gene;
    private int n;
    private static final double TAXA_MUTACAO = 0.02; // TMG = 0.02
    private Random rand = new Random();

    public NRainhasInd(int n) {
        this.n = n;
        this.gene = new int[n];
        // Preencher os genes com valores aleatórios entre 0 e n-1
        for (int i = 0; i < n; i++) {
            this.gene[i] = rand.nextInt(n);
        }
    }

    @Override
    public List<Ind> recombinar(Ind ind) {
        NRainhasInd outro = (NRainhasInd) ind;
        int corte1 = rand.nextInt(n);
        int corte2 = rand.nextInt(n);
        if (corte1 > corte2) {
            int temp = corte1;
            corte1 = corte2;
            corte2 = temp;
        }
        int[] filho1 = new int[n];
        int[] filho2 = new int[n];
        for (int i = 0; i < n; i++) {
            if (i >= corte1 && i <= corte2) {
                filho1[i] = outro.gene[i];
                filho2[i] = this.gene[i];
            } else {
                filho1[i] = this.gene[i];
                filho2[i] = outro.gene[i];
            }
        }
        List<Ind> filhos = new ArrayList<>();
        NRainhasInd f1 = new NRainhasInd(n);
        NRainhasInd f2 = new NRainhasInd(n);
        f1.gene = filho1;
        f2.gene = filho2;
        filhos.add(f1);
        filhos.add(f2);
        return filhos;
    }

    @Override
    public Ind mutar() {
        NRainhasInd mutante = new NRainhasInd(n);
        mutante.gene = Arrays.copyOf(this.gene, n);
        boolean mutou = false;
        
        for (int i = 0; i < n; i++) {
            if (rand.nextDouble() < TAXA_MUTACAO) {
                mutou = true;
                int novoValor;
                do {
                    novoValor = rand.nextInt(n);
                } while (novoValor == mutante.gene[i]);
                mutante.gene[i] = novoValor;
            }
        }
        
        // Se nenhum gene sofreu mutação, escolher um gene aleatório e mutar
        if (!mutou) {
            int posicao = rand.nextInt(n);
            int novoValor;
            do {
                novoValor = rand.nextInt(n);
            } while (novoValor == mutante.gene[posicao]);
            mutante.gene[posicao] = novoValor;
        }
        
        return mutante;
    }

    @Override
    public double getAvaliacao() {
        int colisoes = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // Verifica colisão na mesma linha ou na diagonal
                if (gene[i] == gene[j] || Math.abs(gene[i] - gene[j]) == Math.abs(i - j)) {
                    colisoes++;
                }
            }
        }
        return colisoes;
    }

    @Override
    public String toString() {
        return Arrays.toString(gene);
    }
}
