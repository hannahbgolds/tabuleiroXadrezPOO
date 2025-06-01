package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class ChessFacade {
    private static ChessFacade instance;

    private Piece[][] board = new Piece[8][8];
    private Piece selectedPiece = null;
    private boolean whiteTurn = true; // true = branco, false = preto

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

        int fromX = selectedPiece.getX();
        int fromY = selectedPiece.getY();

        // Verifica se pode se mover para a posição
        if (!selectedPiece.canMoveTo(x, y)) return false;

        Piece destino = board[x][y];
        if (destino != null && destino.getColor() == selectedPiece.getColor()) {
            return false; // não pode capturar peça da mesma cor
        }

        // Atualiza o tabuleiro: move a peça e limpa a casa antiga
        board[fromX][fromY] = null;
        selectedPiece.move(x, y);              // atualiza posição interna primeiro
        board[x][y] = selectedPiece;           // depois grava no tabuleiro


        // Troca o turno e limpa seleção
        whiteTurn = !whiteTurn;
        selectedPiece = null;
        
        boolean emCheque = isKingInCheck(whiteTurn);
        if (emCheque) {
            System.out.println("Cheque no rei " + (whiteTurn ? "branco" : "preto") + "!");
        }


        return true;
    }

    private boolean isInBounds(int x, int y) {
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
    
    private boolean isSquareUnderAttack(int x, int y, boolean byColor) {
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

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (selectedPiece.canMoveTo(x, y)) {
                    Piece destino = board[x][y];
                    if (destino == null || destino.getColor() != selectedPiece.getColor()) {
                        validMoves.add(new Point(x, y));
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

    
    
}
