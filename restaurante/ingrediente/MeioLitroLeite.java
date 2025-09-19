package restaurante.ingrediente;

public final class MeioLitroLeite extends Ingrediente {
    @Override
    public String obterNome() {
        return "Leite";
    }

    @Override
    public float obterPreco() {
        return 1.20f;
    }
}