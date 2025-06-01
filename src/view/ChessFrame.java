package view;

import javax.swing.JFrame;

public class ChessFrame extends JFrame {

    public ChessFrame() {
        setTitle("INF1636 - Xadrez");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Cria o painel de desenho e adiciona na janela
        ChessPanel panel = new ChessPanel();
        add(panel);

        pack(); // ajusta o tamanho com base no painel
        setLocationRelativeTo(null); // centraliza na tela
        setVisible(true);
    }

    public static void main(String[] args) {
        new ChessFrame(); // Inicia o programa
    }
}
