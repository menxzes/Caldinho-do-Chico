package view;

import model.*;

import java.util.Scanner;
import java.util.InputMismatchException;

import static model.ItemCardapio.inicializarCardapio;
import static model.Pedido.criarComanda;

public class Menu {

    private Atendente atendente = new Atendente("Guilherme", "A1");
    private Mesa mesas = new Mesa();

    public Menu() {
        inicializarCardapio();
    }

    public void exibirMenuPrincipal() {
        Scanner scanner = new Scanner(System.in);
        Pedido pedido = null;
        GestaoFinanceira gestaoFinanceira = new GestaoFinanceira();

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
                        criarComanda(scanner);
                        break;
                    case 2:
                        if (pedido != null) {
                            pedido.exibirItens();
                        } else {
                            System.out.println("Nenhuma comanda foi criada ainda.");
                        }
                        break;
                    case 3:
                        mesas.verificarDisponibilidadeMesas();
                        break;
                    case 4:
                        if (pedido != null) {
                            if (atendente == null) {
                                System.out.println("Erro: Atendente não está definido.");
                            } else {
                                SistemaDeImpressao.imprimirComanda(pedido, atendente, gestaoFinanceira);
                            }
                        } else {
                            System.out.println("Nenhuma comanda disponível para impressão.");
                        }
                        break;
                    case 5:
                        gestaoFinanceira.exibirReceitaTotal();
                        break;
                    case 6:
                        System.out.println("Encerrando o sistema...");
                        return;
                    default:
                        System.out.println("Opção inválida! Por favor, escolha uma opção entre 1 e 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
            }
        }
    }
}
