package restaurante;

import restaurante.objetos.Alimento;
import restaurante.objetos.Cliente;
import restaurante.objetos.Funcionario;

import java.util.InputMismatchException;
import java.util.Vector;
import java.util.Scanner;

public class Main {
    static Scanner in = new Scanner(System.in);
    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();

        Vector<Cliente> clientes = new Vector<Cliente>();
        Vector<Funcionario> funcionarios = new Vector<Funcionario>();
        Vector<Alimento> alimentos = new Vector<Alimento>();
        restaurante.InserirFuncionarios(funcionarios);
        restaurante.InserirAlimentos(alimentos);
        int op = Menu();
        switch (op){
            case 1:
                System.out.println("Reserva de Mesa\n");
                break;
            case 2:
                System.out.println("Mostra mesas");
                break;
            case 3:
                System.out.println("Mostra Cadapio do dia");
                restaurante.ListaAlimentos(alimentos);
                break;
            case 4:
                restaurante.ListaFuncionarios(funcionarios);
            default:
                System.out.println("Opção Invalida");
                break;
        }

        clientes.add(new Cliente("Marcos"));
        clientes.add(new Cliente("Julia"));

        restaurante.reservarMesa(clientes);


    }
    static int Menu(){
        System.out.println("1 - Reservar Mesa");
        System.out.println("2 - Mostrar Mesas Reservadas");
        System.out.println("3 - Mostra Cadapio do Dia");
        System.out.println("4 - Exibir Funcionairos");

        try {
            System.out.println("Sua Opção: ");
            int op = in.nextInt();
            return op;
        }catch (InputMismatchException e){
            System.out.println("ERROR!");
        }
        return 0;
    }


}
