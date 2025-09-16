package restaurante.objetos;

import java.util.Vector;

public class Pedido{
    protected Cliente cliente;
    protected Vector<Alimento> alimentos;
    protected double Conta;
    public Pedido(Cliente cliente, Vector<Alimento> alimentos){
        this.cliente = cliente;
        this.alimentos = alimentos;
        this.Conta = ContaPedido();
    }

    double ContaPedido(){
        double conta = 0;
        for(int i = 0; i < this.alimentos.size(); i++){
           conta += alimentos.get(i).preco;
        }
        return conta;
    }

    public String mostraPedido() {
        String aux = (
            "Nome do Cliente: " + this.cliente.nome + "\n" +
            "Alimentos:\n"
        );

        for (Alimento alimento : alimentos) {
            aux += " - " + alimento.getNome() + " (R$ " + alimento.getPreco() + ")\n";
        }

        aux += "\nConta: " + this.Conta;

        return aux;
    }

}