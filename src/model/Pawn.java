package model;

class Pawn extends Piece {
    private boolean hasMoved;

    public Pawn(int x, int y, boolean color) {
        super(x, y, color);
        this.hasMoved = false;
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = y - getY();
        int dir = getColor() ? -1 : 1; // branco sobe (-1), preto desce (+1)

        // Movimento reto (avanço simples ou duplo)
        if (x == getX()) {
            if (!hasMoved && dy == 2 * dir) return true;
            if (dy == dir) return true;
        }

        // Movimento diagonal (captura) — verificado no ChessFacade
        if (dx == 1 && dy == dir) {
            return true; // indica que a captura é possível
        }

        return false;
    }

    @Override
    public void move(int x, int y) {
        if ((x > 7) || (x < 0) || (y < 0) || (y > 7)) {
            System.out.println("Out of bounds!");
            return;
        }

        if (canMoveTo(x, y)) {
            setPosition(x, y);
            hasMoved = true;
        } else {
            System.out.println("Movimento inválido para peão");
        }
    }
}
