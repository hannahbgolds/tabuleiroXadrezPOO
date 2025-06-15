package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import view.InterfaceFacade;

public class ChessFacade implements Observado {
    private static ChessFacade instance;

    private Piece[][] board = new Piece[8][8];
    private Piece selectedPiece = null;
    private boolean whiteTurn = true; // true = branco, false = preto
    private final List<Observador> observadores = new ArrayList<>();

    private ChessFacade() {
        inicializaTabuleiro();
    }

    public static ChessFacade getInstance() {
        if (instance == null) {
            instance = new ChessFacade();
        }
        return instance;
    }

    public static void resetInstanceForTests() {
        instance = new ChessFacade();
    }

    // Inicializa todas as peças no tabuleiro
    private void inicializaTabuleiro() {
        // Peões
        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn(i, 1, false); // pretos
            board[i][6] = new Pawn(i, 6, true);  // brancos
        }

        // Torres
        board[0][0] = new Rook(0, 0, false);
        board[7][0] = new Rook(7, 0, false);
        board[0][7] = new Rook(0, 7, true);
        board[7][7] = new Rook(7, 7, true);

        // Cavalos
        board[1][0] = new Knight(1, 0, false);
        board[6][0] = new Knight(6, 0, false);
        board[1][7] = new Knight(1, 7, true);
        board[6][7] = new Knight(6, 7, true);

        // Bispos
        board[2][0] = new Bishop(2, 0, false);
        board[5][0] = new Bishop(5, 0, false);
        board[2][7] = new Bishop(2, 7, true);
        board[5][7] = new Bishop(5, 7, true);

        // Rainhas
        board[3][0] = new Queen(3, 0, false);
        board[3][7] = new Queen(3, 7, true);

        // Reis
        board[4][0] = new King(4, 0, false);
        board[4][7] = new King(4, 7, true);
    }

    // Retorna a peça na posição (x, y) se houver
    public Piece getPieceAt(int x, int y) {
        if (!isInBounds(x, y)) return null;
        return board[x][y];
    }

    // Seleciona a peça na posição informada, se for da cor da vez
    public boolean selecionaPeca(int x, int y) {
        if (!isInBounds(x, y)) return false;

        Piece p = board[x][y];
        if (p != null && p.getColor() == whiteTurn) {
            selectedPiece = p;
            return true;
        }

        selectedPiece = null;
        return false;
    }

    // Tenta mover a peça selecionada para a nova posição
    public boolean selecionaCasa(int x, int y) {
        if (selectedPiece == null || !isInBounds(x, y)) return false;

        // ✅ Nova validação: só permite casas válidas mesmo em situações de cheque
        List<Point> validas = getCasasValidasParaPecaSelecionada();
        boolean permitido = validas.stream().anyMatch(p -> p.x == x && p.y == y);
        if (!permitido) return false;

        int fromX = selectedPiece.getX();
        int fromY = selectedPiece.getY();

        Piece destino = board[x][y];
        if (destino != null && destino.getColor() == selectedPiece.getColor()) {
            return false; // não pode capturar peça da mesma cor
        }

        // Atualiza o tabuleiro: move a peça e limpa a casa antiga
        board[fromX][fromY] = null;
        selectedPiece.move(x, y);              // atualiza posição interna primeiro
        board[x][y] = selectedPiece;           // depois grava no tabuleiro

        promoverPeao(x, y);

        // Troca o turno e limpa seleção
        whiteTurn = !whiteTurn;
        selectedPiece = null;

        boolean emCheque = isKingInCheck(whiteTurn);
        if (emCheque) {
            System.out.println("Cheque no rei " + (whiteTurn ? "branco" : "preto") + "!");
        }

        notificarObservadores();

        // Verifica fim de jogo
        if (isCheckmate(!whiteTurn)) {
            for (Observador obs : observadores) {
                if (obs instanceof InterfaceFacade gui) {
                    gui.mostrarMensagem("Xeque-mate! O jogador " + (whiteTurn ? "branco" : "preto") + " venceu!");
                }
            }
        } else if (isStalemate(!whiteTurn)) {
            for (Observador obs : observadores) {
                if (obs instanceof InterfaceFacade gui) {
                    gui.mostrarMensagem("Empate por congelamento (stalemate).");
                }
            }
        }

        return true;
    }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }
    
    private Piece findKing(boolean color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board[x][y];
                if (p instanceof King && p.getColor() == color) {
                    return p;
                }
            }
        }
        return null;
    }
    
    public boolean isSquareUnderAttack(int x, int y, boolean byColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j];
                if (p != null && p.getColor() == byColor && p.canMoveTo(x, y)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isKingInCheck(boolean color) {
        Piece king = findKing(color);
        if (king == null) return false;

        return isSquareUnderAttack(king.getX(), king.getY(), !color);
    }
    

 // Apenas para uso em testes!
    public void limparTabuleiro() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y] = null;
            }
        }
        selectedPiece = null;
        whiteTurn = true;
        
        notificarObservadores();
    }

    // Apenas para testes — adiciona peça genérica
    public void adicionarPeca(String tipo, int x, int y, boolean cor) {
        switch (tipo.toLowerCase()) {
            case "rei": board[x][y] = new King(x, y, cor); break;
            case "rainha": board[x][y] = new Queen(x, y, cor); break;
            case "torre": board[x][y] = new Rook(x, y, cor); break;
            case "bispo": board[x][y] = new Bishop(x, y, cor); break;
            case "cavalo": board[x][y] = new Knight(x, y, cor); break;
            case "peao": board[x][y] = new Pawn(x, y, cor); break;
            default:
                throw new IllegalArgumentException("Tipo de peça inválido: " + tipo);
        }
        
        notificarObservadores();
    }
    
    public String getPieceIdAt(int x, int y) {
        Piece p = getPieceAt(x, y);
        if (p == null) return null;

        String cor = p.getColor() ? "white" : "black";
        String tipo = p.getClass().getSimpleName().toLowerCase(); // exemplo: "pawn"

        return tipo + "_" + cor;
    }
    
    public List<Point> getCasasValidasParaPecaSelecionada() {
        List<Point> validMoves = new ArrayList<>();

        if (selectedPiece == null) return validMoves;

        boolean emCheque = isKingInCheck(selectedPiece.getColor());

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (selectedPiece.canMoveTo(x, y)) {
                    Piece destino = board[x][y];
                    if (destino == null || destino.getColor() != selectedPiece.getColor()) {
                        // ✅ Aqui: se estiver em cheque, só pode mover se eliminar o cheque
                        if (!emCheque || movimentoRemoveCheque(selectedPiece, x, y)) {
                            validMoves.add(new Point(x, y));
                        }
                    }
                }
            }
        }

        return validMoves;
    }
    
    public boolean isPathClear(int x1, int y1, int x2, int y2) {
        int dx = Integer.compare(x2 - x1, 0);
        int dy = Integer.compare(y2 - y1, 0);

        int cx = x1 + dx;
        int cy = y1 + dy;

        while (cx != x2 || cy != y2) {
            if (getPieceAt(cx, cy) != null) return false;
            cx += dx;
            cy += dy;
        }
        return true;
    }

    public void promoverPeao(int x, int y) {
        Piece p = getPieceAt(x, y);
        if (!(p instanceof Pawn)) return;

        boolean isBranco = p.getColor();
        int linhaFinal = isBranco ? 0 : 7;

        if (p.getY() != linhaFinal) return;

        // Janela Swing de escolha
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

        Piece novaPeca = switch (escolha.toLowerCase()) {
            case "torre" -> new Rook(x, y, isBranco);
            case "bispo" -> new Bishop(x, y, isBranco);
            case "cavalo" -> new Knight(x, y, isBranco);
            default -> new Queen(x, y, isBranco);
        };

        board[x][y] = novaPeca;
        System.out.println("Peão promovido a: " + escolha);
        
        notificarObservadores();
    }
    
    private boolean isCheckmate(boolean color) {
        if (!isKingInCheck(color)) return false;

        Piece king = findKing(color);

        // 1. Verifica se o rei pode fugir para alguma casa segura
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = king.getX() + dx;
                int ny = king.getY() + dy;
                if (!isInBounds(nx, ny)) continue;

                Piece destino = getPieceAt(nx, ny);
                if ((destino == null || destino.getColor() != color) && king.canMoveTo(nx, ny)) {
                    // Testa se a casa não está sob ataque
                    Piece original = board[nx][ny];
                    board[king.getX()][king.getY()] = null;
                    board[nx][ny] = king;
                    boolean aindaEmCheque = isKingInCheck(color);
                    board[nx][ny] = original;
                    board[king.getX()][king.getY()] = king;

                    if (!aindaEmCheque) return false;
                }
            }
        }

        // 2. Verifica se alguma peça aliada pode capturar o atacante ou bloquear o ataque
        List<Piece> atacantes = getAtacantesDoRei(color);
        if (atacantes.size() >= 2) return true; // duplo cheque só o rei pode mover

        Piece atacante = atacantes.get(0);
        int ax = atacante.getX(), ay = atacante.getY();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board[x][y];
                if (p != null && p.getColor() == color && !(p instanceof King)) {
                    if (p.canMoveTo(ax, ay)) {
                        return false; // pode capturar o atacante
                    }

                    // pode bloquear? (se ataque não for de cavalo)
                    if (!(atacante instanceof Knight)) {
                        List<Point> caminho = getCaminhoEntre(atacante.getX(), atacante.getY(), king.getX(), king.getY());
                        for (Point ponto : caminho) {
                            if (p.canMoveTo(ponto.x, ponto.y)) return false;
                        }
                    }
                }
            }
        }

        return true; // nenhuma saída
    }
    
 // Retorna a lista de peças que estão atacando o rei da cor indicada
    private List<Piece> getAtacantesDoRei(boolean corRei) {
        List<Piece> atacantes = new ArrayList<>();
        Piece rei = findKing(corRei);
        if (rei == null) return atacantes;

        int x = rei.getX();
        int y = rei.getY();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece p = board[i][j];
                if (p != null && p.getColor() != corRei && p.canMoveTo(x, y)) {
                    atacantes.add(p);
                }
            }
        }

        return atacantes;
    }
    
 // Retorna a lista de casas entre dois pontos em linha reta ou diagonal (exclui início e fim)
    private List<Point> getCaminhoEntre(int x1, int y1, int x2, int y2) {
        List<Point> caminho = new ArrayList<>();

        int dx = Integer.compare(x2 - x1, 0);
        int dy = Integer.compare(y2 - y1, 0);

        int cx = x1 + dx;
        int cy = y1 + dy;

        while (cx != x2 || cy != y2) {
            caminho.add(new Point(cx, cy));
            cx += dx;
            cy += dy;
        }

        return caminho;
    }

    
    public boolean isStalemate(boolean color) {
        if (isKingInCheck(color)) return false;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece p = board[x][y];
                if (p != null && p.getColor() == color) {
                    for (int dx = 0; dx < 8; dx++) {
                        for (int dy = 0; dy < 8; dy++) {
                            if (p.canMoveTo(dx, dy)) {
                                // Simula movimento
                                Piece originalDestino = board[dx][dy];
                                Piece original = board[x][y];

                                board[dx][dy] = p;
                                board[x][y] = null;
                                p.setPosition(dx, dy);

                                boolean emCheque = isKingInCheck(color);

                                // Desfaz movimento
                                board[x][y] = original;
                                board[dx][dy] = originalDestino;
                                p.setPosition(x, y);

                                if (!emCheque) return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    
    private boolean movimentoRemoveCheque(Piece p, int xDestino, int yDestino) {
        int xOrigem = p.getX();
        int yOrigem = p.getY();
        Piece destinoOriginal = board[xDestino][yDestino];

        board[xOrigem][yOrigem] = null;
        board[xDestino][yDestino] = p;
        p.setPosition(xDestino, yDestino); // simula movimento

        boolean aindaEmCheque = isKingInCheck(p.getColor());

        // desfaz movimento
        board[xDestino][yDestino] = destinoOriginal;
        board[xOrigem][yOrigem] = p;
        p.setPosition(xOrigem, yOrigem);

        return !aindaEmCheque;
    }

    @Override
    public void registrarObservador(Observador o) {
        if (!observadores.contains(o)) {
            observadores.add(o);
        }
    }

    @Override
    public void removerObservador(Observador o) {
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores() {
        for (Observador o : observadores) {
            o.atualizar();
        }
    }

    
}


