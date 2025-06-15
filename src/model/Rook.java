package model;

class Rook extends Piece {
    private boolean movido = false; // Novo campo para rastrear se a torre já se moveu

    public Rook(int x, int y, boolean color) {
        super(x, y, color);
    }

    /**
     * Verifica se a Torre pode se mover para a posição (x, y).
     * A Torre se move apenas em linha reta: horizontal ou verticalmente.
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     * @return true se o movimento for válido, false caso contrário.
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        if (x != getX() && y != getY()) return false;

        int dx = Integer.compare(x, getX());
        int dy = Integer.compare(y, getY());

        int cx = getX() + dx;
        int cy = getY() + dy;

        while (cx != x || cy != y) {
            if (ChessFacade.getInstance().getPieceAt(cx, cy) != null) return false;
            cx += dx;
            cy += dy;
        }

        return true;
    }

    // Novo método para verificar se a torre já se moveu
    public boolean hasMoved() {
        return movido;
    }

    // Sobrescreve o método move() para marcar que a torre se moveu
    @Override
    public void move(int x, int y) {
        super.move(x, y);
        movido = true;
    }
}
