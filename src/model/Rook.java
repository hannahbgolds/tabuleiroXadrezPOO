package model;

class Rook extends Piece {

    public Rook(int x, int y, boolean color) {
        super(x, y, color);
    }

    @Override
    public boolean canMoveTo(int x, int y) {
        return (x == getX() || y == getY()) && !(x == getX() && y == getY());
    }

    @Override
    public void move(int x, int y) {
        if ((x > 7) || (x < 0) || (y > 7) || (y < 0)) {
            System.out.println("Out of bounds!");
            return;
        }

        if (canMoveTo(x, y)) {
            setPosition(x, y);
        } else {
            System.out.println("Movimento inválido para torre");
        }
    }
}
