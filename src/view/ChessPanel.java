package view;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.awt.Point;

import controller.GameController;
import model.ChessFacade;

public class ChessPanel extends Canvas {

    public static final int CELL_SIZE = 64;
    private static final int BOARD_SIZE = 8;

    private final ChessFacade model;
    private final GameController controller;

    private final Map<String, Image> imagens = new HashMap<>();
    private List<Point> casasValidas = null;

    // Construtor padrão para compatibilidade (caso alguém use ainda)
    public ChessPanel() {
        this(ChessFacade.getInstance()); // delega para o principal
    }

    // Construtor principal usado no ChessFrame
    public ChessPanel(ChessFacade modelo) {
        this.model = modelo;
        this.controller = new GameController();

        setSize(CELL_SIZE * BOARD_SIZE, CELL_SIZE * BOARD_SIZE);
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
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        // Desenha o tabuleiro
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if ((row + col) % 2 == 0) {
                    g2.setColor(new Color(240, 217, 181)); // claro
                } else {
                    g2.setColor(new Color(181, 136, 99)); // escuro
                }
                g2.fillRect(col * CELL_SIZE, row * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        // Destaca a peça selecionada
        if (controller.estaEsperandoDestino()) {
            int origemX = controller.getOrigemX();
            int origemY = controller.getOrigemY();
            g2.setColor(new Color(255, 255, 0, 128)); // amarelo semi-transparente
            g2.fillRect(origemX * CELL_SIZE, origemY * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Destaca casas válidas
        g2.setColor(new Color(0, 255, 0, 128)); // verde semi-transparente
        for (Point p : controller.getCasasAlcancaveis()) {
            g2.fillRect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Desenha as peças
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

    // Carrega e armazena imagens por ID
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

    public void setCasasValidas(List<Point> casas) {
        this.casasValidas = casas;
    }
}
