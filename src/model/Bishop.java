package model;

class Bishop extends Piece {

    /**
     * Cria um novo Bispo com posição inicial e cor.
     *
     * @param x     Coluna inicial.
     * @param y     Linha inicial.
     * @param color true para branco, false para preto.
     */
    public Bishop(int x, int y, boolean color) {
        super(x, y, color);
    }

    /**
     * Verifica se o Bispo pode se mover para a posição (x, y).
     * O movimento válido ocorre apenas em diagonais.
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     * @return true se for um movimento diagonal diferente da posição atual.
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        if (Math.abs(x - getX()) != Math.abs(y - getY())) return false;

        int dx = Integer.compare(x, getX());
        int dy = Integer.compare(y, getY());

        int cx = getX() + dx;
        int cy = getY() + dy;

        while (cx != x && cy != y) {
            if (ChessFacade.getInstance().getPieceAt(cx, cy) != null) return false;
            cx += dx;
            cy += dy;
        }

        return true;
    }

}
