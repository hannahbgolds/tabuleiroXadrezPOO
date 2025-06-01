package model;

class Pawn extends Piece {
    private boolean hasMoved; // Indica se o peão já se moveu (para permitir o avanço de duas casas)
    private static final ChessFacade model = ChessFacade.getInstance();

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
        if (!isInBounds(x, y)) return false;

        int dx = x - getX();
        int dy = y - getY();

        ChessFacade facade = ChessFacade.getInstance();
        Piece destino = facade.getPieceAt(x, y);

        boolean isBranco = getColor();
        int direcao = isBranco ? -1 : 1;

        // CAPTURA: 1 casa na diagonal para frente se houver inimigo
        if (Math.abs(dx) == 1 && dy == direcao) {
            return destino != null && destino.getColor() != isBranco;
        }

        // AVANÇO DE 1 CASA
        if (dx == 0 && dy == direcao && destino == null) {
            return true;
        }

        // AVANÇO DE 2 CASAS (primeiro movimento apenas)
        int linhaInicial = isBranco ? 6 : 1;
        if (dx == 0 && dy == 2 * direcao && getY() == linhaInicial) {
            int meioY = getY() + direcao;
            return destino == null && facade.getPieceAt(x, meioY) == null;
        }

        // Caso contrário, movimento inválido
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

