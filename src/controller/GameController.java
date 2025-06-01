package controller;

import model.ChessFacade;

public class GameController {
    private ChessFacade model;

    public GameController() {
        this.model = ChessFacade.getInstance();
    }

    public void tratarClique(int col, int row) {
        System.out.println("Clique recebido: " + col + "," + row);

        // Exemplo mínimo de lógica:
        // Se uma peça ainda não foi selecionada → seleciona
        // Se já tiver uma selecionada → tenta movimentar
        // (isso você vai implementar depois)
    }
}

