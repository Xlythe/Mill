package com.will.mill;

public class BoardTools{
	public static void setGame(int m){
		Global.setN(m);
		clearBoard();
		Global.setPolyXY(new Posn[Global.getN()][Global.getN()]);
	}
	
	public static boolean makeMove(final int x, final int y, final byte team){
		if(Global.getRunning() && team==1){
			if(Global.getRemove()){
				//Dont remove your own piece
				if(Global.getGameboard()[x][y]!=(byte) team%2+1){
					return false;
				}
				
				//If there's a piece not in a mill, you have to take that first
				if(checkFor3(new Posn(x,y))){
					for(int i=0;i<Global.getN();i++){
						for(int j=0;j<Global.getN();j++){
							if(Global.getGameboard()[i][j]==(byte) team%2+1 && !checkFor3(new Posn(i,j))){
								return false;
							}
						}
					}
				}
				
				Global.setGameboard(x,y,(byte) 0);
	        	Global.setP2BoardPieces(Global.getP2BoardPieces()-1);
	        	Global.setRemove(false);
	        	
	        	return true;
			}
			else if(Global.getP1Pieces()>0){
				if(Global.getGameboard()[x][y]!=0){
					return false;
				}
				
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
					Global.setGameboard(Global.getHighlighted().getX(),Global.getHighlighted().getY(),(byte) team);
				}
				Global.setHighlighted(new Posn(x,y));
				Global.setGameboard(x,y,(byte) 3);
				
				return true;
			}
			else if(Global.getHighlighted()!=null && checkIfValid(Global.getHighlighted(), new Posn(x,y))){
				if(Global.getGameboard()[x][y]!=0){
					return false;
				}
				
				//Move piece
				Global.setGameboard(Global.getHighlighted().getX(),Global.getHighlighted().getY(),(byte) 0);
				Global.setHighlighted(null);
				Global.setGameboard(x,y,team);
				Global.addToMoveList(new Posn(x,y));
				
				return true;
			}
		}
		else if(Global.getRunning() && team==2){
			if(Global.getRemove()){
				//Dont remove your own piece
				if(Global.getGameboard()[x][y]!=(byte) team%2+1){
					return false;
				}
				
				//If there's a piece not in a mill, you have to take that first
				if(checkFor3(new Posn(x,y))){
					for(int i=0;i<Global.getN();i++){
						for(int j=0;j<Global.getN();j++){
							if(Global.getGameboard()[i][j]==(byte) team%2+1 && !checkFor3(new Posn(i,j))){
								return false;
							}
						}
					}
				}
				
				Global.setGameboard(x,y,(byte) 0);
	        	Global.setP1BoardPieces(Global.getP1BoardPieces()-1);
	        	Global.setRemove(false);
	        	
	        	return true;
			}
			else if(Global.getP2Pieces()>0){
				if(Global.getGameboard()[x][y]!=0){
					return false;
				}
				
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
					Global.setGameboard(Global.getHighlighted().getX(),Global.getHighlighted().getY(),(byte) team);
				}
				Global.setHighlighted(new Posn(x,y));
				Global.setGameboard(x,y,(byte) 4);
				
				return true;
			}
			else if(Global.getHighlighted()!=null && checkIfValid(Global.getHighlighted(), new Posn(x,y))){
				if(Global.getGameboard()[x][y]!=0){
					return false;
				}
				
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
		//Quick check
		if(highlighted.getX()!=move.getX() && highlighted.getY()!=move.getY()){
			return false;
		}
		
		//Longer check
		if(highlighted.getX()==0 && highlighted.getY()==0){
			if((move.getX()==3 && move.getY()==0) || (move.getX()==0 && move.getY()==3)){
				return true;
			}
		}
		else if(highlighted.getX()==3 && highlighted.getY()==0){
			if((move.getX()==0 && move.getY()==0) || (move.getX()==6 && move.getY()==0) || (move.getX()==3 && move.getY()==1)){
				return true;
			}
		}
		else if(highlighted.getX()==6 && highlighted.getY()==0){
			if((move.getX()==3 && move.getY()==0) || (move.getX()==6 && move.getY()==3)){
				return true;
			}
		}
		else if(highlighted.getX()==1 && highlighted.getY()==1){
			if((move.getX()==3 && move.getY()==1) || (move.getX()==1 && move.getY()==3)){
				return true;
			}
		}
		else if(highlighted.getX()==3 && highlighted.getY()==1){
			if((move.getX()==3 && move.getY()==0) || (move.getX()==1 && move.getY()==1) || (move.getX()==5 && move.getY()==1) || (move.getX()==2 && move.getY()==3)){
				return true;
			}
		}
		else if(highlighted.getX()==5 && highlighted.getY()==1){
			if((move.getX()==3 && move.getY()==1) || (move.getX()==5 && move.getY()==3)){
				return true;
			}
		}
		else if(highlighted.getX()==2 && highlighted.getY()==2){
			if((move.getX()==3 && move.getY()==2) || (move.getX()==2 && move.getY()==3)){
				return true;
			}
		}
		else if(highlighted.getX()==3 && highlighted.getY()==2){
			if((move.getX()==3 && move.getY()==1) || (move.getX()==2 && move.getY()==2) || (move.getX()==4 && move.getY()==2)){
				return true;
			}
		}
		else if(highlighted.getX()==4 && highlighted.getY()==2){
			if((move.getX()==3 && move.getY()==2) || (move.getX()==4 && move.getY()==3)){
				return true;
			}
		}
		else if(highlighted.getX()==0 && highlighted.getY()==3){
			if((move.getX()==0 && move.getY()==0) || (move.getX()==1 && move.getY()==3) || (move.getX()==0 && move.getY()==6)){
				return true;
			}
		}
		else if(highlighted.getX()==1 && highlighted.getY()==3){
			if((move.getX()==1 && move.getY()==1) || (move.getX()==0 && move.getY()==3) || (move.getX()==2 && move.getY()==3) || (move.getX()==1 && move.getY()==5)){
				return true;
			}
		}
		else if(highlighted.getX()==2 && highlighted.getY()==3){
			if((move.getX()==2 && move.getY()==2) || (move.getX()==1 && move.getY()==3) || (move.getX()==2 && move.getY()==4)){
				return true;
			}
		}
		else if(highlighted.getX()==4 && highlighted.getY()==3){
			if((move.getX()==4 && move.getY()==2) || (move.getX()==5 && move.getY()==3) || (move.getX()==4 && move.getY()==4)){
				return true;
			}
		}
		else if(highlighted.getX()==5 && highlighted.getY()==3){
			if((move.getX()==5 && move.getY()==1) || (move.getX()==4 && move.getY()==3) || (move.getX()==6 && move.getY()==3) || (move.getX()==5 && move.getY()==5)){
				return true;
			}
		}
		else if(highlighted.getX()==6 && highlighted.getY()==3){
			if((move.getX()==6 && move.getY()==0) || (move.getX()==5 && move.getY()==3) || (move.getX()==6 && move.getY()==6)){
				return true;
			}
		}
		else if(highlighted.getX()==2 && highlighted.getY()==4){
			if((move.getX()==2 && move.getY()==3) || (move.getX()==3 && move.getY()==4)){
				return true;
			}
		}
		else if(highlighted.getX()==3 && highlighted.getY()==4){
			if((move.getX()==2 && move.getY()==4) || (move.getX()==4 && move.getY()==4) || (move.getX()==3 && move.getY()==5)){
				return true;
			}
		}
		else if(highlighted.getX()==4 && highlighted.getY()==4){
			if((move.getX()==4 && move.getY()==3) || (move.getX()==3 && move.getY()==4)){
				return true;
			}
		}
		else if(highlighted.getX()==1 && highlighted.getY()==5){
			if((move.getX()==1 && move.getY()==3) || (move.getX()==3 && move.getY()==5)){
				return true;
			}
		}
		else if(highlighted.getX()==3 && highlighted.getY()==5){
			if((move.getX()==3 && move.getY()==4) || (move.getX()==1 && move.getY()==5) || (move.getX()==5 && move.getY()==5) || (move.getX()==3 && move.getY()==6)){
				return true;
			}
		}
		else if(highlighted.getX()==5 && highlighted.getY()==5){
			if((move.getX()==5 && move.getY()==3) || (move.getX()==3 && move.getY()==5)){
				return true;
			}
		}
		else if(highlighted.getX()==0 && highlighted.getY()==6){
			if((move.getX()==0 && move.getY()==3) || (move.getX()==3 && move.getY()==6)){
				return true;
			}
		}
		else if(highlighted.getX()==3 && highlighted.getY()==6){
			if((move.getX()==3 && move.getY()==5) || (move.getX()==0 && move.getY()==6) || (move.getX()==6 && move.getY()==6)){
				return true;
			}
		}
		else if(highlighted.getX()==6 && highlighted.getY()==6){
			if((move.getX()==6 && move.getY()==3) || (move.getX()==3 && move.getY()==6)){
				return true;
			}
		}
		
		
		return false;
	}
	
	public static boolean checkFor3(Posn lastMove){
		if(lastMove.getX()==0){
			if(Global.getGameboard()[0][0]==Global.getGameboard()[0][3] && Global.getGameboard()[0][3]==Global.getGameboard()[0][6]){
				return true;
			}
		}
		else if(lastMove.getX()==1){
			if(Global.getGameboard()[1][1]==Global.getGameboard()[1][3] && Global.getGameboard()[1][3]==Global.getGameboard()[1][5]){
				return true;
			}
		}
		else if(lastMove.getX()==2){
			if(Global.getGameboard()[2][2]==Global.getGameboard()[2][3] && Global.getGameboard()[2][3]==Global.getGameboard()[2][4]){
				return true;
			}
		}
		else if(lastMove.getX()==3 && lastMove.getY()<3){
			if(Global.getGameboard()[3][0]==Global.getGameboard()[3][1] && Global.getGameboard()[3][1]==Global.getGameboard()[3][2]){
				return true;
			}
		}
		else if(lastMove.getX()==3 && lastMove.getY()>3){
			if(Global.getGameboard()[3][4]==Global.getGameboard()[3][5] && Global.getGameboard()[3][5]==Global.getGameboard()[3][6]){
				return true;
			}
		}
		else if(lastMove.getX()==4){
			if(Global.getGameboard()[4][2]==Global.getGameboard()[4][3] && Global.getGameboard()[4][3]==Global.getGameboard()[4][4]){
				return true;
			}
		}
		else if(lastMove.getX()==5){
			if(Global.getGameboard()[5][1]==Global.getGameboard()[5][3] && Global.getGameboard()[5][3]==Global.getGameboard()[5][5]){
				return true;
			}
		}
		else if(lastMove.getX()==6){
			if(Global.getGameboard()[6][0]==Global.getGameboard()[6][3] && Global.getGameboard()[6][3]==Global.getGameboard()[6][6]){
				return true;
			}
		}
		
		if(lastMove.getY()==0){
			if(Global.getGameboard()[0][0]==Global.getGameboard()[3][0] && Global.getGameboard()[3][0]==Global.getGameboard()[6][0]){
				return true;
			}
		}
		else if(lastMove.getY()==1){
			if(Global.getGameboard()[1][1]==Global.getGameboard()[3][1] && Global.getGameboard()[3][1]==Global.getGameboard()[5][1]){
				return true;
			}
		}
		else if(lastMove.getY()==2){
			if(Global.getGameboard()[2][2]==Global.getGameboard()[3][2] && Global.getGameboard()[3][2]==Global.getGameboard()[4][2]){
				return true;
			}
		}
		else if(lastMove.getY()==3 && lastMove.getX()<3){
			if(Global.getGameboard()[0][3]==Global.getGameboard()[1][3] && Global.getGameboard()[1][3]==Global.getGameboard()[2][3]){
				return true;
			}
		}
		else if(lastMove.getY()==3 && lastMove.getX()>3){
			if(Global.getGameboard()[4][3]==Global.getGameboard()[5][3] && Global.getGameboard()[5][3]==Global.getGameboard()[6][3]){
				return true;
			}
		}
		else if(lastMove.getY()==4){
			if(Global.getGameboard()[2][4]==Global.getGameboard()[3][4] && Global.getGameboard()[3][4]==Global.getGameboard()[4][4]){
				return true;
			}
		}
		else if(lastMove.getY()==5){
			if(Global.getGameboard()[1][5]==Global.getGameboard()[3][5] && Global.getGameboard()[3][5]==Global.getGameboard()[5][5]){
				return true;
			}
		}
		else if(lastMove.getY()==6){
			if(Global.getGameboard()[0][6]==Global.getGameboard()[3][6] && Global.getGameboard()[3][6]==Global.getGameboard()[6][6]){
				return true;
			}
		}
		
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