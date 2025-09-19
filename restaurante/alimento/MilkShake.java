package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.*;

public final class MilkShake extends Alimento {
    @Override
    public String obterNome() {
        return "Milk Shake";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

        ingredientes.add(new SorveteBaunilha());
        ingredientes.add(new MeioLitroLeite());
        ingredientes.add(new CaldasChocolate());

        return ingredientes;
    }
}