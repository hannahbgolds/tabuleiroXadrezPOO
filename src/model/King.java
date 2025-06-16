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
        if (!ChessFacade.getInstance().isInBounds(x, y)) return false;

        int dx = Math.abs(x - this.getX());
        int dy = Math.abs(y - this.getY());

        ChessFacade chess = ChessFacade.getInstance();

        // Movimento normal (1 casa em qualquer direção)
        if (dx <= 1 && dy <= 1 && !(dx == 0 && dy == 0)) {
            Piece destino = chess.getPieceAt(x, y);
            if (destino != null && destino.getColor() == this.getColor()) return false;

            boolean ameacada = chess.isSquareUnderAttack(x, y, !getColor());
            return !ameacada;
        }

        // Movimento de roque (2 casas na horizontal, mesma linha)
        if (!movido && dy == 0 && dx == 2) {
            return chess.canCastle(this, x > getX()); // true = roque pequeno, false = roque grande
        }

        return false;
    }
}
