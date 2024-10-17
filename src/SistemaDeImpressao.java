public class SistemaDeImpressao {

	public static void imprimirComanda(Pedido pedido, Atendente atendente) {
		System.out.println("\n--- COMANDA ---");
		System.out.println("Atendente: " + atendente.getNome() + " (ID: " + atendente.getId() + ")");
		System.out.println("Pedido: " + pedido.getValorTotal());

		pedido.exibirItens();
		System.out.println("\nObrigado pela preferência!");
	}
}
