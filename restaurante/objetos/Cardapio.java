package restaurante.objetos;

import java.util.Vector;

public class Cardapio {
    Vector<Alimento> alimento;

    public Cardapio() {
        this.alimento = new Vector<Alimento>();
    }

    public void exibirCadapio(){
        for(int i = 0; i < this.alimento.size(); i++){
            Alimento alimento = this.alimento.get(i);
            System.err.printf("%d - %s....%d\n", i, alimento.nome, alimento.preco);
        }
    }
        
}
