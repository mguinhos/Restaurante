package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.CarneDeHamburguer;
import restaurante.ingrediente.FatiaTomate;
import restaurante.ingrediente.Ingrediente;
import restaurante.ingrediente.PaoDeHamburguer;

public final class Hamburguer extends Alimento {
    @Override
    public String obterNome() {
        return "Hamburguer";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        
        ingredientes.add(new PaoDeHamburguer());
        ingredientes.add(new FatiaTomate());
        ingredientes.add(new CarneDeHamburguer());
        ingredientes.add(new FatiaTomate());
        ingredientes.add(new PaoDeHamburguer());

        return ingredientes;
    }
}