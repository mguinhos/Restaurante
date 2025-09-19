package restaurante.ingrediente;

public final class Alface extends Ingrediente {
    @Override
    public String obterNome() {
        return "Alface";
    }

    @Override
    public float obterPreco() {
        return 1.90f;
    }
}
