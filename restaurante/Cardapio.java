package restaurante;

import java.util.ArrayList;
import restaurante.alimento.Alimento;

public class Cardapio {
    ArrayList<ItemCardapio> items;

    public Cardapio() {
        this.items = new ArrayList<ItemCardapio>();
    }

    public void adicionarDesconto(ItemCardapio item, float percentualDesconto) {
        float precoAtual = item.obterPreco();
        float novoPreco = precoAtual * (1 - percentualDesconto / 100);
        item.definirPreco(novoPreco);
    }

    public void adicionarItemNoCardapio(Alimento alimento, float preco) {
        ItemCardapio item = new ItemCardapio(alimento, preco);
        this.items.add(item);
    }

    public void adicionarItemNoCardapio(ItemCardapio item) {
        this.items.add(item);
    }

    public ArrayList<ItemCardapio> obterItemsNoCardapio() {
        return this.items;
    }

    public ArrayList<ItemCardapio> obterItemsEsgotados(Estoque estoque) {
        ArrayList<ItemCardapio> items_esgotados = new ArrayList<ItemCardapio>();
        for (ItemCardapio item : this.items) {
            if (!item.produzivel(estoque)) {
                items_esgotados.add(item);
            }
        }
        return items_esgotados;
    }
}
