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
        int dir = getColor() ? -1 : 1;

        if (dx == 1 && dy == dir) return true; // captura
        if (dx == 0) {
            if (!hasMoved && dy == 2 * dir) return true;
            if (dy == dir) return true;
        }

        return false;
    }

    @Override
    protected void pósMovimento() {
        hasMoved = true;
    }
}
