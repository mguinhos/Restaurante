package restaurante.acao;
import restaurante.Cargo;

public abstract class Acao {
    public abstract void executar();
    public abstract String obterNome();
    public abstract String obterDescricao();
    
    public boolean verificarSePodeExecutar(Cargo cargo) {
        return true;
    }
}