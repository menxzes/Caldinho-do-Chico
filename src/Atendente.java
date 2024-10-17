public class Atendente {
    private String nome;
    private String id;

    public Atendente(String nome, String id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getId() {
        return id;
    }

    public void exibirInformacoes() {
        System.out.println("Atendente: " + nome + " (ID: " + id + ")");
    }
}
