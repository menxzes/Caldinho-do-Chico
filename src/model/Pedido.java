package model;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private String id;
    private List<ItemCardapio> itens;
    private float valorTotal;

    public Pedido(String id) {
        this.id = id;
        this.itens = new ArrayList<>();
        this.valorTotal = 0;
    }

    public void adicionarItem(ItemCardapio item) {
        itens.add(item);
        valorTotal += item.getPreco();
        System.out.println(item.getNome() + " adicionado à comanda. Total: R$" + valorTotal);
    }

    public void exibirItens() {
        System.out.println("Itens na comanda:");
        for (ItemCardapio item : itens) {
            System.out.println("- " + item);
        }
        System.out.println("Valor total: R$" + valorTotal);
    }

    public List<ItemCardapio> getItens() {
        return itens;
    }

    public void setItens(List<ItemCardapio> itens) {
        this.itens = itens;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }
}
