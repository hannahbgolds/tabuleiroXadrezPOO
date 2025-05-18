package model;

class Piece {
    private int xSqr;
    private int ySqr;
    private boolean clr;

    public Piece(int x, int y, boolean color) {
        if((x > 7) || (x < 0) || (y < 0) || (y > 7)) {
            System.out.println("Falha ao criar peca");
            return;
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
}

