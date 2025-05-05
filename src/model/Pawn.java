package model;


public class Pawn extends Piece{
	
	int[] xSeen;
	int[] ySeen;
	boolean hasMoved;
	
	public Pawn(int x, int y, boolean color) {
		super(x,y,color);
		
		this.hasMoved = false;
		seenSquares(x,y);
	}
	
	public void move(int x, int y) {
		if((x>8)||(x<0)||(y<0)||(y>8)) {
			System.out.println("Out of bounds!");
			return;
		}
		
		if(validateX(x) && validateY(y)) {
			if(this.hasMoved==false) {
				this.hasMoved = true;
			}
			seenSquares(x,y);
		}
		else {
			System.out.println("Invalid Coordinates");
		}
		
		
	}
	
	private void seenSquares(int x,int y) {
		
		if(this.hasMoved==true) {
			this.ySeen = new int[] {y+1};
		}
		
		this.xSeen = new int[] {x};
		this.ySeen = new int[] {y+1, y+2};
	}
	
	private boolean validateX(int x) {

		for( int i : xSeen) {
			if(i == x) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean validateY(int y) {

		for( int i : ySeen) {
			if(i == y) {
				return true;
			}
		}
		
		return false;
	}
}
