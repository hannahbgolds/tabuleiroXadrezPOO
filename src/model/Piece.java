package model;

public class Piece {
	
	int xSqr;
	int ySqr;
	boolean clr;
	
	public Piece(int x, int y, boolean color) {
		if((x>7)||(x<0)||(y<0)||(y>7)) {
			System.out.println("Falha ao criar peca");
			return;
		}
		this.xSqr = x;
		this.ySqr = y;
		this.clr = color;
		
	}
}
