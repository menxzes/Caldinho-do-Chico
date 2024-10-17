public class ItemCardapio {
    private String nome;
    private String tipo;
    private float preco;

    public ItemCardapio(String nome, String tipo, float preco) {
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public float getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + "- R$" + preco;
    }
}
