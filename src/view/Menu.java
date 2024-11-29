package view;

import model.*;

import java.util.Scanner;
import java.util.InputMismatchException;

import static model.ItemCardapio.inicializarCardapio;
import static model.Mesa.verificarEAtualizarDisponibilidadeMesa;
import static model.Pedido.criarComanda;
import static model.Pedido.salvarPedido;

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
                System.out.println("\n\033[1;33m=== BEM-VINDO AO CALDINHO DO CHICO! ===");
                System.out.println("1. Criar Comanda");
                System.out.println("2. Ver Comanda");
                System.out.println("3. Verificar Disponibilidade de Mesas");
                System.out.println("4. Imprimir Comanda");
                System.out.println("5. Gerar Relatório Financeiro");
                System.out.println("6. Sair\033[0m");
                System.out.print("\033[1mEscolha uma opção: \033[0m");

                int escolha = scanner.nextInt();
                scanner.nextLine();

                switch (escolha) {
                    case 1:
                        // Criar nova comanda e salvar no banco
                        pedido = criarComanda(scanner);
                        break;
                    case 2:
                        // Ver Comanda: consultar itens do pedido no banco
                        if (pedido != null) {
                            pedido.exibirItens();
                        } else {
                            System.out.println("\033[31mNenhuma comanda foi criada ainda.\033[0m");
                        }
                        break;
                    case 3:
                        // Verificar mesas disponíveis
                        mesas.verificarDisponibilidadeMesas();
                        break;
                    case 4:
                        // Imprimir comanda e registrar no banco
                        if (pedido != null) {
                            if (atendente == null) {
                                System.out.println("\033[31mErro: Atendente não está definido.\033[0m");
                            } else {
                                SistemaDeImpressao.imprimirComanda(pedido, atendente, gestaoFinanceira);
                            }
                        } else {
                            System.out.println("\033[31mNenhuma comanda disponível para impressão.\033[0m");
                        }
                        break;
                    case 5:
                        // Gerar Relatório Financeiro: busca dados do banco para mostrar a receita
                        gestaoFinanceira.exibirReceitaTotal();
                        break;
                    case 6:
                        // Sair do sistema
                    	String saindo = "Encerrando o sistema...";
                    	for (char caractere : saindo.toCharArray()) {
                            System.out.print(caractere);
                            try {
                                Thread.sleep(70); // Animação bonitinha pra sair do sistema
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt(); 
                            }
                        }
                        return;
                    default:
                        System.out.println("\033[31mOpção inválida!\033[0m Por favor, escolha uma opção entre 1 e 6.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\033[31mEntrada inválida! \033[0mPor favor, insira um número.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("\033[31mOcorreu um erro inesperado: \033[0m" + e.getMessage());
            }
        }
    }
}
