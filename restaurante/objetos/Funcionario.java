package restaurante.objetos;

import restaurante.objetos.Pessoa;

public class Funcionario extends Pessoa {
    protected String cargo;
    protected int ID;

    public Funcionario(String nome, String cargo, int matricula) {
        super(nome);
        this.cargo = cargo;
        this.ID = matricula;
    }

    @Override
    public String toString() {
        String aux = super.toString() + " Cargo: " + cargo + " - ID: " + ID;
        
        return aux;
    }
}
