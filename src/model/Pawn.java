package model;

class Pawn extends Piece {
    private boolean hasMoved; // Indica se o peão já se moveu (para permitir o avanço de duas casas)

    /**
     * Cria um novo Peão com posição inicial e cor.
     *
     * @param x     Coluna inicial.
     * @param y     Linha inicial.
     * @param color true para branco, false para preto.
     */
    public Pawn(int x, int y, boolean color) {
        super(x, y, color);
        this.hasMoved = false;
    }

    /**
     * Verifica se o Peão pode se mover para a posição (x, y).
     * O peão anda 1 casa para frente (ou 2 se for o primeiro movimento).
     * Pode capturar peças na diagonal.
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     * @return true se o movimento for válido.
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = y - getY();
        int dir = getColor() ? -1 : 1; // Peões brancos sobem (-1), pretos descem (+1)

        // Movimento de captura na diagonal
        if (dx == 1 && dy == dir) return true;

        // Movimento reto para frente
        if (dx == 0) {
            if (!hasMoved && dy == 2 * dir) return true;
            if (dy == dir) return true;
        }

        return false;
    }

    /**
     * Marca que o Peão já se moveu (para impedir segundo avanço duplo).
     */
    @Override
    protected void pósMovimento() {
        hasMoved = true;
    }
}

