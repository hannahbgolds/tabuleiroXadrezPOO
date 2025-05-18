package test;

import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;
import model.ChessFacade;
import model.Piece;

public class ChessFacadeTest {

    
	@Before
	public void resetGame() {
	    ChessFacade.resetInstanceForTests();
	}
	
	// 1. Peão - válido
    @Test
    public void testPawnValidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(0, 6); // peão branco
        boolean resultado = game.selecionaCasa(0, 4); // anda 2
        assertTrue(resultado);
        Piece p = game.getPieceAt(0, 4);
        assertNotNull(p);
        assertEquals(0, p.getX());
        assertEquals(4, p.getY());
    }

    // 2. Peão - inválido
    @Test
    public void testPawnInvalidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(1, 6);
        boolean resultado = game.selecionaCasa(1, 3); // inválido (já moveu 2)
        assertFalse(resultado);
    }

    // 3. Torre - válido
    @Test
    public void testRookValidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(0, 6); // peão branco
        game.selecionaCasa(0, 5); // move peão

        game.selecionaPeca(0, 1); // peão preto
        game.selecionaCasa(0, 2); // joga com preto para passar a vez

        game.selecionaPeca(0, 7); // torre branca
        boolean resultado = game.selecionaCasa(0, 6); // move
        assertTrue(resultado);
       
    }

    // 4. Torre - inválido
    @Test
    public void testRookInvalidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(0, 7); // torre
        boolean resultado = game.selecionaCasa(1, 6); // diagonal
        assertFalse(resultado);
    }

    // 5. Cavalo - válido
    @Test
    public void testKnightValidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(1, 7); // cavalo
        boolean resultado = game.selecionaCasa(2, 5); // L
        assertTrue(resultado);
    }

    // 6. Cavalo - inválido
    @Test
    public void testKnightInvalidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(1, 7);
        boolean resultado = game.selecionaCasa(1, 5); // reto
        assertFalse(resultado);
    }

    // 7. Bispo - válido
    @Test
    public void testBishopValidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(2, 6); // tira peão da frente
        game.selecionaCasa(2, 5);
        
        game.selecionaPeca(0, 1); // peão preto
        game.selecionaCasa(0, 2); // joga com preto para passar a vez
        
        game.selecionaPeca(2, 7); // bispo branco
        boolean resultado = game.selecionaCasa(4, 5); // movimento diagonal
        assertTrue(resultado);
    }

    // 8. Bispo - inválido
    @Test
    public void testBishopInvalidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(2, 7);
        boolean resultado = game.selecionaCasa(2, 5); // reto
        assertFalse(resultado);
    }

    // 9. Rainha - válido
    @Test
    public void testQueenValidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(3, 6); // tira peão
        game.selecionaCasa(3, 5);
        
        game.selecionaPeca(0, 1); // peão preto
        game.selecionaCasa(0, 2); // joga com preto para passar a vez
        
        game.selecionaPeca(3, 7); // rainha
        boolean resultado = game.selecionaCasa(5, 5); // movimento diagonal
        assertTrue(resultado);
    }

    // 10. Rainha - inválido
    @Test
    public void testQueenInvalidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(3, 7);
        boolean resultado = game.selecionaCasa(2, 5); // L
        assertFalse(resultado);
    }

    // 11. Rei - válido
    @Test
    public void testKingValidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(4, 6);
        game.selecionaCasa(4, 5);
        
        game.selecionaPeca(0, 1); // peão preto
        game.selecionaCasa(0, 2); // joga com preto para passar a vez
        
        game.selecionaPeca(4, 7);
        boolean resultado = game.selecionaCasa(4, 6);
        assertTrue(resultado);
    }

    // 12. Rei - inválido
    @Test
    public void testKingInvalidMove() {
        ChessFacade game = ChessFacade.getInstance();
        game.selecionaPeca(4, 7);
        boolean resultado = game.selecionaCasa(4, 5); // 2 casas
        assertFalse(resultado);
    }

    // 13. Selecionar peça da vez
    @Test
    public void testSelecionarPecaDoJogador() {
        ChessFacade game = ChessFacade.getInstance();
        boolean resultado = game.selecionaPeca(4, 6); // branco
        assertTrue(resultado);
    }

    // 14. Tentar selecionar peça do adversário
    @Test
    public void testSelecionarPecaDoAdversario() {
        ChessFacade game = ChessFacade.getInstance();
        boolean resultado = game.selecionaPeca(4, 1); // preto, vez do branco
        assertFalse(resultado);
    }
    
    @Test
    public void testChequeDireto() {
        ChessFacade game = ChessFacade.getInstance();
        ChessFacade.resetInstanceForTests();

        game.limparTabuleiro();

        game.adicionarPeca("rei", 4, 0, false);     // Rei preto
        game.adicionarPeca("rainha", 4, 2, true);   // Rainha branca

        game.selecionaPeca(4, 2);
        game.selecionaCasa(4, 1); // Rainha ataca o rei preto

        assertTrue("Rei preto deve estar em cheque após ataque direto da rainha",
                game.isKingInCheck(false));
        
    }
    
    @Test
    public void testChequeDescoberto() {
        ChessFacade game = ChessFacade.getInstance();
        ChessFacade.resetInstanceForTests();

        game.limparTabuleiro();

        game.adicionarPeca("rei", 4, 0, false);     // Rei preto
        game.adicionarPeca("torre", 4, 3, true);    // Torre branca
        game.adicionarPeca("peao", 4, 2, true);     // Peão branco bloqueando

        game.selecionaPeca(4, 2);
        game.selecionaCasa(4, 1); // Peão sai da frente → torre ameaça o rei

        assertTrue("Rei preto deve estar em cheque descoberto após movimento do peão",
                game.isKingInCheck(false));
    }

    

}
