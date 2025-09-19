package restaurante.acao;

import restaurante.Cardapio;
import restaurante.ItemCardapio;
import restaurante.Cargo;

public final class BaixarPreco extends Acao {
    Cardapio cardapio;
    ItemCardapio item;
    float percentualReducao;

    public BaixarPreco(Cardapio cardapio, ItemCardapio item, float percentualReducao) {
        this.cardapio = cardapio;
        this.item = item;
        this.percentualReducao = percentualReducao;
    }

    @Override
    public void executar() {
        float precoAtual = this.item.obterPreco();
        float novoPreco = precoAtual * (1 - this.percentualReducao / 100);
        this.item.definirPreco(novoPreco);
        System.out.println("  Preço de " + this.item.obterNome() + " reduzido de R$ " +
                String.format("%.2f", precoAtual) + " para R$ " +
                String.format("%.2f", novoPreco));
    }

    @Override
    public String obterNome() {
        return "Baixar Preço de " + this.item.obterNome();
    }

    @Override
    public String obterDescricao() {
        return "Reduz o preço de " + this.item.obterNome() + " em " + this.percentualReducao
                + "% devido à baixa demanda";
    }

    @Override
    public boolean verificarSePodeExecutar(Cargo cargo) {
        return cargo == Cargo.Gerente;
    }
}