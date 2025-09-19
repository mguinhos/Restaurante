package restaurante.ingrediente;

public final class Bacon extends Ingrediente {
    @Override
    public String obterNome() {
        return "Bacon";
    }

    @Override
    public float obterPreco() {
        return 1.00f;
    }
}