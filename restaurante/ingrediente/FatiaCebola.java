package restaurante.ingrediente;

public final class FatiaCebola extends Ingrediente {
    @Override
    public String obterNome() {
        return "Fata de Cebola";
    }

    @Override
    public float obterPreco() {
        return 0.30f;
    }
}