package model;

class Queen extends Piece {

    /**
     * Cria uma nova Rainha com posição inicial e cor.
     *
     * @param x     Coluna inicial no tabuleiro.
     * @param y     Linha inicial no tabuleiro.
     * @param color true para branco, false para preto.
     */
    public Queen(int x, int y, boolean color) {
        super(x, y, color);
    }

    /**
     * Verifica se a Rainha pode se mover para a posição (x, y).
     * A Rainha pode se mover em linha reta (horizontal ou vertical)
     * ou em diagonal, qualquer número de casas.
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     * @return true se o movimento for válido, false caso contrário.
     */
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
