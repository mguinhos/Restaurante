package restaurante.ingrediente;

public final class Maionese extends Ingrediente {
    @Override
    public String obterNome() {
        return "Maionese";
    }

    @Override
    public float obterPreco() {
        return 0.30f;
    }
}