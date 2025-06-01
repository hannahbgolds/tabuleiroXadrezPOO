package model;

class King extends Piece {

    /**
     * Cria um novo Rei com posição inicial e cor.
     *
     * @param x     Coluna inicial.
     * @param y     Linha inicial.
     * @param color true para branco, false para preto.
     */
    public King(int x, int y, boolean color) {
        super(x, y, color);
    }

    /**
     * Verifica se o Rei pode se mover para a posição (x, y).
     * O Rei pode se mover uma casa em qualquer direção,
     * desde que a casa de destino não esteja sob ataque.
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     * @return true se o movimento for válido e seguro.
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = Math.abs(y - getY());

        if ((dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0)) {
            ChessFacade chess = ChessFacade.getInstance();

            // Verifica se a casa de destino está ameaçada por inimigo
            boolean ameacada = chess.isSquareUnderAttack(x, y, !getColor());

            return !ameacada;
        }

        return false;
    }
}

