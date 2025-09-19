package restaurante.ingrediente;

public final class RefrigeranteEmPo extends Ingrediente {
    @Override
    public String obterNome() {
        return "Refrigerante em Pó";
    }

    @Override
    public float obterPreco() {
        return 10.00f;
    }
}
