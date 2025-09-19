package restaurante.erros;

public class FuncionarioNaoEncontradoErro extends Exception {
    public FuncionarioNaoEncontradoErro(String message) {
        super(message);
    }
    
    public FuncionarioNaoEncontradoErro(String message, Throwable cause) {
        super(message, cause);
    }
}