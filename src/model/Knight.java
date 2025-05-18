package model;

class Knight extends Piece {

    public Knight(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = Math.abs(y - getY());
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}
