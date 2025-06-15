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
        if (!ChessFacade.getInstance().isInBounds(x, y)) return false;

        int dx = Math.abs(x - this.getX());
        int dy = Math.abs(y - this.getY());

        if (dx <= 1 && dy <= 1) {
            Piece destino = ChessFacade.getInstance().getPieceAt(x, y);

            // Se for peça aliada, não pode
            if (destino != null && destino.getColor() == this.getColor()) return false;

            // Verifica se o destino está sob ataque de outra peça (diferente do atacante)
            if (!ChessFacade.getInstance().isSquareUnderAttack(x, y, !this.getColor())) {
                return true;
            }
        }

        return false;
    }


}

