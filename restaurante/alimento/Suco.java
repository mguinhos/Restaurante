package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.*;

public final class Suco extends Alimento {
    @Override
    public String obterNome() {
        return "Suco";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        ingredientes.add(new FrutaCitrica());
        ingredientes.add(new MeioLitroAguaMineral());
        return ingredientes;
    }
}