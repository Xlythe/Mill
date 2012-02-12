package com.will.mill;

public class GameObject{
	public GameObject(){
		new Thread(new Runnable() {
			public void run() {
				while (Global.getRunning()) {
					if(Global.getCurrentPlayer()==(byte) 1){
		        		Global.getPlayer1().getPlayerTurn();
		        		while(Global.getHighlighted()!=null){
		        			Global.getBoard().postInvalidate();
		        			Global.getPlayer1().getPlayerTurn();
		        		}
		        		
		        		
		        		//Check for 3 in a row
		        		Posn lastMove = Global.getMoveList().get(Global.getMoveList().size()-1);
		        		if(BoardTools.checkFor3(lastMove)){
		        			//TODO Let Player1 remove one of Player2's pieces
		    	        	Global.setP2BoardPieces(Global.getP2BoardPieces()-1);
		        		}
		        		
		        		
		        		//Check for victory
		        		if(BoardTools.checkWinPlayer1()) 
		        			Global.setRunning(false);
		        	}
		        	else if(Global.getCurrentPlayer()==(byte) 2){
		        		Global.getPlayer2().getPlayerTurn();
		        		while(Global.getHighlighted()!=null){
		        			Global.getBoard().postInvalidate();
		        			Global.getPlayer2().getPlayerTurn();
		        		}
		        		
		        		
		        		//Check for 3 in a row
		        		Posn lastMove = Global.getMoveList().get(Global.getMoveList().size()-1);
		        		if(BoardTools.checkFor3(lastMove)){
		        			Global.getBoard().postInvalidate();
		        			
		        			//TODO Let Player2 remove one of Player1's pieces
		    	        	Global.setP1BoardPieces(Global.getP1BoardPieces()-1);
		        		}
		        		
		        		
		        		//Check for victory
		        		if(BoardTools.checkWinPlayer2()) 
		        			Global.setRunning(false);
		        	}
		        	
					
		        	//Update the player, refresh the board
		        	BoardTools.updateCurrentPlayer();
		        	Global.getBoard().postInvalidate();
				}
			}
		}).start();
	}
}
