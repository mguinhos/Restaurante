package restaurante;

import restaurante.objetos.*;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
import restaurante.objetos.Cardapio;
import restaurante.objetos.Pedido;

public class Restaurante {
    static Logger logger = Logger.getLogger("restaurante");

    Cardapio cardapio;
    Vector<Mesa> mesas;

    Vector<Cliente> clientes;
    Vector<Funcionario> funcionarios;
    Vector<Alimento> alimentos;
    Vector<Pedido> pedido;

    public Restaurante() {
        this.cardapio = new Cardapio();
        this.mesas = new Vector<Mesa>();

        this.clientes = new Vector<Cliente>();
        this.funcionarios = new Vector<Funcionario>();
        this.alimentos = new Vector<Alimento>();
        this.pedido = new Vector<Pedido>();
    }

    public void Abrir(){
        this.InserirAlimentos();
        this.InserirFuncionarios();
    }

    // Quais mesas estão disponíveis?
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

    public void ListaFuncionarios() {
        System.out.println("FUNCINARIOS NO SISTEMA: ");

        for (Funcionario f : this.funcionarios){
            System.out.println(f.toString());
        }
    }

    public void ListaAlimentos() {
        for (Alimento a : this.alimentos) {
            System.out.println(a.toString());
        }
    }

    public Vector<Cliente> clientesNoSistemas() {
        Vector<Cliente> clientes = new Vector<Cliente>(); 
        
        clientes.add(new Cliente("Mariana Oliveira"));
        clientes.add(new Cliente("Carlos Mendes"));
        clientes.add(new Cliente("Ana Beatriz "));

        return clientes;
    }

    public void InserirFuncionarios() {
        this.funcionarios.add(new Funcionario("Pedro", "Gerente", 11));
        this.funcionarios.add(new Funcionario("Lucas", "Chefe", 12));
        this.funcionarios.add(new Funcionario("Betriz", "Assintente de Cozinha", 13));
        this.funcionarios.add(new Funcionario("Inaldo", "Barista", 14));
        this.funcionarios.add(new Funcionario("Carol", "Garçonete", 15));
        this.funcionarios.add(new Funcionario("Maria", "Faxineira", 16 ));
        this.funcionarios.add(new Funcionario("Alex", "Garço", 17));
    }

    public void InserirAlimentos() {
        this.alimentos.add(new Prato("Picanha", "Carne", 30));
        this.alimentos.add(new Prato("Pizza", "Mussarela", 34));
        this.alimentos.add(new Prato("Sushi", "Peixe Cru", 25));
        this.alimentos.add(new Bebida("Vinho", "Feito de uva", 45, "Tinto"));
        this.alimentos.add(new Bebida("Cerveja", "Deixa Bebado", 75, "Skoll"));
        this.alimentos.add(new Bebida("Guarana", "E simples", 14, "Guarana"));
    }

    Vector<Alimento> alimentosSolicitados(int ali1, int ali2) {
        Vector<Alimento> alimentos = new Vector<Alimento>(); 

        alimentos.add(this.alimentos.get(ali1));
        alimentos.add(this.alimentos.get(ali2));

        return alimentos;

    }

    public void InserirPedido() {
        this.pedido.add(new Pedido(this.clientes.get(0), alimentosSolicitados(0, 2)));
        this.pedido.add(new Pedido(this.clientes.get(1), alimentosSolicitados(1, 4)));
        this.pedido.add(new Pedido(this.clientes.get(2), alimentosSolicitados(5, 3)));
    }

    public void MostraPedidos() {
       for (Pedido p : this.pedido){
            p.mostraPedido();
       }
    }
}