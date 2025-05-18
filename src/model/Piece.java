package model;

/**
 * Classe abstrata que representa uma peça genérica de xadrez.
 * Define a posição e cor da peça, bem como regras básicas de movimento.
 */
public abstract class Piece {
    private int xSqr;       // Posição horizontal no tabuleiro (coluna)
    private int ySqr;       // Posição vertical no tabuleiro (linha)
    private boolean clr;    // Cor da peça: true para branco, false para preto

    /**
     * Construtor da peça.
     *
     * @param x     Coluna inicial.
     * @param y     Linha inicial.
     * @param color Cor da peça: true para branco, false para preto.
     */
    public Piece(int x, int y, boolean color) {
        if (!isInBounds(x, y)) {
            throw new IllegalArgumentException("Posição inválida para peça.");
        }
        this.xSqr = x;
        this.ySqr = y;
        this.clr = color;
    }

    public int getX() {
        return xSqr;
    }

    public int getY() {
        return ySqr;
    }

    public boolean getColor() {
        return clr;
    }

    /**
     * Atualiza a posição da peça.
     * Deve ser usado internamente, não deve ignorar validações de movimento.
     */
    protected void setPosition(int x, int y) {
        this.xSqr = x;
        this.ySqr = y;
    }

    /**
     * Verifica se a posição está dentro dos limites do tabuleiro (8x8).
     */
    protected boolean isInBounds(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    /**
     * Define a regra de movimentação específica da peça.
     *
     * @param x Coluna de destino.
     * @param y Linha de destino.
     * @return true se o movimento for válido.
     */
    public abstract boolean canMoveTo(int x, int y);

    /**
     * Tenta mover a peça para a posição (x, y).
     * Valida limites e regra de movimento da peça.
     */
    public void move(int x, int y) {
        if (!isInBounds(x, y)) {
            System.out.println("Out of bounds!");
            return;
        }
        if (canMoveTo(x, y)) {
            setPosition(x, y);
            pósMovimento();
        } else {
            System.out.println("Movimento inválido para " + getClass().getSimpleName());
        }
    }

    /**
     * Método opcional chamado após um movimento bem-sucedido.
     * Subclasses podem sobrescrever para realizar ações específicas.
     */
    protected void pósMovimento() {
        // Por padrão, não faz nada
    }
}
