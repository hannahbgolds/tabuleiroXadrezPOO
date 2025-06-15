package view;

import model.Observador;
import model.ChessFacade;

import javax.swing.*;
import java.awt.*;

public class InterfaceFacade implements Observador {

    private ChessPanel painelDoTabuleiro;

    public InterfaceFacade() {
        // Inicialmente pode estar vazio, ou receber o painel depois
    }

    public void setPainelDoTabuleiro(ChessPanel painel) {
        this.painelDoTabuleiro = painel;
    }

    @Override
    public void atualizar() {
        if (painelDoTabuleiro != null) {
            painelDoTabuleiro.repaint();  // Redesenha o tabuleiro com o novo estado
            System.out.println("Tabuleiro atualizado pela InterfaceFacade.");
        }
    }

    public void mostrarMensagem(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Aviso", JOptionPane.INFORMATION_MESSAGE);
    }

    public void exibirMenuPromocao(int x, int y, boolean isBranco) {
        String[] opcoes = {"Rainha", "Torre", "Bispo", "Cavalo"};
        String escolha = (String) JOptionPane.showInputDialog(
                null,
                "Escolha a peça para promoção:",
                "Promoção de Peão",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                "Rainha"
        );

        if (escolha == null) escolha = "Rainha";

        // Atualiza a peça diretamente no modelo
        ChessFacade modelo = ChessFacade.getInstance();
        modelo.adicionarPeca(escolha, x, y, isBranco);

        modelo.notificarObservadores();  // Para refletir imediatamente na GUI
    }
}
