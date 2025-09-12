package restaurante;

import java.util.Optional;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

abstract class Pessoa {
    protected String nome;

    public Pessoa(String nome) {
        this.nome = nome;
    }
}

class Cliente extends Pessoa {
    public Cliente(String nome) {
        super(nome);
    }

    @Override
    public String toString() {
        String aux = super.toString();
        return aux;
    }
}

class Funcionario extends Pessoa {
    protected String cargo;
    protected int matricula;

    public Funcionario(String nome, String cargo, int matricula) {
        super(nome);
        this.cargo = cargo;
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        String aux = super.toString() + "-Cargo: " + cargo + "Matricula: " + matricula;

        return aux;
    }
}

interface Compravel {
    double getPreco();
    void setPreco();
}

abstract class Alimento implements Compravel {
    protected String nome;
    protected String descricao;
    protected double preco;

    public Alimento(String nome, String descricao, double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    String getNome() {
        return this.nome;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    String getDescricao() {
        return this.descricao;
    }

    void setDescricao() {
        this.descricao = descricao;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco() {
        this.preco = preco;
    }
}

final class Refrigerante extends Alimento {
    protected String marca;

    public Refrigerante(String nome, String descricao, double preco, String marca) {
        super(nome, descricao, preco);

        this.marca = marca;
    }

    @Override
    public String toString() {
        String aux = super.toString() + "-Marca: " + marca;

        return aux;
    }
}

class Cardapio {
    Vector<Alimento> items;

    public Cardapio() {
        this.items = new Vector<Alimento>();
    }
}

class Conta {
    Mesa mesa;
    Vector<Alimento> pedidos;

    void fecharConta() {

    }

    void pedir(Alimento alimento) {

    }
}

class Reserva {
    protected String data;
    protected Cliente cliente;

    public Reserva(String data, Cliente cliente) {
        this.data = data;
        this.cliente = cliente;
    }
}

class Mesa {
    Integer numero_da_mesa;
    Vector<Optional<Cliente>> assentos;

    public Mesa(Integer numero_da_mesa) {
        this.numero_da_mesa = numero_da_mesa;
        this.assentos = new Vector<Optional<Cliente>>();
    }

    Boolean estaDisponivel() {
        return this.assentos.size() == 0;
    }

    Vector<Integer> obterAssentosVagos() {
        Vector<Integer> assentos_vagos = new Vector<Integer>();

        for (int i = 0; i < this.assentos.size(); i++) {
            Optional<Cliente> assento = this.assentos.get(i);

            if (assento.isPresent())
                assentos_vagos.add(i);
        }

        return assentos_vagos;
    }

    void reservar(Vector<Cliente> clientes) {
        for (int i = 0; i < clientes.size(); i++) {
            this.assentos.set(i, Optional.of(clientes.get(i)));
        }
    }
}

public class Restaurante {
    static Logger logger = Logger.getLogger("restaurante");
    
    Cardapio cardapio;
    Vector<Mesa> mesas;

    public Restaurante() {
        this.cardapio = new Cardapio();
        this.mesas = new Vector<Mesa>();
    }

    public List<Mesa> obterMesasDisponiveis() {
        Vector<Mesa> mesas_disponiveis = new Vector<Mesa>();

        for (Mesa mesa : this.mesas) {
            if (mesa.estaDisponivel()) {
                mesas_disponiveis.add(mesa);
            }
        }

        return mesas;
    }

    public Boolean reservarMesa(Vector<Cliente> clientes) {
        logger.info("Reservando mesa");
        
        for (Mesa mesa_disponivel : obterMesasDisponiveis()) {
            Vector<Integer> assentos_vagos = mesa_disponivel.obterAssentosVagos();

            if (assentos_vagos.size() >= clientes.size()) {
                mesa_disponivel.reservar(clientes);

                return true;
            }
        }
        
        return false;
    }
}