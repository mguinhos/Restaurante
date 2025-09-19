package restaurante.ingrediente;

public final class MolhoBarbecue extends Ingrediente {
    @Override
    public String obterNome() {
        return "Molho Barbecue";
    }

    @Override
    public float obterPreco() {
        return 0.40f;
    }
}