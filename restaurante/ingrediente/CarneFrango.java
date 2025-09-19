package restaurante.ingrediente;

public final class CarneFrango extends Ingrediente {
    @Override
    public String obterNome() {
        return "Carne de Frango";
    }

    @Override
    public float obterPreco() {
        return 1.00f;
    }
}