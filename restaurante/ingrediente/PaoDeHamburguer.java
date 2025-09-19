package restaurante.ingrediente;

public final class PaoDeHamburguer extends Ingrediente {
    @Override
    public String obterNome() {
        return "Pão de Hambúrguer";
    }

    @Override
    public float obterPreco() {
        return 0.80f;
    }
}