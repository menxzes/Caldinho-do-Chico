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

    public void exibirReceitaTotal() {
        System.out.println("\033[1;33m> Receita Total: R$" + receitaTotal%.2f + "\n");
        System.out.println("Pedidos registrados:\033[0m");
        for (Pedido pedido : pedidos) {
            System.out.println(pedido);
        }
    }
}