package restaurante.objetos;

import restaurante.objetos.Pessoa;

public class Funcionario extends Pessoa {
    protected String cargo;
    protected int matricula;

    public Funcionario(String nome, String cargo, int matricula) {
        super(nome);
        this.cargo = cargo;
        this.matricula = matricula;
    }

    @Override
    public String toString() {
        String aux = super.toString() + " Cargo: " + cargo + " |Matricula: " + matricula;
        return aux;
    }
}
