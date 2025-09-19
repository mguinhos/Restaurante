package restaurante;

import java.util.ArrayList;

import restaurante.erros.FuncionarioNaoEncontradoErro;
import restaurante.pessoa.Funcionario;

public class Restaurante {
    Cardapio cardapio;
    Estoque estoque;
    float dinheiroNoCofre;

    ArrayList<Funcionario> funcionarios;

    public Restaurante() {
        this.cardapio = new Cardapio();
        this.estoque = new Estoque();
        this.funcionarios = new ArrayList<Funcionario>();
        this.dinheiroNoCofre = 500.0f;
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        this.funcionarios.add(funcionario);
    }

    public void demitirFuncionario(Funcionario funcionario) {
        this.funcionarios.remove(funcionario);
    }

    public void promoverFuncionario(Funcionario funcionario, Cargo novoCargo) {
        if (this.funcionarios.contains(funcionario)) {
            Cargo cargoAtual = funcionario.obterCargo();
            funcionario.definirCargo(novoCargo);

            funcionario.avaliar(0.8f);
            System.out.println("  Funcionário " + funcionario.obterNome() +
                    " foi promovido de " + cargoAtual + " para " + novoCargo);
        }
    }

    public void rebaixarFuncionario(Funcionario funcionario, Cargo novoCargo) {
        if (this.funcionarios.contains(funcionario)) {
            Cargo cargoAtual = funcionario.obterCargo();
            funcionario.definirCargo(novoCargo);

            funcionario.restaurarAvaliacaoParcial();
            System.out.println("  Funcionário " + funcionario.obterNome() +
                    " foi rebaixado de " + cargoAtual + " para " + novoCargo +
                    " (segunda chance dada)");
        }
    }

    public float obterDinheiroNoCofre() {
        return this.dinheiroNoCofre;
    }

    public void adicionarDinheiroNoCofre(float valor) {
        this.dinheiroNoCofre += valor;
    }

    public boolean removerDinheiroNoCofre(float valor) {
        if (this.dinheiroNoCofre >= valor) {
            this.dinheiroNoCofre -= valor;
            return true;
        }
        return false;
    }

    public void processarPagamento(float valorPago) {
        this.adicionarDinheiroNoCofre(valorPago);
    }

    public ArrayList<Funcionario> obterFuncionarios() {
        return this.funcionarios;
    }

    public Cardapio obteCardapio() {
        return this.cardapio;
    }

    public Estoque obterEstoque() {
        return this.estoque;
    }

    public Funcionario obterFuncionarioPorNome(String nome) throws FuncionarioNaoEncontradoErro {
        for (Funcionario funcionario : this.funcionarios) {
            if (funcionario.obterNome().equals(nome)) {
                return funcionario;
            }
        }

        throw new FuncionarioNaoEncontradoErro("Funcionário com nome '" + nome + "' não foi encontrado");
    }
}