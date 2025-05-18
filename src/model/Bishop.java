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
        int dx = Math.abs(x - getX());
        return dx != 0 && dx == Math.abs(y - getY());
    }
}
