package restaurante.acao;
import restaurante.Cardapio;
import restaurante.ItemCardapio;
import restaurante.Cargo;

public final class AplicarDesconto extends Acao {
    Cardapio cardapio;
    ItemCardapio item;
    float percentualDesconto;

    public AplicarDesconto(Cardapio cardapio, ItemCardapio item, float percentualDesconto) {
        this.cardapio = cardapio;
        this.item = item;
        this.percentualDesconto = percentualDesconto;
    }

    @Override
    public void executar() {
        this.cardapio.adicionarDesconto(this.item, this.percentualDesconto);
    }

    @Override
    public String obterNome() {
        return "Aplicar Desconto";
    }

    @Override
    public String obterDescricao() {
        return "Aplica desconto de " + this.percentualDesconto + "% no item " + this.item.obterNome();
    }
    
    @Override
    public boolean verificarSePodeExecutar(Cargo cargo) {
        return cargo == Cargo.Gerente;
    }
}