package controller;

import model.ChessFacade;

public class GameController {
    private final ChessFacade model = ChessFacade.getInstance();
    private boolean esperandoDestino = false; // false = esperando selecionar peça

    public void tratarClique(int col, int row) {
        if (!esperandoDestino) {
            boolean selecionou = model.selecionaPeca(col, row);
            if (selecionou) {
                esperandoDestino = true;
                System.out.println("Peça selecionada em: " + col + "," + row);
            } else {
                System.out.println("Nenhuma peça válida nessa posição.");
            }
        } else {
            boolean moveu = model.selecionaCasa(col, row);
            if (moveu) {
                esperandoDestino = false;
                System.out.println("Movimento realizado para: " + col + "," + row);
            } else {
                System.out.println("Movimento inválido. Reiniciando jogada.");
                esperandoDestino = false; // força reinício da seleção
            }
        }
    }
}

