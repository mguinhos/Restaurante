package restaurante;

import restaurante.objetos.*;

import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
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

    public void ListaFuncionarios(Vector<Funcionario> funcionarios){
        System.out.println("FUNCINARIOS NO SISTEMA: ");
        for(Funcionario f : funcionarios){
            System.out.println(f.toString());
        }

    }

    public void InserirFuncionarios(Vector<Funcionario> funcionarios){

        funcionarios.add(new Funcionario("Pedro", "Gerente", 11));
        funcionarios.add(new Funcionario("Lucas", "Chefe", 12));
        funcionarios.add(new Funcionario("Betriz", "Assintente de Cozinha", 13));
        funcionarios.add(new Funcionario("Inaldo", "Barista", 14));
        funcionarios.add(new Funcionario("Carol", "Garçonete", 15));
        funcionarios.add(new Funcionario("Maria", "Faxineira", 16 ));
        funcionarios.add(new Funcionario("Alex", "Garço", 17));
    }


    public void ListaAlimentos(Vector<Alimento> alimento){

        System.out.println("CADAPIO DO DIA: ");
        for(Alimento a : alimento){
            System.out.println(a.toString());
        }

    }

    public void InserirAlimentos(Vector<Alimento> alimentos){

        alimentos.add(new Prato("Picanha", "Carne", 30));
        alimentos.add(new Prato("Pizza", "Mussarela", 34));
        alimentos.add(new Prato("Sushi", "Peixe Cru", 25));
        alimentos.add(new Bebida("Vinho", "Feito de uva", 45, "Tinto"));
        alimentos.add(new Bebida("Cerveja", "Deixa Bebado", 75, "Skoll"));
        alimentos.add(new Bebida("Guarana", "E simples", 14, "Guarana"));

    }
}