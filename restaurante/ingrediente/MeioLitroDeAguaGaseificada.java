package restaurante.ingrediente;

public final class MeioLitroDeAguaGaseificada extends Ingrediente {
    @Override
    public String obterNome() {
        return "Meio Litro de Agua Gaseificada";
    }

    @Override
    public float obterPreco() {
        return 0.10f;
    }
}