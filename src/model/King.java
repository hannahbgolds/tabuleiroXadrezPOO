package model;

class King extends Piece {
    private boolean movido = false;

    public King(int x, int y, boolean color) {
        super(x, y, color);
    }

    public boolean hasMoved() {
        return movido;
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y);
        movido = true; // Marca que o rei já se moveu
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = Math.abs(y - getY());

        ChessFacade chess = ChessFacade.getInstance();

        // Movimento normal (1 casa em qualquer direção)
        if (dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0)) {
            boolean ameacada = chess.isSquareUnderAttack(x, y, !getColor());
            return !ameacada;
        }

        // Verifica se é um roque
        if (!movido && dy == 0 && dx == 2) {
            return chess.canCastle(this, x > getX()); // true = roque pequeno, false = roque grande
        }

        return false;
    }
}