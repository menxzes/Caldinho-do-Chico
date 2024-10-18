package model;

import java.util.ArrayList;
import java.util.List;

public class GestaoFinanceira {

    private float receitaTotal;
    private List<Pedido> pedidos;

    public GestaoFinanceira() {;
        this.receitaTotal = 0;
        this.pedidos = new ArrayList<>();
    }

    public void adicionarReceita(Pedido pedido) {
        this.receitaTotal += pedido.getValorTotal();
        this.pedidos.add(pedido);
    }

    public float getReceitaTotal() {
        return receitaTotal;
    }

    public void exibirReceitaTotal() {
        System.out.println("Receita Total: R$" + receitaTotal + "\n");
        System.out.println("Pedidos registrados:");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }
}