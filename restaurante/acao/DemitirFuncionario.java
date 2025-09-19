package restaurante.acao;
import restaurante.Restaurante;
import restaurante.pessoa.Funcionario;
import restaurante.Cargo;

public final class DemitirFuncionario extends Acao {
    Restaurante restaurante;
    Funcionario funcionario;

    public DemitirFuncionario(Restaurante restaurante, Funcionario funcionario) {
        this.restaurante = restaurante;
        this.funcionario = funcionario;
    }

    @Override
    public void executar() {
        this.restaurante.demitirFuncionario(this.funcionario);
    }

    @Override
    public String obterNome() {
        return "Demitir Funcionário(a) '" + this.funcionario.obterNome() + "'";
    }

    @Override
    public String obterDescricao() {
        return "Demite o funcionário(a) '" + this.funcionario.obterNome() + "'' do restaurante";
    }
    
    @Override
    public boolean verificarSePodeExecutar(Cargo cargo) {
        return cargo == Cargo.Gerente;
    }
}