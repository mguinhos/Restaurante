// ./restaurante/pessoa/Funcionario.java
package restaurante.pessoa;

import restaurante.Avaliavel;
import restaurante.Cargo;
import restaurante.Cardapio;
import restaurante.ItemCardapio;

public class Funcionario extends Avaliavel implements Pessoa {
    String nome;
    int idade;
    Cargo cargo;

    public Funcionario(String nome, int idade, Cargo cargo) {
        this.nome = nome;
        this.idade = idade;
        this.cargo = cargo;
    }

    public String obterNome() {
        return this.nome;
    }

    public int obterIdade() {
        return this.idade;
    }

    public Cargo obterCargo() {
        return this.cargo;
    }

    public void definirCargo(Cargo novoCargo) {
        this.cargo = novoCargo;
    }

    public void restaurarAvaliacaoParcial() {
        this.avaliar(0.6f);
    }

    public ItemCardapio recomendarAlimento(Cardapio cardapio) {
        ItemCardapio maisBarato = null;
        for (ItemCardapio item : cardapio.obterItemsNoCardapio()) {
            if (maisBarato == null || item.obterPreco() < maisBarato.obterPreco()) {
                maisBarato = item;
            }
        }

        return maisBarato;
    }
}