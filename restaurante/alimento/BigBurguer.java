package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.*;

public final class BigBurguer extends Alimento {
    @Override
    public String obterNome() {
        return "Big Burguer";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();

        ingredientes.add(new PaoDeHamburguer());
        ingredientes.add(new CarneDeHamburguer());
        ingredientes.add(new CarneDeHamburguer());
        ingredientes.add(new Alface());
        ingredientes.add(new Picles());
        ingredientes.add(new FatiaCebola());
        ingredientes.add(new QueijoMussarela());
        ingredientes.add(new Maionese());
        ingredientes.add(new PaoDeHamburguer());

        return ingredientes;
    }
}