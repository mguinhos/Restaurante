package restaurante;

import restaurante.alimento.Alimento;

public class ItemCardapio extends Avaliavel {
    private Alimento alimento;
    private float preco;

    public ItemCardapio(Alimento alimento, float preco) {
        super();
        this.alimento = alimento;
        this.preco = preco;
    }

    public Alimento obterAlimento() {
        return this.alimento;
    }

    public float obterPreco() {
        return this.preco;
    }

    public void definirPreco(float preco) {
        this.preco = preco;
    }

    public String obterNome() {
        return this.alimento.obterNome();
    }

    public float obterCustoDeProducao() {
        return this.alimento.obterCustoDeProducao();
    }

    public float obterLucro() {
        return this.preco - this.obterCustoDeProducao();
    }

    public boolean produzivel(Estoque estoque) {
        return this.alimento.produzivel(estoque);
    }
}