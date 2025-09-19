package restaurante.ingrediente;

public final class MeioLitroAguaMineral extends Ingrediente {
    @Override
    public String obterNome() {
        return "Água Mineral";
    }

    @Override
    public float obterPreco() {
        return 1.00f;
    }
}