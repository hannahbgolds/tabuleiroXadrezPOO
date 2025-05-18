package model;

public abstract class Piece {
    private int xSqr;
    private int ySqr;
    private boolean clr;

    public Piece(int x, int y, boolean color) {
        if (!isInBounds(x, y)) {
            throw new IllegalArgumentException("Posição inválida para peça.");
        }
        this.xSqr = x;
        this.ySqr = y;
        this.clr = color;
    }

    public int getX() {
        return xSqr;
    }

    public int getY() {
        return ySqr;
    }

    public boolean getColor() {
        return clr;
    }

    protected void setPosition(int x, int y) {
        this.xSqr = x;
        this.ySqr = y;
    }

    protected boolean isInBounds(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public abstract boolean canMoveTo(int x, int y);

    public void move(int x, int y) {
        if (!isInBounds(x, y)) {
            System.out.println("Out of bounds!");
            return;
        }
        if (canMoveTo(x, y)) {
            setPosition(x, y);
            pósMovimento(); // opcionalmente sobrescrito
        } else {
            System.out.println("Movimento inválido para " + getClass().getSimpleName());
        }
    }

    protected void pósMovimento() {
        // Subclasses podem sobrescrever
    }
}
