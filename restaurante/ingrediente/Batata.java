package restaurante.ingrediente;

public final class Batata extends Ingrediente {
    @Override
    public String obterNome() {
        return "Batata";
    }

    @Override
    public float obterPreco() {
        return 3.00f;
    }
}
