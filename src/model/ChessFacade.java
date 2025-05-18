package model;

public class ChessFacade {

    private static ChessFacade instance = null;

    private Piece[][] board;
    private boolean currentPlayer; // true = branco, false = preto
    private Piece selectedPiece;

    private ChessFacade() {
        board = new Piece[8][8];
        currentPlayer = true;
        selectedPiece = null;
        setupInitialPosition();
    }

    public static ChessFacade getInstance() {
        if (instance == null) {
            instance = new ChessFacade();
        }
        return instance;
    }

    private void setupInitialPosition() {
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

    public boolean selecionaPeca(int x, int y) {
        if (!posicaoValida(x, y)) return false;

        Piece p = board[x][y];
        if (p != null && p.getColor() == currentPlayer) {
            selectedPiece = p;
            return true;
        }
        return false;
    }

    public boolean selecionaCasa(int x, int y) {
        if (!posicaoValida(x, y) || selectedPiece == null) return false;

        if (selectedPiece instanceof Pawn) {
            ((Pawn) selectedPiece).move(x, y);
        } else if (selectedPiece instanceof Rook) {
            ((Rook) selectedPiece).move(x, y);
        } else if (selectedPiece instanceof Knight) {
            ((Knight) selectedPiece).move(x, y);
        } else if (selectedPiece instanceof Bishop) {
            ((Bishop) selectedPiece).move(x, y);
        } else if (selectedPiece instanceof Queen) {
            ((Queen) selectedPiece).move(x, y);
        } else if (selectedPiece instanceof King) {
            ((King) selectedPiece).move(x, y);
        }

        // Atualiza tabuleiro
        int oldX = selectedPiece.getX();
        int oldY = selectedPiece.getY();
        board[oldX][oldY] = null;
        board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece;

        // Passa a vez
        currentPlayer = !currentPlayer;
        selectedPiece = null;
        return true;
    }

    public Piece getPieceAt(int x, int y) {
        if (!posicaoValida(x, y)) return null;
        return board[x][y];
    }

    private boolean posicaoValida(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}
