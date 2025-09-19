package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.Ingrediente;
import restaurante.ingrediente.MeioLitroDeAguaGaseificada;
import restaurante.ingrediente.RefrigeranteEmPo;

public final class Refrigerante extends Alimento {
    @Override
    public String obterNome() {
        return "Refrigerante";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        
        ingredientes.add(new MeioLitroDeAguaGaseificada());
        ingredientes.add(new RefrigeranteEmPo());

        return ingredientes;
    }
}
