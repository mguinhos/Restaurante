package restaurante.ingrediente;

public final class QueijoMussarela extends Ingrediente {
    @Override
    public String obterNome() {
        return "Queijo Mussarela";
    }

    @Override
    public float obterPreco() {
        return 0.75f;
    }
}