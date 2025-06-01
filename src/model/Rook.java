package model;

class Rook extends Piece {

    /**
     * Cria uma nova Torre com posição inicial e cor.
     *
     * @param x     Coluna inicial no tabuleiro.
     * @param y     Linha inicial no tabuleiro.
     * @param color true para branco, false para preto.
     */
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
}

