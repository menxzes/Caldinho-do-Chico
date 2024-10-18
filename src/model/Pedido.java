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
        if (itens == null || itens.isEmpty()) {
            System.out.println("Nenhum item na comanda.");
        } else {
            System.out.println("Itens na comanda:");
            for (ItemCardapio item : itens) {
                System.out.println("- " + item);
            }
            System.out.println("Valor total: R$" + valorTotal);
        }
    }

    public void limparItens() {
        this.itens = null;
        this.valorTotal = 0;
        System.out.println("Comanda finalizada e limpa.");
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
