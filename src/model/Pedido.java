package model;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static model.ItemCardapio.getItensCardapio;

public class Pedido {
    private String mesa;
    private static int contadorId = 0;
    private int id;
    private static int idMesa;
    private static List<ItemCardapio> itens = getItensCardapio();
    private static float valorTotal;
    private int mesaId;

    public Pedido(String mesa) {
        this.mesa = mesa;
        this.id = ++contadorId;;
        this.valorTotal = 0;
    }

    public Pedido() {
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

    public static Pedido criarComanda(Scanner scanner) {
        Mesa mesas = new Mesa();

        int mesaEscolhida = mesas.selecionarMesa(scanner);

        if (mesaEscolhida == -1) {
            System.out.println("Não há mesas disponíveis.");
            return null;
        }

        int idMesa = mesaEscolhida;

        Pedido pedido = new Pedido("Mesa " + mesaEscolhida);
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

    public void limparItens() {
        this.itens = null;
        this.valorTotal = 0;
        System.out.println("Comanda finalizada e limpa.");
    }

    public static float getValorTotal() {
        return valorTotal;
    }

    public static void salvarPedido() {
        //verificar ou criar a mesa no banco
        Mesa mesa = new Mesa();
        if (!mesa.verificarEAtualizarDisponibilidadeMesa(idMesa)) {
            System.out.println("Erro ao salvar pedido: não foi possível criar/verificar a mesa.");
            return;
        }

        //salvamento do pedido
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "INSERT INTO pedidos (mesa_id, valor_total) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idMesa);
            stmt.setFloat(2, getValorTotal());
            stmt.executeUpdate();
            System.out.println("Pedido da Mesa " + idMesa + " registrado no banco.");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar pedido: " + e.getMessage());
        }
    }

    public void atualizarValorTotal(float novoValor) {
        try (Connection conn = DataBaseConnection.getConnection()) {
            String sql = "UPDATE pedidos SET valor_total = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setFloat(1, novoValor);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            this.valorTotal = novoValor;

            System.out.println("Valor total do pedido atualizado.");
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o pedido: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        String result = "Pedido" + id + " - Valor total: R$" + valorTotal;
        return result;
    }
}
