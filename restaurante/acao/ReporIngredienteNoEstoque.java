package restaurante.acao;

import restaurante.Estoque;
import restaurante.Restaurante;
import restaurante.ingrediente.Ingrediente;
import restaurante.Cargo;

public final class ReporIngredienteNoEstoque extends Acao {
    Estoque estoque;
    Restaurante restaurante;
    Ingrediente ingrediente;

    public ReporIngredienteNoEstoque(Estoque estoque, Ingrediente ingrediente) {
        this.estoque = estoque;
        this.ingrediente = ingrediente;
        this.restaurante = null;
    }

    public ReporIngredienteNoEstoque(Restaurante restaurante, Ingrediente ingrediente) {
        this.estoque = restaurante.obterEstoque();
        this.restaurante = restaurante;
        this.ingrediente = ingrediente;
    }

    @Override
    public void executar() {
        float custoIngrediente = this.ingrediente.obterPreco() * 100;

        if (this.restaurante != null) {
            if (this.restaurante.removerDinheiroNoCofre(custoIngrediente)) {
                for (int i = 0; i < 100; i++) {
                    this.estoque.adicionarIngrediente(this.ingrediente);
                }
                System.out.println("  Reposição realizada! Custo: R$ " + String.format("%.2f", custoIngrediente));
                System.out.println("  Dinheiro restante no cofre: R$ "
                        + String.format("%.2f", this.restaurante.obterDinheiroNoCofre()));
            } else {
                System.out.println("  ERRO: Dinheiro insuficiente no cofre para repor " + this.ingrediente.obterNome());
                System.out.println("  Custo necessário: R$ " + String.format("%.2f", custoIngrediente));
                System.out.println(
                        "  Dinheiro disponível: R$ " + String.format("%.2f", this.restaurante.obterDinheiroNoCofre()));
            }
        } else {
            this.estoque.adicionarIngrediente(this.ingrediente);
            System.out.println("  Reposição realizada sem custo");
        }
    }

    @Override
    public String obterNome() {
        return "Repor '" + this.ingrediente.obterNome() + "'";
    }

    @Override
    public String obterDescricao() {
        float custoIngrediente = this.ingrediente.obterPreco() * 10;
        return "Reponhe o ingrediente '" + this.ingrediente.obterNome() +
                "' no estoque (Custo: R$ " + String.format("%.2f", custoIngrediente) + ")";
    }

    @Override
    public boolean verificarSePodeExecutar(Cargo cargo) {
        return cargo == Cargo.Gerente || cargo == Cargo.Estoquista;
    }
}