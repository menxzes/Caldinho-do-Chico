package model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static model.ItemCardapio.getItensCardapio;
import static model.Mesa.verificarEAtualizarDisponibilidadeMesa;

public class Pedido {
    private int mesaId;
    private static List<ItemCardapio> itens = getItensCardapio();
    private List<ItemCardapio> itensPedido = new ArrayList<>();
    private float valorTotal;

    public Pedido(int mesaId, List<ItemCardapio> itensPedido) {
        this.mesaId = mesaId;
        this.itensPedido = itensPedido;
        valorTotal = 0;
    }

    public void adicionarItem(ItemCardapio item) {
        itensPedido.add(item);
        for (ItemCardapio i : itens) {
            valorTotal += item.getPreco();
        }
        atualizarValorTotal(mesaId, valorTotal);
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

    public static Pedido criarComanda(Scanner scanner) {

        System.out.print("Digite o número da mesa escolhida: ");
        int mesaEscolhida = scanner.nextInt();
        scanner.nextLine();

        Pedido pedido = new Pedido(mesaEscolhida);
        System.out.println("Comanda criada para a Mesa " + mesaEscolhida + ".");

        while (true) {
            try {
                System.out.println("\n--- CATEGORIAS DO CARDÁPIO ---");
                System.out.println("1. Petiscos");
                System.out.println("2. Bebidas");
                System.out.println("3. Drinks");
                System.out.println("4. Caldinhos");
                System.out.println("5. Sobremesas");
                System.out.println("6. Pratos Principais");
                System.out.print("Escolha uma categoria: ");

                int escolhaCategoria = scanner.nextInt();
                scanner.nextLine();

                List<ItemCardapio> itensFiltrados = filtrarItensPorCategoria(escolhaCategoria);

                if (itensFiltrados.isEmpty()) {
                    System.out.println("Categoria inválida! Tente novamente.");
                    continue;
                }

                System.out.println("\n--- ITENS DISPONÍVEIS ---");
                for (int i = 0; i < itensFiltrados.size(); i++) {
                    ItemCardapio item = itensFiltrados.get(i);
                    System.out.printf("%d. %s - R$%.2f\n", i + 1, item.getNome(), item.getPreco());
                }

                System.out.print("Escolha um item: ");
                int escolhaItem = scanner.nextInt() - 1;
                scanner.nextLine();

                if (escolhaItem >= 0 && escolhaItem < itensFiltrados.size()) {
                    ItemCardapio itemEscolhido = itensFiltrados.get(escolhaItem);
                    pedido.adicionarItem(itemEscolhido);
                } else {
                    System.out.println("Item inválido! Tente novamente.");
                }

                System.out.print("Deseja adicionar mais um item? (S/N): ");
                String continuar = scanner.nextLine().trim().toUpperCase();

                if (continuar.equals("N")) {
                    break;
                } else if (!continuar.equals("S")) {
                    System.out.println("Opção inválida! Por favor, digite 'S' para sim ou 'N' para não.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
        salvarPedidoNoBanco(pedido);
        verificarEAtualizarDisponibilidadeMesa(mesaEscolhida, pedido);
        return pedido;
    }

    private static List<ItemCardapio> filtrarItensPorCategoria(int categoria) {
        String categoriaEscolhida = switch (categoria) {
            case 1 -> "Petiscos";
            case 2 -> "Bebidas";
            case 3 -> "Drinks";
            case 4 -> "Caldinhos";
            case 5 -> "Sobremesas";
            case 6 -> "Pratos Principais";
            default -> "";
        };

        List<ItemCardapio> itensFiltrados = new ArrayList<>();

        for (ItemCardapio item : itens) {
            if (item.getTipo().equalsIgnoreCase(categoriaEscolhida)) {
                itensFiltrados.add(item);
            }
        }
        return itensFiltrados;
    }

    public static void salvarPedidoNoBanco(Pedido pedido) {
        String query = "INSERT INTO pedidos (mesa_id, valor_total) VALUES (?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, pedido.mesaId);
            stmt.setDouble(2, pedido.valorTotal);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pedido salvo no banco de dados com sucesso.");
            } else {
                System.out.println("Erro ao salvar o pedido no banco de dados.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao salvar o pedido: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public static boolean salvarPedido(int mesaId, double valorTotal) {
        String insertQuery = "INSERT INTO pedidos (mesa_id, valor_total) VALUES (?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement insertStatement = conn.prepareStatement(insertQuery)) {

            insertStatement.setInt(1, mesaId);
            insertStatement.setDouble(2, valorTotal);

            int rowsAffected = insertStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pedido para a mesa " + mesaId + " foi salvo com sucesso!");
                return true;
            } else {
                System.out.println("Falha ao salvar o pedido.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao salvar o pedido: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean atualizarValorTotal(int pedidoId, float novoValorTotal) {
        String updateQuery = "UPDATE pedidos SET valor_total = ? WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement updateStatement = conn.prepareStatement(updateQuery)) {

            updateStatement.setDouble(1, novoValorTotal);
            updateStatement.setInt(2, pedidoId);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Valor total do pedido " + pedidoId + " atualizado com sucesso!");
                return true;
            } else {
                System.out.println("Falha ao atualizar o valor total do pedido.");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o valor total do pedido: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
