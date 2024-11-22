package model;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static model.DataBaseConnection.connection;


public class Mesa {
	public boolean[] getMesasOcupadas;
	private int idMesa;
	private boolean disponivel;
	private boolean[] mesasOcupadas = new boolean[10];

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

	public boolean verificarOuCriarMesa(int idMesa) {
		try (Connection conn = DataBaseConnection.getConnection()) {
			String sqlCheck = "SELECT COUNT(*) FROM mesas WHERE id = ?";
			PreparedStatement stmtCheck = conn.prepareStatement(sqlCheck);
			stmtCheck.setInt(1, idMesa);
			ResultSet rs = stmtCheck.executeQuery();

			if (rs.next() && rs.getInt(1) == 0) {
				// A mesa não existe, então vamos criá-la
				try {
					String sql = "INSERT INTO mesas (id, disponivel) VALUES (?, ?) ON DUPLICATE KEY UPDATE id=id";
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.setInt(1, idMesa);
					statement.setBoolean(2, false); // Valor padrão para 'disponível'
					statement.executeUpdate();
					System.out.println("Mesa verificada/criada com sucesso.");
				} catch (SQLException e) {
					System.out.println("Erro ao verificar ou criar mesa: " + e.getMessage());
				}
			}
			return true;
		} catch (SQLException e) {
			System.out.println("Erro ao verificar ou criar mesa: " + e.getMessage());
			return false;
		}
	}

	public void salvarMesa() {
		try (Connection conn = DataBaseConnection.getConnection()) {
			String sql = "INSERT INTO mesas (disponivel) VALUES (?)";
			PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			stmt.setBoolean(1, disponivel);
			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				this.idMesa = rs.getInt(1);
			}

			System.out.println("Mesa " + idMesa + " registrada no banco.");
		} catch (SQLException e) {
			System.out.println("Erro ao salvar mesa: " + e.getMessage());
		}
	}

	public void atualizarDisponibilidade(boolean novoStatus) {
		try (Connection conn = DataBaseConnection.getConnection()) {
			String sql = "UPDATE mesas SET disponivel = ? WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setBoolean(1, novoStatus);
			stmt.setInt(2, idMesa);
			stmt.executeUpdate();
			this.disponivel = novoStatus;

			System.out.println("Disponibilidade da mesa " + idMesa + " atualizada.");
		} catch (SQLException e) {
			System.out.println("Erro ao atualizar mesa: " + e.getMessage());
		}
	}

	public boolean verificarEAtualizarDisponibilidadeMesa(int mesaId) {
		String selectQuery = "SELECT disponivel FROM mesas WHERE id = ?";
		String updateQuery = "UPDATE mesas SET disponivel = false WHERE id = ?";

		try (Connection conn = DataBaseConnection.getConnection();
			 PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
			 PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {

			selectStatement.setInt(1, mesaId);

			try (ResultSet resultSet = selectStatement.executeQuery()) {
				if (resultSet.next()) {
					// Pegar o valor da coluna "disponivel"
					boolean disponivel = resultSet.getBoolean("disponivel");

					if (disponivel) {
						System.out.println("A mesa " + mesaId + " está marcada como disponível. Atualizando para atendida...");
						// Atualizar a disponibilidade para false
						updateStatement.setInt(1, mesaId);
						int rowsAffected = updateStatement.executeUpdate();

						if (rowsAffected > 0) {
							System.out.println("A mesa " + mesaId + " foi marcada como atendida.");
						} else {
							System.out.println("Falha ao atualizar a mesa " + mesaId + ".");
						}
					} else {
						System.out.println("A mesa " + mesaId + " já foi atendida.");
						System.out.println("Deseja criar uma nova comanda para esta mesa? (s/n): ");
						Scanner scanner = new Scanner(System.in);
						String input = scanner.nextLine().trim().toLowerCase();

						if (input.equals("s")) {
							System.out.println("Nova comanda criada para a mesa " + mesaId + ".");
							return true;
						} else if (input.equals("n")) {
							System.out.println("Nenhuma nova comanda foi criada.");
							return false;
						} else {
							System.out.println("Entrada inválida. Operação cancelada.");
							return false;
						}
					}
					return true;
				} else {
					System.out.println("Mesa com ID " + mesaId + " não encontrada no banco de dados.");
					return false;
				}
			}

		} catch (Exception e) {
			System.out.println("Erro ao verificar ou atualizar a disponibilidade da mesa: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
}
