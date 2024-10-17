public class Atendente {
    private String id;
    private String nome;

    public void fazerComanda(Pedido pedido) {
        System.out.println("Comanda criada: " + pedido.getDescricao());
    }

    public void consultarComanda(Pedido pedido) {
        System.out.println("Pedido: " + pedido.getDescricao());
    }

    public void atualizarComanda(Pedido pedido, String novaDescricao) {
        pedido.setDescricao(novaDescricao);
        System.out.println("Comanda atualizada!");
    }

    public void apagarComanda(Pedido pedido) {
        System.out.println("Comanda apagada.");
    }

    public void indicarMesa(Mesa mesa) {
        System.out.println("Mesa " + mesa.getIdMesa() + " selecionada.");
    }
}
