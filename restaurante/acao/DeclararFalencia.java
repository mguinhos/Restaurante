package restaurante.acao;

import restaurante.Restaurante;
import restaurante.Cargo;

public final class DeclararFalencia extends Acao {
    Restaurante restaurante;

    public DeclararFalencia(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    @Override
    public void executar() {
        System.out.println("  🚨 DECLARAÇÃO DE FALÊNCIA 🚨");
        System.out.println("  O restaurante não consegue mais sustentar suas operações");
        System.out.println("  Dinheiro no cofre: R$ " + String.format("%.2f", restaurante.obterDinheiroNoCofre()));
        System.out.println("  Todas as operações serão encerradas");
        System.out.println("  FIM DA SIMULAÇÃO");

        System.exit(0);
    }

    @Override
    public String obterNome() {
        return "Declarar Falência";
    }

    @Override
    public String obterDescricao() {
        return "Declara falência do restaurante quando não há mais recursos para continuar operando";
    }

    @Override
    public boolean verificarSePodeExecutar(Cargo cargo) {
        return cargo == Cargo.Gerente;
    }
}