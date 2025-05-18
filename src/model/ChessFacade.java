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
    
    public static void resetInstanceForTests() {
        instance = null;
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
    
    private boolean isPathClear(int x1, int y1, int x2, int y2) {
        int dx = Integer.compare(x2, x1);
        int dy = Integer.compare(y2, y1);

        int cx = x1 + dx;
        int cy = y1 + dy;

        while (cx != x2 || cy != y2) {
            if (board[cx][cy] != null) {
                return false; // tem peça no caminho
            }
            cx += dx;
            cy += dy;
        }
        return true;
    }


    public boolean selecionaCasa(int x, int y) {
        if (!posicaoValida(x, y) || selectedPiece == null) return false;

        // Regra de movimento básica da peça
        if (!selectedPiece.canMoveTo(x, y)) return false;

        // Verifica se há peça do mesmo jogador no destino
        Piece destino = board[x][y];
        if (destino != null && destino.getColor() == selectedPiece.getColor()) return false;

        // Verifica caminho livre para peças que precisam
        boolean precisaVerificarCaminho =
            selectedPiece instanceof Rook ||
            selectedPiece instanceof Bishop ||
            selectedPiece instanceof Queen ||
            (selectedPiece instanceof Pawn && x == selectedPiece.getX()); // avanço vertical

        if (precisaVerificarCaminho) {
            if (!isPathClear(selectedPiece.getX(), selectedPiece.getY(), x, y)) return false;
        }

        // Salva posição antiga e move
        int oldX = selectedPiece.getX();
        int oldY = selectedPiece.getY();
        selectedPiece.move(x, y);
        board[oldX][oldY] = null;
        board[selectedPiece.getX()][selectedPiece.getY()] = selectedPiece;

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
