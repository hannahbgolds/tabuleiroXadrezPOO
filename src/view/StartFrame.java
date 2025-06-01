package view;

import javax.swing.*;
import java.awt.*;

public class StartFrame extends JFrame {

    private JButton criarBotaoEstilizado(String texto, Font fonte, Color fundo, Color textoCor, Dimension tamanho) {
        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                super.paintComponent(g);
                g2.dispose();
            }
        };

        botao.setFont(fonte);
        botao.setBackground(fundo);
        botao.setForeground(textoCor);
        botao.setFocusPainted(false);
        botao.setContentAreaFilled(false); 
        botao.setOpaque(false); 
        botao.setMaximumSize(tamanho);
        botao.setAlignmentX(Component.CENTER_ALIGNMENT);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); 
        return botao;
    }

    public static final int CELL_SIZE = 64;
    public static final int BOARD_SIZE = 8;

    public StartFrame() {
        setTitle("♞ Jogo de Xadrez ♞");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setPreferredSize(new Dimension(CELL_SIZE * BOARD_SIZE, CELL_SIZE * BOARD_SIZE));
        setLayout(new BorderLayout());

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Home");
        JMenuItem novoJogoItem = new JMenuItem("Nova Partida");
        JMenuItem carregarItem = new JMenuItem("Continuar Partida");
        JMenuItem sairItem = new JMenuItem("Sair");

        novoJogoItem.addActionListener(e -> iniciarNovoJogo());
        carregarItem.addActionListener(e -> carregarPartidaSalva());
        sairItem.addActionListener(e -> System.exit(0));

        menu.add(novoJogoItem);
        menu.add(carregarItem);
        menu.addSeparator();
        menu.add(sairItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.setBackground(Color.BLACK);

        JLabel titulo = new JLabel("♜ Jogo de Xadrez ♜");
        titulo.setFont(new Font("Serif", Font.BOLD, 32));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        Font botaoFonte = new Font("Serif", Font.BOLD, 22);
        Color corFundo = new Color(60, 60, 60); 
        Color corTexto = Color.WHITE;
        Dimension buttonSize = new Dimension(300, 50);

        JButton novoJogoBtn = criarBotaoEstilizado("Nova Partida", botaoFonte, corFundo, corTexto, buttonSize);
        JButton carregarBtn = criarBotaoEstilizado("Continuar Partida", botaoFonte, corFundo, corTexto, buttonSize);
        JButton sairBtn = criarBotaoEstilizado("Sair", botaoFonte, corFundo, corTexto, buttonSize);

        novoJogoBtn.addActionListener(e -> iniciarNovoJogo());
        carregarBtn.addActionListener(e -> carregarPartidaSalva());
        sairBtn.addActionListener(e -> System.exit(0));

        mainPanel.add(titulo);
        mainPanel.add(novoJogoBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(carregarBtn);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(sairBtn);

        add(mainPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void iniciarNovoJogo() {
        JOptionPane.showMessageDialog(this, "Novo jogo iniciado!");
    }

    private void carregarPartidaSalva() {
        JOptionPane.showMessageDialog(this, "Continuar a disputar uma partida interrompida...");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartFrame::new);
    }
}
