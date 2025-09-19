package restaurante.ingrediente;

public final class Picles extends Ingrediente {
    @Override
    public String obterNome() {
        return "Picles";
    }

    @Override
    public float obterPreco() {
        return 0.05f;
    }
}