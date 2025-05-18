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

    /**
     * Tenta mover o Rei para (x, y), validando limites e movimento.
     * Este método sobrescreve a versão da superclasse e não chama pósMovimento().
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     */
    @Override
    public void move(int x, int y) {
        if ((x > 7) || (x < 0) || (y > 7) || (y < 0)) {
            System.out.println("Out of bounds!");
            return;
        }

        if (canMoveTo(x, y)) {
            setPosition(x, y);
        } else {
            System.out.println("Movimento inválido para rei");
        }
    }
}

