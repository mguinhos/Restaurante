package restaurante.acao;

import restaurante.Cardapio;
import restaurante.ItemCardapio;
import restaurante.Cargo;

public final class AumentarPreco extends Acao {
    Cardapio cardapio;
    ItemCardapio item;
    float percentualAumento;

    public AumentarPreco(Cardapio cardapio, ItemCardapio item, float percentualAumento) {
        this.cardapio = cardapio;
        this.item = item;
        this.percentualAumento = percentualAumento;
    }

    @Override
    public void executar() {
        float precoAtual = this.item.obterPreco();
        float novoPreco = precoAtual * (1 + this.percentualAumento / 100);
        this.item.definirPreco(novoPreco);
        System.out.println("  Preço de " + this.item.obterNome() + " aumentado de R$ " +
                String.format("%.2f", precoAtual) + " para R$ " +
                String.format("%.2f", novoPreco));
    }

    @Override
    public String obterNome() {
        return "Aumentar Preço de " + this.item.obterNome();
    }

    @Override
    public String obterDescricao() {
        return "Aumenta o preço de " + this.item.obterNome() + " em " + this.percentualAumento
                + "% devido à alta demanda";
    }

    @Override
    public boolean verificarSePodeExecutar(Cargo cargo) {
        return cargo == Cargo.Gerente;
    }
}