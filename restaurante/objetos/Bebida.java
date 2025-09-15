package restaurante.objetos;

public final class Bebida extends Alimento {
    protected String marca;

    public Bebida(String nome, String descricao, double preco, String marca) {
        super(nome, descricao, preco);
        this.marca = marca;
    }

    @Override
    public String toString() {
        return nome + "\n| Descricao: " + descricao + " - Preco: " + preco + " - Marca: " + marca + " |";
    }
}
