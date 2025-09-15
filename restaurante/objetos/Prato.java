package restaurante.objetos;

public class Prato extends Alimento{


    public Prato(String nome, String descricao, double preco) {
        super(nome, descricao, preco);
    }

    @Override
    public String toString() {
        String aux = super.toString();

        return aux;
    }
}
