package model;

public class SistemaDeImpressao {

	public static void imprimirComanda(Pedido pedido, Atendente atendente, GestaoFinanceira gestaoFinanceira) {
		System.out.println("\n\033[1;33m=== IMPRESSÃO DA COMANDA ===\033[0m");
		System.out.println("\033[33mAtendente: " + atendente.getNome() + " (ID: " + atendente.getId() + ")");
		System.out.println("Pedido: " + pedido.getValorTotal() + "\033[0m");

		pedido.exibirItens();
		gestaoFinanceira.adicionarReceita(pedido);
	}
}
