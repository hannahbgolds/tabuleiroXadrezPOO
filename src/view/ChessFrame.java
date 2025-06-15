package view;

import model.ChessFacade;

import javax.swing.*;

public class ChessFrame extends JFrame {

    public ChessFrame(ChessFacade modelo, InterfaceFacade interfaceGUI) {
        setTitle("INF1636 - Xadrez");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Cria o painel do tabuleiro e conecta com a InterfaceFacade
        ChessPanel panel = new ChessPanel(modelo);
        interfaceGUI.setPainelDoTabuleiro(panel);

        add(panel); // adiciona o tabuleiro à janela principal

        pack(); // ajusta tamanho da janela com base no painel
        setLocationRelativeTo(null); // centraliza a janela
        setVisible(true); // exibe a janela
    }
}
