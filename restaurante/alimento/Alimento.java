package restaurante.alimento;

import java.util.ArrayList;
import restaurante.Estoque;
import restaurante.ingrediente.Ingrediente;

public abstract class Alimento {
    public abstract String obterNome();
    public abstract ArrayList<Ingrediente> obterIngredientes();

    public float obterCustoDeProducao() {
        float custo = 0.0f;
        for (Ingrediente ingrediente : this.obterIngredientes()) {
            custo += ingrediente.obterPreco();
        }
        return custo;
    }

    public boolean produzivel(Estoque estoque) {
        return estoque.possuiIngredientes(this.obterIngredientes());
    }
}
