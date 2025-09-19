package restaurante.ingrediente;

public final class QueijoCheddar extends Ingrediente {
    @Override
    public String obterNome() {
        return "Queijo Cheddar";
    }

    @Override
    public float obterPreco() {
        return 0.10f;
    }
}