package model;

class Knight extends Piece {

    public Knight(int x, int y, boolean color) {
        super(x, y, color);
    }

    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = Math.abs(y - getY());

        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    public void move(int x, int y) {
        if ((x > 7) || (x < 0) || (y > 7) || (y < 0)) {
            System.out.println("Out of bounds!");
            return;
        }

        if (canMoveTo(x, y)) {
            setPosition(x, y);
        } else {
            System.out.println("Movimento inválido para cavalo");
        }
    }
}
