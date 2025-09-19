package restaurante.ingrediente;

public final class MolhoKetchup extends Ingrediente {
    @Override
    public String obterNome() {
        return "Ketchup";
    }

    @Override
    public float obterPreco() {
        return 0.25f;
    }
}