package model;

class Knight extends Piece {

    /**
     * Cria um novo Cavalo com posição inicial e cor.
     *
     * @param x     Coluna inicial no tabuleiro.
     * @param y     Linha inicial no tabuleiro.
     * @param color true para branco, false para preto.
     */
    public Knight(int x, int y, boolean color) {
        super(x, y, color);
    }

    /**
     * Verifica se o Cavalo pode se mover para a posição (x, y).
     * O movimento deve seguir o padrão em "L": 2x1 ou 1x2.
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     * @return true se o movimento for válido, false caso contrário.
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = Math.abs(y - getY());
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}

