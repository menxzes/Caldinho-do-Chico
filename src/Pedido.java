public class Pedido {
    private String id;
    private String descricao;

    public Pedido(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void isReady() {
        System.out.println("Pedido pronto para ser servido.");
    }
}
