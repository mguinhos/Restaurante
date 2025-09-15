package restaurante.objetos;

public abstract class Alimento implements Compravel {
    protected String nome;
    protected String descricao;
    protected double preco;

    public Alimento(String nome, String descricao, double preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }

    String getNome() {
        return this.nome;
    }

    void setNome(String nome) {
        this.nome = nome;
    }

    String getDescricao() {
        return this.descricao;
    }

    void setDescricao() {
        this.descricao = descricao;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco() {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return nome + "\n| Descricao: " + descricao + " - Preco: " + preco + " |";
    }
}
