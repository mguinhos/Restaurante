package restaurante.ingrediente;

public final class FrutaCitrica extends Ingrediente {
    @Override
    public String obterNome() {
        return "Fruta Cítrica";
    }

    @Override
    public float obterPreco() {
        return 2.50f;
    }
}