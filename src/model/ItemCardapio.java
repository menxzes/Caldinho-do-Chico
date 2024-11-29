package model;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemCardapio {
    private String nome;
    private String tipo;
    private float preco;
    private static List<ItemCardapio> itensCardapio = new ArrayList<>();

    public ItemCardapio(String nome, String tipo, float preco) {
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
    }

    public static void inicializarCardapio() {
        // PETISCOS
        adicionarItensCategoria("\033[1mPetiscos\033[0m", 
            new String[]{"Mini Coxinhas (8 unidades)", "Torresmo", "Batata Frita com Filé", "Macaxeira Frita", "Croquetes de Costela (8 unidades)"}, 
            new float[]{30.00f, 20.00f, 45.00f, 15.00f, 40.00f});

        // BEBIDAS
        adicionarItensCategoria("\033[1mBebidas\033[0m", 
            new String[]{"Cerveja 600ml", "Cachaças (Dose)", "Sucos (Copo)", "Refrigerantes", "Água"}, 
            new float[]{17.00f, 10.00f, 10.00f, 14.00f, 6.00f});

        // DRINKS
        adicionarItensCategoria("\033[1mDrinks\033[0m", 
            new String[]{"Caipirinha", "Gin Tônica", "Mojito", "Margarita", "Caipiroska"}, 
            new float[]{15.00f, 20.00f, 18.00f, 25.00f, 17.00f});

        // CALDINHOS
        adicionarItensCategoria("\033[1mCaldinhos\033[0m", 
            new String[]{"Caldinho de Feijão", "Caldinho de Camarão", "Caldinho de Marisco", "Caldinho de Peixe", "Caldinho de Caldeirada"}, 
            new float[]{13.00f, 17.00f, 15.00f, 13.00f, 20.00f});

        // SOBREMESAS
        adicionarItensCategoria("\033[1mSobremesas\033[0m", 
            new String[]{"Brownie com Sorvete", "Pudim", "Pavê", "Mousses", "Torta de Limão (Fatia)"}, 
            new float[]{18.00f, 13.00f, 16.00f, 13.00f, 15.00f});

        // PRATOS PRINCIPAIS
        adicionarItensCategoria("\033[1mPratos Principais\033[0m", 
            new String[]{"Feijoada", "Moqueca de Peixe", "Lasanha", "Carne de Sol de Bode", "Filé à Parmegiana"}, 
            new float[]{60.00f, 60.00f, 40.00f, 55.00f, 60.00f});
    }

    // Método auxiliar para adicionar itens ao cardápio por categoria
    private static void adicionarItensCategoria(String categoria, String[] nomes, float[] precos) {
        for (int i = 0; i < nomes.length; i++) {
            itensCardapio.add(new ItemCardapio(nomes[i], categoria, precos[i]));
        }
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

    public void salvarItem() {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "INSERT INTO itens_cardapio (nome, preco, tipo) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setFloat(2, preco);
            stmt.setString(3, tipo);
            stmt.executeUpdate();

            System.out.println("Item " + nome + " adicionado ao cardápio.");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar item no cardápio: " + e.getMessage());
        }
    }

    public static void carregarCardapio() {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "SELECT * FROM itens_cardapio";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                System.out.println("Item: " + rs.getString("nome") + " - R$" + rs.getFloat("preco"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao carregar o cardápio: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return nome + "- R$" + preco;
    }

    public static List<ItemCardapio> getItensCardapio() {
        return itensCardapio;
    }
}
