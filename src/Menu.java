import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Menu {

    private List<ItemCardapio> itensCardapio;
    private Atendente atendente;

    public Menu() {
        itensCardapio = new ArrayList<>();
        inicializarCardapio();
        atendente = new Atendente("Guilherme", "A1");

    }

    private void inicializarCardapio() {
        // PETISCOS
        itensCardapio.add(new ItemCardapio("Mini Coxinhas (8 unidades)", "Petiscos", 30));
        itensCardapio.add(new ItemCardapio("Torresmo", "Petiscos", 20));
        itensCardapio.add(new ItemCardapio("Batata Frita com Filé", "Petiscos", 45));
        itensCardapio.add(new ItemCardapio("Macaxeira Frita", "Petiscos", 15));
        itensCardapio.add(new ItemCardapio("Croquetes de Costela (8 unidades)", "Petiscos", 40));

        // BEBIDAS
        itensCardapio.add(new ItemCardapio("Cerveja 600ml", "Bebidas", 17));
        itensCardapio.add(new ItemCardapio("Cachaças (Dose)", "Bebidas", 10));
        itensCardapio.add(new ItemCardapio("Sucos (Copo)", "Bebidas", 10));
        itensCardapio.add(new ItemCardapio("Refrigerantes", "Bebidas", 14));
        itensCardapio.add(new ItemCardapio("Água", "Bebidas", 6));

        // DRINKS
        itensCardapio.add(new ItemCardapio("Caipirinha", "Drinks", 15));
        itensCardapio.add(new ItemCardapio("Gin Tônica", "Drinks", 20));
        itensCardapio.add(new ItemCardapio("Mojito", "Drinks", 18));
        itensCardapio.add(new ItemCardapio("Margarita", "Drinks", 25));
        itensCardapio.add(new ItemCardapio("Caipiroska", "Drinks", 17));

        // CALDINHOS
        itensCardapio.add(new ItemCardapio("Caldinho de Feijão", "Caldinhos", 13));
        itensCardapio.add(new ItemCardapio("Caldinho de Camarão", "Caldinhos", 17));
        itensCardapio.add(new ItemCardapio("Caldinho de Marisco", "Caldinhos", 15));
        itensCardapio.add(new ItemCardapio("Caldinho de Peixe", "Caldinhos", 13));
        itensCardapio.add(new ItemCardapio("Caldinho de Caldeirada", "Caldinhos", 20));

        // SOBREMESAS
        itensCardapio.add(new ItemCardapio("Brownie com Sorvete", "Sobremesas", 18));
        itensCardapio.add(new ItemCardapio("Pudim", "Sobremesas", 13));
        itensCardapio.add(new ItemCardapio("Pavê", "Sobremesas", 16));
        itensCardapio.add(new ItemCardapio("Mousses", "Sobremesas", 13));
        itensCardapio.add(new ItemCardapio("Torta de Limão (Fatia)", "Sobremesas", 15));

        // PRATOS PRINCIPAIS
        itensCardapio.add(new ItemCardapio("Feijoada", "Pratos Principais", 60));
        itensCardapio.add(new ItemCardapio("Moqueca de Peixe", "Pratos Principais", 60));
        itensCardapio.add(new ItemCardapio("Lasanha", "Pratos Principais", 40));
        itensCardapio.add(new ItemCardapio("Carne de Sol de Bode", "Pratos Principais", 55));
        itensCardapio.add(new ItemCardapio("Filé à Parmegiana", "Pratos Principais", 60));
    }

    public void exibirMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        Pedido pedido = new Pedido("001");

        while (true) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Criar Comanda");
            System.out.println("2. Ver Comanda");
            System.out.println("3. Verificar Disponibilidade de Mesas");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarComanda(pedido, scanner);
                    break;
                case 2:
                    pedido.exibirItens();
                    break;
                case 3:
                    System.out.println("Todas as mesas estão disponíveis.");
                    break;
                case 4:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void criarComanda(Pedido pedido, Scanner scanner) {
        while (true) {
            System.out.println("\n--- CATEGORIAS ---");
            System.out.println("1. Petiscos");
            System.out.println("2. Bebidas");
            System.out.println("3. Drinks");
            System.out.println("4. Caldinhos");
            System.out.println("5. Sobremesas");
            System.out.println("6. Pratos Principais");
            System.out.print("Escolha uma categoria: ");
            int categoriaEscolhida = scanner.nextInt();
            scanner.nextLine();

            exibirPorCategoria(categoriaEscolhida, pedido, scanner);

            System.out.println("Deseja adicionar mais um item? (s/n): ");
            String continuar = scanner.nextLine();
            if (!continuar.equalsIgnoreCase("S")) break;
        }
    }

    private void exibirPorCategoria(int categoria, Pedido pedido, Scanner scanner) {
        String[] categorias = {"Petiscos", "Bebidas", "Drinks", "Caldinhos", "Sobremesas", "Pratos Principais"};
        String categoriaSelecionada = categorias[categoria - 1];

        System.out.println("\n---" + categoriaSelecionada + " ---");
        for (ItemCardapio item : itensCardapio) {
            if (item.getTipo().equals(categoriaSelecionada)) {
                System.out.println("- " + item);
            }
        }

        System.out.println("Digite o nome do item para adicionar: ");
        String nomeItem = scanner.nextLine();

        for (ItemCardapio item : itensCardapio) {
            if (item.getNome().equalsIgnoreCase(nomeItem)) {
                pedido.adicionarItem(item);
                return;
            }
        }
        System.out.println("Item não encontrado.");
    }

}
