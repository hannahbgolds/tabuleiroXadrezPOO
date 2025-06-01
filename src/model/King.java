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
     * O Rei pode se mover uma casa em qualquer direção.
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     * @return true se o movimento for válido.
     */
    @Override
    public boolean canMoveTo(int x, int y) {
        int dx = Math.abs(x - getX());
        int dy = Math.abs(y - getY());

        return (dx <= 1 && dy <= 1) && !(dx == 0 && dy == 0);
    }
 }
