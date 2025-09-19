package restaurante.alimento;

import java.util.ArrayList;
import restaurante.ingrediente.*;

public final class BaconBurguer extends Alimento {
    @Override
    public String obterNome() {
        return "Bacon Burguer";
    }

    @Override
    public ArrayList<Ingrediente> obterIngredientes() {
        ArrayList<Ingrediente> ingredientes = new ArrayList<Ingrediente>();
        
        ingredientes.add(new PaoDeHamburguer());
        ingredientes.add(new CarneDeHamburguer());
        ingredientes.add(new Bacon());
        ingredientes.add(new QueijoCheddar());
        ingredientes.add(new Alface());
        ingredientes.add(new FatiaTomate());
        ingredientes.add(new MolhoBarbecue());
        ingredientes.add(new PaoDeHamburguer());

        return ingredientes;
    }
}