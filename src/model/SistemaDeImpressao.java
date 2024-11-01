package model;

public class SistemaDeImpressao {

	public static void imprimirComanda(Pedido pedido, Atendente atendente, GestaoFinanceira gestaoFinanceira) {
		System.out.println("\n--- IMPRESSÃO DA COMANDA ---");
		System.out.println("Atendente: " + atendente.getNome() + " (ID: " + atendente.getId() + ")");
		System.out.println("Pedido: " + pedido.getValorTotal());

		pedido.exibirItens();
		gestaoFinanceira.adicionarReceita(pedido);
	}
}
