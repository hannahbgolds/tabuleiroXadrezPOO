package controller;

import model.ChessFacade;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final ChessFacade model = ChessFacade.getInstance();
    private boolean esperandoDestino = false;

    private int origemX = -1, origemY = -1;
    private List<Point> casasAlcancaveis = new ArrayList<>();

    public void tratarClique(int col, int row) {
        if (!esperandoDestino) {
            boolean selecionou = model.selecionaPeca(col, row);
            if (selecionou) {
                esperandoDestino = true;
                origemX = col;
                origemY = row;
                casasAlcancaveis = model.getCasasValidasParaPecaSelecionada();
                System.out.println("Peça selecionada em: " + col + "," + row);
            } else {
                casasAlcancaveis.clear();
                System.out.println("Nenhuma peça válida nessa posição.");
            }
        } else {
            boolean moveu = model.selecionaCasa(col, row);
            if (moveu) {
                esperandoDestino = false;
                casasAlcancaveis.clear();
                System.out.println("Movimento realizado para: " + col + "," + row);
            } else {
                esperandoDestino = false;
                casasAlcancaveis.clear();
                System.out.println("Movimento inválido. Reiniciando jogada.");
            }
        }
    }

    public List<Point> getCasasAlcancaveis() {
        return casasAlcancaveis;
    }

    public boolean estaEsperandoDestino() {
        return esperandoDestino;
    }

    public int getOrigemX() {
        return origemX;
    }

    public int getOrigemY() {
        return origemY;
    }
}

