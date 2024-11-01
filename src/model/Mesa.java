package model;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Mesa {
	public boolean[] getMesasOcupadas;
	private int idMesa;
	private boolean disponivel;
	private boolean[] mesasOcupadas = new boolean[10];

	public Mesa(int idMesa, boolean disponivel) {
		this.idMesa = idMesa;
		this.disponivel = disponivel;
	}

	public Mesa() {;
	}

	public boolean todasMesasOcupadas() {
		for (boolean ocupada : mesasOcupadas) {
			if (!ocupada) {
				return false;
			}
		}
		return true;
	}

	public void verificarDisponibilidadeMesas() {
		System.out.println("\n--- DISPONIBILIDADE DE MESAS ---");
		for (int i = 0; i < mesasOcupadas.length; i++) {
			String status = mesasOcupadas[i] ? "Ocupada" : "Disponível";
			System.out.printf("Mesa %d: %s\n", i + 1, status);
		}
	}

	public int selecionarMesa(Scanner scanner) {
		if (todasMesasOcupadas()) {
			System.out.println("Não há mesas disponíveis.");
			return -1;
		}

		System.out.println("\n--- SELEÇÃO DE MESA ---");
		verificarDisponibilidadeMesas();

		while (true) {
			try {
				System.out.print("Escolha uma mesa (1-10): ");
				int mesa = scanner.nextInt();
				scanner.nextLine();

				if (mesa < 1 || mesa > 10) {
					System.out.println("Número de mesa inválido! Escolha entre 1 e 10.");
				} else if (mesasOcupadas[mesa - 1]) {
					System.out.print("Mesa já atendida! Deseja abrir uma nova comanda para essa mesa? (S/N): ");
					String resposta = scanner.nextLine().trim().toUpperCase();

					if (resposta.equals("S")) {
						return mesa;
					} else {
						System.out.println("Seleção de mesa cancelada.");
					}
				} else {
					mesasOcupadas[mesa - 1] = true; // Marca a mesa como ocupada
					return mesa;
				}
			} catch (InputMismatchException e) {
				System.out.println("Entrada inválida! Por favor, insira um número.");
				scanner.nextLine();
			}
		}
	}

	public int getIdMesa() {
		return idMesa;
	}

	public boolean isDisponivel() {
		return disponivel;
	}

	public void setDisponivel() {
		this.disponivel = true;
	}
}
