package model;

class Bishop extends Piece {

    public Bishop(int x, int y, boolean color) {
        super(x, y, color);
    }

    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = Math.abs(y - getY());

        return dx == dy && !(dx == 0 && dy == 0); // Movimento diagonal, não parado
    }

    public void move(int x, int y) {
        if ((x > 7) || (x < 0) || (y > 7) || (y < 0)) {
            System.out.println("Out of bounds!");
            return;
        }

        if (canMoveTo(x, y)) {
            setPosition(x, y);
        } else {
            System.out.println("Movimento inválido para bispo");
        }
    }
}
