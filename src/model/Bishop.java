package model;

class Bishop extends Piece {

    public Bishop(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        return dx != 0 && dx == Math.abs(y - getY());
    }
}
