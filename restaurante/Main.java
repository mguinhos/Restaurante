package restaurante;

import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();

        Vector<Cliente> clientes = new Vector<Cliente>();
        
        
        clientes.add(new Cliente("Marcos"));
        clientes.add(new Cliente("Julia"));

        restaurante.reservarMesa(clientes);
    }
}
