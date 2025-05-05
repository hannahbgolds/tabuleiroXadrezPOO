package model;

public class Piece {
	
	int xSqr;
	int ySqr;
	boolean clr;
	
	public Piece(int x, int y, boolean color) {
		if((x>8)||(x<0)||(y<0)||(x>8)) {
			System.out.println("Falha ao criar peca");
			return;
		}
		this.xSqr = x;
		this.ySqr = y;
		this.clr = color;
		
	}
}
