package restaurante.ingrediente;

public final class FatiaTomate extends Ingrediente {
    @Override
    public String obterNome() {
        return "Fatia de Tomate";
    }

    @Override
    public float obterPreco() {
        return 0.10f;
    }
}