package restaurante.objetos;

import java.util.Optional;
import java.util.Vector;

public class Mesa {
    Integer numero_da_mesa;
    Vector<Optional<Cliente>> assentos;

    public Mesa(Integer numero_da_mesa) {
        this.numero_da_mesa = numero_da_mesa;
        this.assentos = new Vector<Optional<Cliente>>();
    }

    public Boolean estaDisponivel() {
        return this.assentos.size() == 0;
    }

    // Quais assentos estão vagos?
    public Vector<Integer> obterAssentosVagos() {
        Vector<Integer> assentos_vagos = new Vector<Integer>();

        for (int i = 0; i < this.assentos.size(); i++) {
            Optional<Cliente> assento = this.assentos.get(i);

            if (assento.isPresent())
                assentos_vagos.add(i);
        }

        return assentos_vagos;
    }

    public void reservar(Vector<Cliente> clientes) {
        for (int i = 0; i < clientes.size(); i++) {
            this.assentos.set(i, Optional.of(clientes.get(i)));
        }
    }
}
