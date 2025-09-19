package restaurante;

import java.util.ArrayList;

public abstract class Avaliavel {
    ArrayList<Float> avaliacoes;
  
    public Avaliavel() {
        this.avaliacoes = new ArrayList<Float>();

        this.avaliacoes.add(0.5f);
    }
    
    public float obterAvaliacao() {
        if (avaliacoes.isEmpty()) {
            return 0.0f;
        }
        
        float soma = 0.0f;

        for (Float avaliacao : avaliacoes) {
            soma += avaliacao;
        }
        
        return soma / avaliacoes.size();
    }

    public void avaliar(float avaliacao) {
        this.avaliacoes.add(avaliacao);
    }

    public boolean desempenhoEstaBaixo() {
        return this.obterAvaliacao() < 0.30f;
    }

    public boolean desempenhoEstaAlto() {
        return this.obterAvaliacao() > 0.80f;
    }
}