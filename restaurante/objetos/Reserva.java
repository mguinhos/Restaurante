package restaurante.objetos;

class Reserva {
    protected String data;
    protected Cliente cliente_Resposnavel;
    protected Mesa mesa;

    public Reserva(String data, Cliente cliente_Resposnavel, Mesa mesa) {
        this.data = data;
        this.cliente_Resposnavel = cliente_Resposnavel;
        this.mesa = mesa;
    }

    @Override
    public String toString(){
        String aux = "data: " + data + "Cliente Responsavel: " + cliente_Resposnavel;
        return aux;
    }
}