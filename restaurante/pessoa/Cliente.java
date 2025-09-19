package restaurante.pessoa;

import java.util.ArrayList;
import java.util.Random;
import restaurante.ClasseSocial;
import restaurante.ingrediente.Ingrediente;
import restaurante.alimento.Alimento;

public final class Cliente implements Pessoa {
    String nome;
    int idade;
    float dinheiro;
    ClasseSocial classeSocial;
    ArrayList<Ingrediente> ingredientesQueGosta;
    ArrayList<Ingrediente> ingredientesQueDesgosta;
    ArrayList<Alimento> alimentosQueGosta;
    ArrayList<Alimento> alimentosQueDesgosta;

    public Cliente(String nome, int idade) {
        this.nome = nome;
        this.idade = idade;

        // Gerar classe social aleatoriamente
        Random random = new Random();
        ClasseSocial[] classes = ClasseSocial.values();
        this.classeSocial = classes[random.nextInt(classes.length)];

        // Dinheiro baseado na classe social
        switch (this.classeSocial) {
            case BaixaRenda:
                this.dinheiro = 15.0f + (float) (Math.random() * 25.0f); // Entre R$ 15 e R$ 40
                break;
            case MediaRenda:
                this.dinheiro = 40.0f + (float) (Math.random() * 60.0f); // Entre R$ 40 e R$ 100
                break;
            case AltaRenda:
                this.dinheiro = 80.0f + (float) (Math.random() * 120.0f); // Entre R$ 80 e R$ 200
                break;
        }

        this.ingredientesQueGosta = new ArrayList<Ingrediente>();
        this.ingredientesQueDesgosta = new ArrayList<Ingrediente>();
        this.alimentosQueGosta = new ArrayList<Alimento>();
        this.alimentosQueDesgosta = new ArrayList<Alimento>();
    }

    public Cliente(String nome, int idade, ClasseSocial classeSocial, float dinheiro) {
        this.nome = nome;
        this.idade = idade;
        this.classeSocial = classeSocial;
        this.dinheiro = dinheiro;
        this.ingredientesQueGosta = new ArrayList<Ingrediente>();
        this.ingredientesQueDesgosta = new ArrayList<Ingrediente>();
        this.alimentosQueGosta = new ArrayList<Alimento>();
        this.alimentosQueDesgosta = new ArrayList<Alimento>();
    }

    public String obterNome() {
        return this.nome;
    }

    public int obterIdade() {
        return this.idade;
    }

    public ClasseSocial obterClasseSocial() {
        return this.classeSocial;
    }

    public float obterDinheiro() {
        return this.dinheiro;
    }

    public boolean possuiDinheiro(float valor) {
        return this.dinheiro >= valor;
    }

    public boolean vaiDesistirPorPreco(float preco) {
        float percentualDinheiro = preco / this.dinheiro;

        switch (this.classeSocial) {
            case BaixaRenda:
                return percentualDinheiro > 0.40f;
            case MediaRenda:
                return percentualDinheiro > 0.25f;
            case AltaRenda:
                return percentualDinheiro > 0.15f;
            default:
                return false;
        }
    }

    public boolean pagar(float valor) {
        if (this.dinheiro >= valor) {
            this.dinheiro -= valor;
            return true;
        }
        return false;
    }

    public void adicionarDinheiro(float valor) {
        this.dinheiro += valor;
    }

    public void adicionarIngredienteQueGosta(Ingrediente ingrediente) {
        if (!this.ingredientesQueGosta.contains(ingrediente)) {
            this.ingredientesQueGosta.add(ingrediente);
        }

        this.ingredientesQueDesgosta.remove(ingrediente);
    }

    public void adicionarIngredienteQueDesgosta(Ingrediente ingrediente) {
        if (!this.ingredientesQueDesgosta.contains(ingrediente)) {
            this.ingredientesQueDesgosta.add(ingrediente);
        }

        this.ingredientesQueGosta.remove(ingrediente);
    }

    public void removerIngredienteQueGosta(Ingrediente ingrediente) {
        this.ingredientesQueGosta.remove(ingrediente);
    }

    public void removerIngredienteQueDesgosta(Ingrediente ingrediente) {
        this.ingredientesQueDesgosta.remove(ingrediente);
    }

    public ArrayList<Ingrediente> obterIngredientesQueGosta() {
        return new ArrayList<Ingrediente>(this.ingredientesQueGosta);
    }

    public ArrayList<Ingrediente> obterIngredientesQueDesgosta() {
        return new ArrayList<Ingrediente>(this.ingredientesQueDesgosta);
    }

    public void adicionarAlimentoQueGosta(Alimento alimento) {
        if (!this.alimentosQueGosta.contains(alimento)) {
            this.alimentosQueGosta.add(alimento);
        }

        this.alimentosQueDesgosta.remove(alimento);
    }

    public void adicionarAlimentoQueDesgosta(Alimento alimento) {
        if (!this.alimentosQueDesgosta.contains(alimento)) {
            this.alimentosQueDesgosta.add(alimento);
        }

        this.alimentosQueGosta.remove(alimento);
    }

    public void removerAlimentoQueGosta(Alimento alimento) {
        this.alimentosQueGosta.remove(alimento);
    }

    public void removerAlimentoQueDesgosta(Alimento alimento) {
        this.alimentosQueDesgosta.remove(alimento);
    }

    public ArrayList<Alimento> obterAlimentosQueGosta() {
        return new ArrayList<Alimento>(this.alimentosQueGosta);
    }

    public ArrayList<Alimento> obterAlimentosQueDesgosta() {
        return new ArrayList<Alimento>(this.alimentosQueDesgosta);
    }

    public float calcularRatingParaAlimento(Alimento alimento) {
        float rating = 0.5f; // Rating neutro inicial

        for (Alimento alimentoQueGosta : this.alimentosQueGosta) {
            if (alimentoQueGosta.obterNome().equals(alimento.obterNome())) {
                return 0.9f;
            }
        }

        for (Alimento alimentoQueDesgosta : this.alimentosQueDesgosta) {
            if (alimentoQueDesgosta.obterNome().equals(alimento.obterNome())) {
                return 0.1f;
            }
        }

        ArrayList<Ingrediente> ingredientesDoAlimento = alimento.obterIngredientes();

        int totalIngredientes = ingredientesDoAlimento.size();
        int ingredientesQueGosta = 0;
        int ingredientesQueDesgosta = 0;

        for (Ingrediente ingrediente : ingredientesDoAlimento) {
            for (Ingrediente ingredienteQueGosta : this.ingredientesQueGosta) {
                if (ingredienteQueGosta.obterNome().equals(ingrediente.obterNome())) {
                    ingredientesQueGosta++;
                    break;
                }
            }

            for (Ingrediente ingredienteQueDesgosta : this.ingredientesQueDesgosta) {
                if (ingredienteQueDesgosta.obterNome().equals(ingrediente.obterNome())) {
                    ingredientesQueDesgosta++;
                    break;
                }
            }
        }

        if (totalIngredientes > 0) {
            float percentualGosta = (float) ingredientesQueGosta / totalIngredientes;
            float percentualDesgosta = (float) ingredientesQueDesgosta / totalIngredientes;

            rating = 0.5f + (percentualGosta * 0.4f) - (percentualDesgosta * 0.4f);
            rating = Math.max(0.0f, Math.min(1.0f, rating));
        }

        return rating;
    }

    public boolean gostaDoIngrediente(Ingrediente ingrediente) {
        for (Ingrediente ing : this.ingredientesQueGosta) {
            if (ing.obterNome().equals(ingrediente.obterNome())) {
                return true;
            }
        }
        return false;
    }

    public boolean desgostaDoIngrediente(Ingrediente ingrediente) {
        for (Ingrediente ing : this.ingredientesQueDesgosta) {
            if (ing.obterNome().equals(ingrediente.obterNome())) {
                return true;
            }
        }
        return false;
    }

    public boolean gostaDoAlimento(Alimento alimento) {
        for (Alimento ali : this.alimentosQueGosta) {
            if (ali.obterNome().equals(alimento.obterNome())) {
                return true;
            }
        }
        return false;
    }

    public boolean desgostaDoAlimento(Alimento alimento) {
        for (Alimento ali : this.alimentosQueDesgosta) {
            if (ali.obterNome().equals(alimento.obterNome())) {
                return true;
            }
        }
        return false;
    }
}