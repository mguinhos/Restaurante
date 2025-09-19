package restaurante.ingrediente;

public final class MolhoMostarda extends Ingrediente {
    @Override
    public String obterNome() {
        return "Mostarda";
    }

    @Override
    public float obterPreco() {
        return 0.25f;
    }
}