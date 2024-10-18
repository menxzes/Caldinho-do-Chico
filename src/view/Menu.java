package view;

import model.Atendente;
import model.ItemCardapio;
import model.Pedido;
import model.SistemaDeImpressao;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

public class Menu {

    private List<ItemCardapio> itensCardapio;
    private Atendente atendente;
    private boolean[] mesasOcupadas;

    public Menu() {
        itensCardapio = new ArrayList<>();
        inicializarCardapio();
        atendente = new Atendente("Guilherme", "A1");
        mesasOcupadas = new boolean[10];
    }

    private void inicializarCardapio() {
        // PETISCOS
        itensCardapio.add(new ItemCardapio("Mini Coxinhas (8 unidades)", "Petiscos", 30.00f));
        itensCardapio.add(new ItemCardapio("Torresmo", "Petiscos", 20.00f));
        itensCardapio.add(new ItemCardapio("Batata Frita com Filé", "Petiscos", 45.00f));
        itensCardapio.add(new ItemCardapio("Macaxeira Frita", "Petiscos", 15.00f));
        itensCardapio.add(new ItemCardapio("Croquetes de Costela (8 unidades)", "Petiscos", 40.00f));

        // BEBIDAS
        itensCardapio.add(new ItemCardapio("Cerveja 600ml", "Bebidas", 17.00f));
        itensCardapio.add(new ItemCardapio("Cachaças (Dose)", "Bebidas", 10.00f));
        itensCardapio.add(new ItemCardapio("Sucos (Copo)", "Bebidas", 10.00f));
        itensCardapio.add(new ItemCardapio("Refrigerantes", "Bebidas", 14.00f));
        itensCardapio.add(new ItemCardapio("Água", "Bebidas", 6.00f));

        // DRINKS
        itensCardapio.add(new ItemCardapio("Caipirinha", "Drinks", 15.00f));
        itensCardapio.add(new ItemCardapio("Gin Tônica", "Drinks", 20.00f));
        itensCardapio.add(new ItemCardapio("Mojito", "Drinks", 18.00f));
        itensCardapio.add(new ItemCardapio("Margarita", "Drinks", 25.00f));
        itensCardapio.add(new ItemCardapio("Caipiroska", "Drinks", 17.00f));

        // CALDINHOS
        itensCardapio.add(new ItemCardapio("Caldinho de Feijão", "Caldinhos", 13.00f));
        itensCardapio.add(new ItemCardapio("Caldinho de Camarão", "Caldinhos", 17.00f));
        itensCardapio.add(new ItemCardapio("Caldinho de Marisco", "Caldinhos", 15.00f));
        itensCardapio.add(new ItemCardapio("Caldinho de Peixe", "Caldinhos", 13.00f));
        itensCardapio.add(new ItemCardapio("Caldinho de Caldeirada", "Caldinhos", 20.00f));

        // SOBREMESAS
        itensCardapio.add(new ItemCardapio("Brownie com Sorvete", "Sobremesas", 18.00f));
        itensCardapio.add(new ItemCardapio("Pudim", "Sobremesas", 13.00f));
        itensCardapio.add(new ItemCardapio("Pavê", "Sobremesas", 16.00f));
        itensCardapio.add(new ItemCardapio("Mousses", "Sobremesas", 13.00f));
        itensCardapio.add(new ItemCardapio("Torta de Limão (Fatia)", "Sobremesas", 15.00f));

        // PRATOS PRINCIPAIS
        itensCardapio.add(new ItemCardapio("Feijoada", "Pratos Principais", 60.00f));
        itensCardapio.add(new ItemCardapio("Moqueca de Peixe", "Pratos Principais", 60.00f));
        itensCardapio.add(new ItemCardapio("Lasanha", "Pratos Principais", 40.00f));
        itensCardapio.add(new ItemCardapio("Carne de Sol de Bode", "Pratos Principais", 55.00f));
        itensCardapio.add(new ItemCardapio("Filé à Parmegiana", "Pratos Principais", 60.00f));
    }

    public void exibirMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        Pedido pedido = null;

        while (true) {
            try {
                System.out.println("\n--- MENU PRINCIPAL ---");
                System.out.println("1. Criar Comanda");
                System.out.println("2. Ver Comanda");
                System.out.println("3. Verificar Disponibilidade de Mesas");
                System.out.println("4. Imprimir Comanda");
                System.out.println("5. Gerar Relatório Financeiro");
                System.out.println("6. Sair");
                System.out.print("Escolha uma opção: ");

                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        pedido = criarComanda(scanner);
                        break;
                    case 2:
                        if (pedido != null) {
                            pedido.exibirItens();
                        } else {
                            System.out.println("Nenhuma comanda foi criada ainda.");
                        }
                        break;
                    case 3:
                        verificarDisponibilidadeMesas();
                        break;
                    case 4:
                        if (pedido != null) {
                            SistemaDeImpressao.imprimirComanda(pedido, atendente);
                            if (atendente == null) {
                                System.out.println("Erro: Atendente não está definido.");
                            } else {
                                SistemaDeImpressao.imprimirComanda(pedido, atendente);
                            }
                        } else {
                            System.out.println("Nenhuma comanda disponível para impressão.");
                        }
                        break;

                    case 6:
                        System.out.println("Encerrando o sistema...");
                        return;
                    default:
                        System.out.println("Opção inválida! Por favor, escolha uma opção entre 1 e 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }

    private Pedido criarComanda(Scanner scanner) {
        int mesaEscolhida = selecionarMesa(scanner);

        if (mesaEscolhida == -1) {
            System.out.println("Não há mesas disponíveis.");
            return null;
        }

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
                scanner.nextLine(); // Consumir quebra de linha

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
                scanner.nextLine(); // Consumir quebra de linha

                if (escolhaItem >= 0 && escolhaItem < itensFiltrados.size()) {
                    ItemCardapio itemEscolhido = itensFiltrados.get(escolhaItem);
                    pedido.adicionarItem(itemEscolhido);
                } else {
                    System.out.println("Item inválido! Tente novamente.");
                }

                System.out.print("Deseja adicionar mais um item? (s/n): ");
                String continuar = scanner.nextLine().toLowerCase();

                if (!continuar.equals("s")) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.nextLine(); // Limpar o buffer de entrada
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }

        return pedido;
    }

    private List<ItemCardapio> filtrarItensPorCategoria(int categoria) {
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
        for (ItemCardapio item : itensCardapio) {
            if (item.getTipo().equalsIgnoreCase(categoriaEscolhida)) {
                itensFiltrados.add(item);
            }
        }
        return itensFiltrados;
    }

//    private void exibirPorCategoria(int categoria, model.Pedido pedido, Scanner scanner) {
    private int selecionarMesa(Scanner scanner) {
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
                scanner.nextLine(); // Consumir quebra de linha

                if (mesa < 1 || mesa > 10) {
                    System.out.println("Número de mesa inválido! Escolha entre 1 e 10.");
                } else if (mesasOcupadas[mesa - 1]) {
                    System.out.println("Mesa já ocupada! Escolha outra mesa.");
                } else {
                    mesasOcupadas[mesa - 1] = true;
                    return mesa;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.nextLine(); // Limpar o buffer de entrada
            }
        }
    }

    private boolean todasMesasOcupadas() {
        for (boolean ocupada : mesasOcupadas) {
            if (!ocupada) {
                return false;
            }
        }
        return true;
    }

    private void verificarDisponibilidadeMesas() {
        System.out.println("\n--- DISPONIBILIDADE DE MESAS ---");
        for (int i = 0; i < mesasOcupadas.length; i++) {
            String status = mesasOcupadas[i] ? "Ocupada" : "Disponível";
            System.out.printf("Mesa %d: %s\n", i + 1, status);
        }
    }

//    private void exibirPorCategoria(int categoria, Pedido pedido, Scanner scanner) {
//        String[] categorias = {"Petiscos", "Bebidas", "Drinks", "Caldinhos", "Sobremesas", "Pratos Principais"};
//        String categoriaSelecionada = categorias[categoria - 1];
//
//        System.out.println("\n---" + categoriaSelecionada + " ---");
//        for (model.ItemCardapio item : itensCardapio) {
//            if (item.getTipo().equals(categoriaSelecionada)) {
//                System.out.println("- " + item);
//            }
//        }
//
//        System.out.println("Digite o nome do item para adicionar: ");
//        String nomeItem = scanner.nextLine();
//
//        for (model.ItemCardapio item : itensCardapio) {
//            if (item.getNome().equalsIgnoreCase(nomeItem)) {
//                pedido.adicionarItem(item);
//                return;
//            }
//        }
//        System.out.println("Item não encontrado.");
//    }

}
