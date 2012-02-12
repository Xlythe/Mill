package com.will.mill;

public class BoardTools{
	public static void setGame(int m){
		Global.setN(m);
		clearBoard();
		Global.setPolyXY(new Posn[Global.getN()][Global.getN()]);
	}
	
	public static boolean makeMove(final int x, final int y, final byte team){
		if(Global.getRunning() && team==1){
			if(Global.getP1Pieces()>0){
				//Play move
				Global.setGameboard(x,y,team);
	        	Global.addToMoveList(new Posn(x,y));
	        	Global.setP1Pieces(Global.getP1Pieces()-1);
	        	Global.setP1BoardPieces(Global.getP1BoardPieces()+1);
	        	
	        	return true;
			}
			else if(Global.getGameboard()[x][y]==(byte) 1){
				//Highlight move
				if(Global.getHighlighted()!=null){
					Global.setGameboard(Global.getHighlighted().getX(),Global.getHighlighted().getY(),(byte) 0);
				}
				Global.setHighlighted(new Posn(x,y));
				Global.setGameboard(x,y,(byte) 3);
				
				return true;
			}
			else if(Global.getHighlighted()!=null && checkIfValid(Global.getHighlighted(), new Posn(x,y))){
				//Move piece
				Global.setGameboard(Global.getHighlighted().getX(),Global.getHighlighted().getY(),(byte) 0);
				Global.setHighlighted(null);
				Global.setGameboard(x,y,team);
				Global.addToMoveList(new Posn(x,y));
				
				return true;
			}
		}
		else if(Global.getRunning() && team==2){
			if(Global.getP2Pieces()>0){
				//Play move
				Global.setGameboard(x,y,team);
	        	Global.addToMoveList(new Posn(x,y));
	        	Global.setP2Pieces(Global.getP2Pieces()-1);
	        	Global.setP2BoardPieces(Global.getP2BoardPieces()+1);
	        	
	        	return true;
			}
			else if(Global.getGameboard()[x][y]==team){
				//Highlight move
				if(Global.getHighlighted()!=null){
					Global.setGameboard(Global.getHighlighted().getX(),Global.getHighlighted().getY(),(byte) 0);
				}
				Global.setHighlighted(new Posn(x,y));
				Global.setGameboard(x,y,(byte) 4);
				
				return true;
			}
			else if(Global.getHighlighted()!=null && checkIfValid(Global.getHighlighted(), new Posn(x,y))){
				//Move piece
				Global.setGameboard(Global.getHighlighted().getX(),Global.getHighlighted().getY(),(byte) 0);
				Global.setHighlighted(null);
				Global.setGameboard(x,y,team);
				Global.addToMoveList(new Posn(x,y));
				
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean checkIfValid(Posn highlighted, Posn move){
		//TODO Check if a piece can be moved here
		return true;
	}
	
	public static boolean checkFor3(Posn lastMove){
		//TODO Check if the last move formed a 3-in-a-row
		return false;
	}
	
	public static byte[][] teamGrid(){
		return Global.getGameboard();
	}
	
	public static Posn[][] getPolyXY(){
		return Global.getPolyXY();
	}
	
	public static void setPolyXY(int x, int y, Posn cord){
		Global.setPolyXY(x, y, cord);
	}
	
	public static void clearBoard(){
		Global.setGameboard(new byte[Global.getN()][Global.getN()]);
		for(int i=0;i<Global.getN();i++){
			for(int j=0;j<Global.getN();j++){
				Global.getGameboard()[i][j]=0;
			}
		}
	}
	
	public static void clearMoveList(){
		Global.clearMoveList();
	}
	
	public static void updateCurrentPlayer(){
		Global.setCurrentPlayer((byte) (Global.getCurrentPlayer()%2+1));
	}
	
	public static boolean checkWinPlayer1() {
		if(Global.getP2Pieces()+Global.getP2BoardPieces()<3){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean checkWinPlayer2() {
		if(Global.getP1Pieces()+Global.getP1BoardPieces()<3){
			return true;
		}
		else{
			return false;
		}
	}
}