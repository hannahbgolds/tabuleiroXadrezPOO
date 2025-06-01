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
        Rook rook = new Rook(getX(), getY(), getColor());
        Bishop bishop = new Bishop(getX(), getY(), getColor());

        return rook.canMoveTo(x, y) || bishop.canMoveTo(x, y);
    }

}
