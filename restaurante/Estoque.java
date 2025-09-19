package restaurante;

import java.util.ArrayList;
import restaurante.ingrediente.Ingrediente;

public class Estoque {
    ArrayList<Ingrediente> ingredientes;

    public Estoque() {
        this.ingredientes = new ArrayList<Ingrediente>();
    }
    
    public boolean possuiIngredientes(ArrayList<Ingrediente> ingredientesNecessarios) {
        for (Ingrediente necessario : ingredientesNecessarios) {
            boolean encontrado = false;

            for (Ingrediente disponivel : ingredientes) {
                if (disponivel.obterNome().equals(necessario.obterNome())) {
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Ingrediente> obterIngredientes() {
        return this.ingredientes;
    }

    public void removerIngredientes(ArrayList<Ingrediente> ingredientesRemover) {
        for (Ingrediente remover : ingredientesRemover) {
            for (int i = 0; i < ingredientes.size(); i++) {
                if (ingredientes.get(i).obterNome().equals(remover.obterNome())) {
                    ingredientes.remove(i);
                    break; // Remove apenas uma unidade
                }
            }
        }
    }
    
    public void adicionarIngrediente(Ingrediente ingrediente) {
        ingredientes.add(ingrediente);
    }
}