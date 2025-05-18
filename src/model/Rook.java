package model;

class Rook extends Piece {

    public Rook(int x, int y, boolean color) {
        super(x, y, color);
    }

    public boolean canMoveTo(int x, int y) {
//         Verifica se é movimento reto horizontal ou vertical
        return (x == getX() || y == getY()) && !(x == getX() && y == getY());
    }

    public void move(int x, int y) {
        if((x > 7) || (x < 0) || (y < 0) || (y > 7)) {
            System.out.println("Out of bounds!");
            return;
        }

        if(canMoveTo(x, y)) {
            setPosition(x, y);
        } else {
            System.out.println("Movimento inválido para torre");
        }
    }
}
