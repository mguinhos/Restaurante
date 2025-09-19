package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.*;

public final class VeggieBurguer extends Alimento {
    @Override
    public String obterNome() {
        return "Veggie Burguer";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

        ingredientes.add(new PaoDeHamburguer());
        ingredientes.add(new CarneDeSoja());
        ingredientes.add(new Alface());
        ingredientes.add(new FatiaTomate());
        ingredientes.add(new FatiaCebola());
        ingredientes.add(new Maionese());
        ingredientes.add(new PaoDeHamburguer());

        return ingredientes;
    }
}