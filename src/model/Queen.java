package model;

class Queen extends Piece {

    public Queen(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = Math.abs(y - getY());

        boolean diagonal = dx == dy;
        boolean vertical = getX() == x;
        boolean horizontal = getY() == y;

        return (diagonal || vertical || horizontal) && !(dx == 0 && dy == 0);
    }
}
