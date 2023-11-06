package dataBaseReference.Menu;

import java.util.ArrayList;


public class InfoMenu extends MenuFuncs {
    public InfoMenu() {
        optionsMenu = new ArrayList<>();
        optionsMenu.add("1 - Ajuda");
        optionsMenu.add("2 - Sobre");
        optionsMenu.add("3 - Voltar ao menu principal");
    }

    // Método para exibir o menu de informações e lidar com as opções selecionadas.
    @Override
    public void showMenu() {
        System.out.println("\nAções de informações:");

        showOptions();

        int option = askOption();

        switch (option) {
            case 1:
                System.out.println("Este programa permite gerenciar clientes e pedidos. Você pode adicionar, obter e deletar clientes e pedidos. Além disso, você pode visualizar relatórios de clientes e pedidos.");
                break;
            case 2:
                System.out.println("Versão 1.0.0. Desenvolvido por GrupoA4.");  
                break;
        }
        if (option != 3) {
            this.showMenu();
        }
    }
}