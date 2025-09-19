package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.Batata;
import restaurante.ingrediente.Ingrediente;

public final class BatatasFritas extends Alimento {
    @Override
    public String obterNome() {
        return "Batatas Fritas";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        
        ingredientes.add(new Batata());

        return ingredientes;
    }
}