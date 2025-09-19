// ./restaurante/acao/PromoverFuncionario.java
package restaurante.acao;

import restaurante.Restaurante;
import restaurante.pessoa.Funcionario;
import restaurante.Cargo;

public final class PromoverFuncionario extends Acao {
    Restaurante restaurante;
    Funcionario funcionario;
    Cargo novoCargo;

    public PromoverFuncionario(Restaurante restaurante, Funcionario funcionario, Cargo novoCargo) {
        this.restaurante = restaurante;
        this.funcionario = funcionario;
        this.novoCargo = novoCargo;
    }

    @Override
    public void executar() {
        this.restaurante.promoverFuncionario(this.funcionario, this.novoCargo);
    }

    @Override
    public String obterNome() {
        return "Promover " + this.funcionario.obterNome() + " para " + this.novoCargo;
    }

    @Override
    public String obterDescricao() {
        return "Promove o funcionário(a) '" + this.funcionario.obterNome() +
                "' de " + this.funcionario.obterCargo() + " para " + this.novoCargo;
    }

    @Override
    public boolean verificarSePodeExecutar(Cargo cargo) {
        return cargo == Cargo.Gerente;
    }
}