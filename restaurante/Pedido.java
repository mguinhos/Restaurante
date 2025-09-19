package restaurante;

import restaurante.pessoa.Cliente;
import restaurante.pessoa.Funcionario;

public class Pedido {
    private Cliente cliente;
    private ItemCardapio item;
    private Funcionario caixa;
    private Funcionario cozinheiro;
    private boolean foiRecomendado;

    public Pedido(Cliente cliente, ItemCardapio item, Funcionario caixa) {
        this.cliente = cliente;
        this.item = item;
        this.caixa = caixa;
        this.foiRecomendado = false;
    }

    public Cliente obterCliente() {
        return cliente;
    }

    public ItemCardapio obterItem() {
        return item;
    }

    public Funcionario obterCaixa() {
        return caixa;
    }

    public Funcionario obterCozinheiro() {
        return cozinheiro;
    }

    public boolean foiRecomendado() {
        return foiRecomendado;
    }

    public void definirCozinheiro(Funcionario cozinheiro) {
        this.cozinheiro = cozinheiro;
    }

    public void marcarComoRecomendado() {
        this.foiRecomendado = true;
    }
}