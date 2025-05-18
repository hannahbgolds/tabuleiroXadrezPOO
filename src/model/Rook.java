package model;

class Rook extends Piece {

    public Rook(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        return (x == getX() || y == getY()) && !(x == getX() && y == getY());
    }
}
