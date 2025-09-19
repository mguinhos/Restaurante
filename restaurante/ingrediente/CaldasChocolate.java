package restaurante.ingrediente;

public final class CaldasChocolate extends Ingrediente {
    @Override
    public String obterNome() {
        return "Calda de Chocolate";
    }

    @Override
    public float obterPreco() {
        return 0.10f;
    }
}