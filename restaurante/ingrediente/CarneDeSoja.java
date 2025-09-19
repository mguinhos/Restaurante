// ./restaurante/ingrediente/CarneVegetariana.java
package restaurante.ingrediente;

public final class CarneDeSoja extends Ingrediente {
    @Override
    public String obterNome() {
        return "Carne de Soja";
    }

    @Override
    public float obterPreco() {
        return 3.20f;
    }
}