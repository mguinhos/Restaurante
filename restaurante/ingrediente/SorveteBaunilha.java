package restaurante.ingrediente;

public final class SorveteBaunilha extends Ingrediente {
    @Override
    public String obterNome() {
        return "Sorvete de Baunilha";
    }

    @Override
    public float obterPreco() {
        return 1.30f;
    }
}