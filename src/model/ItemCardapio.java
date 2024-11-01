package model;

import java.util.ArrayList;
import java.util.List;

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
        itensCardapio.add(new ItemCardapio("Mini Coxinhas (8 unidades)", "Petiscos", 30.00f));
        itensCardapio.add(new ItemCardapio("Torresmo", "Petiscos", 20.00f));
        itensCardapio.add(new ItemCardapio("Batata Frita com Filé", "Petiscos", 45.00f));
        itensCardapio.add(new ItemCardapio("Macaxeira Frita", "Petiscos", 15.00f));
        itensCardapio.add(new ItemCardapio("Croquetes de Costela (8 unidades)", "Petiscos", 40.00f));

        // BEBIDAS
        itensCardapio.add(new ItemCardapio("Cerveja 600ml", "Bebidas", 17.00f));
        itensCardapio.add(new ItemCardapio("Cachaças (Dose)", "Bebidas", 10.00f));
        itensCardapio.add(new ItemCardapio("Sucos (Copo)", "Bebidas", 10.00f));
        itensCardapio.add(new ItemCardapio("Refrigerantes", "Bebidas", 14.00f));
        itensCardapio.add(new ItemCardapio("Água", "Bebidas", 6.00f));

        // DRINKS
        itensCardapio.add(new ItemCardapio("Caipirinha", "Drinks", 15.00f));
        itensCardapio.add(new ItemCardapio("Gin Tônica", "Drinks", 20.00f));
        itensCardapio.add(new ItemCardapio("Mojito", "Drinks", 18.00f));
        itensCardapio.add(new ItemCardapio("Margarita", "Drinks", 25.00f));
        itensCardapio.add(new ItemCardapio("Caipiroska", "Drinks", 17.00f));

        // CALDINHOS
        itensCardapio.add(new ItemCardapio("Caldinho de Feijão", "Caldinhos", 13.00f));
        itensCardapio.add(new ItemCardapio("Caldinho de Camarão", "Caldinhos", 17.00f));
        itensCardapio.add(new ItemCardapio("Caldinho de Marisco", "Caldinhos", 15.00f));
        itensCardapio.add(new ItemCardapio("Caldinho de Peixe", "Caldinhos", 13.00f));
        itensCardapio.add(new ItemCardapio("Caldinho de Caldeirada", "Caldinhos", 20.00f));

        // SOBREMESAS
        itensCardapio.add(new ItemCardapio("Brownie com Sorvete", "Sobremesas", 18.00f));
        itensCardapio.add(new ItemCardapio("Pudim", "Sobremesas", 13.00f));
        itensCardapio.add(new ItemCardapio("Pavê", "Sobremesas", 16.00f));
        itensCardapio.add(new ItemCardapio("Mousses", "Sobremesas", 13.00f));
        itensCardapio.add(new ItemCardapio("Torta de Limão (Fatia)", "Sobremesas", 15.00f));

        // PRATOS PRINCIPAIS
        itensCardapio.add(new ItemCardapio("Feijoada", "Pratos Principais", 60.00f));
        itensCardapio.add(new ItemCardapio("Moqueca de Peixe", "Pratos Principais", 60.00f));
        itensCardapio.add(new ItemCardapio("Lasanha", "Pratos Principais", 40.00f));
        itensCardapio.add(new ItemCardapio("Carne de Sol de Bode", "Pratos Principais", 55.00f));
        itensCardapio.add(new ItemCardapio("Filé à Parmegiana", "Pratos Principais", 60.00f));
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

    public static List<ItemCardapio> getItensCardapio() {
        return itensCardapio;
    }
}
