package restaurante.ingrediente;

public final class CarneDeHamburguer extends Ingrediente {
    @Override
    public String obterNome() {
        return "Carne de Hamburguer";
    }

    @Override
    public float obterPreco() {
        return 1.50f;
    }
}
