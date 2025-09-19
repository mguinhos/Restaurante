package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.*;

public final class ChickenBurguer extends Alimento {
    @Override
    public String obterNome() {
        return "Chicken Burguer";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        
        ingredientes.add(new PaoDeHamburguer());
        ingredientes.add(new CarneFrango());
        ingredientes.add(new QueijoMussarela());
        ingredientes.add(new Alface());
        ingredientes.add(new FatiaTomate());
        ingredientes.add(new Maionese());
        ingredientes.add(new PaoDeHamburguer());

        return ingredientes;
    }
}