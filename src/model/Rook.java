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
        // Movimento válido se for na mesma linha ou coluna, mas não permanecendo na mesma posição
        return (x == getX() || y == getY()) && !(x == getX() && y == getY());
    }
}

