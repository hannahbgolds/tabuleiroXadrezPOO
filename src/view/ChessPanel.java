package view;

import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import controller.GameController;
import model.ChessFacade;

public class ChessPanel extends JPanel {

    public static final int CELL_SIZE = 64;
    private static final int BOARD_SIZE = 8;

    private final ChessFacade model = ChessFacade.getInstance();
    private final GameController controller = new GameController();

    // Armazena as imagens já carregadas para não recarregar sempre
    private final Map<String, Image> imagens = new HashMap<>();

    public ChessPanel() {
        setPreferredSize(new Dimension(CELL_SIZE * BOARD_SIZE, CELL_SIZE * BOARD_SIZE));
        setBackground(Color.WHITE);

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int col = e.getX() / CELL_SIZE;
                int row = e.getY() / CELL_SIZE;
                controller.tratarClique(col, row);
                repaint(); // redesenha após clique
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Desenha o tabuleiro
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    g2.setColor(new Color(240, 217, 181)); // claro
                } else {
                    g2.setColor(new Color(181, 136, 99));  // escuro
                }
                g2.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Desenha as peças com base no model
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                String id = model.getPieceIdAt(x, y); // Ex: "pawn_white"
                if (id != null) {
                    Image img = getImageForId(id);
                    if (img != null) {
                        g2.drawImage(img, x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE, null);
                    }
                }
            }
        }
    }

    // Carrega imagem da peça com base no id ("pawn_white", etc.)
    private Image getImageForId(String id) {
        if (!imagens.containsKey(id)) {
            try {
                imagens.put(id, ImageIO.read(new File("resources/" + id + ".png")));
            } catch (IOException e) {
                System.out.println("Erro ao carregar imagem para: " + id);
                return null;
            }
        }
        return imagens.get(id);
    }
}
