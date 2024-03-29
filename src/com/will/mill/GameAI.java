package com.will.mill;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class GameAI implements PlayingEntity { 
	byte team;// 1 is left-right, 2 is top-down
	byte difficalty;
	byte[][] gameBoard;
	int[] n = {BoardTools.teamGrid().length-1,BoardTools.teamGrid().length-2},m = {0,0};//n is the leftmost AI move, m is the rightmost AI move;
	List<List<List<Integer>>> pairs;
	int diameter;
	int rand_a;
	int rand_b;

	public GameAI(byte teamNumberT,byte difficaltyT){
		team=teamNumberT;
		difficalty=difficaltyT;
		pairs = new ArrayList<List<List<Integer>>>();//List of pair-pieces
		diameter = (int) (0.60*(BoardTools.teamGrid().length-1));
		rand_a = new Random().nextInt(diameter)-diameter/2;
		rand_b = new Random().nextInt(diameter)-diameter/2;
	}

	public void getPlayerTurn(byte[][] gameBoard) { // for net play
		 this.gameBoard=gameBoard;
		 makeMove();
	}

	@Override
	public void getPlayerTurn() { // with out net play
		this.gameBoard=BoardTools.teamGrid();
		makeMove();
	}
	
	private boolean right(){
		return m[0]+2 <= gameBoard.length-1 && m[1]+1 <= gameBoard.length-1 && m[1]-1 >= 0;
	}
	private boolean left(){
		return n[0]-2 >= 0 && n[1]-1 >= 0 && n[1]+1 <= gameBoard.length-1;
	}

	private void makeMove(){
		/**
		 * Will's AI
		 * */
		
		int x = 0;
		int y = 0;
		if(team==2){
			y++;
		}
		else if(team==1){
			x++;
		}
		
		//Sleep to stop instantaneous playing
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Play in the middle if possible
		int mid = 1;
		mid*=(gameBoard.length-1)/2;
		if(gameBoard[mid][mid]==0){
			n[0] = mid;//horizontal
			n[1] = mid;//vertical
			m[0] = mid;
			m[1] = mid;
			BoardTools.makeMove(mid, mid, team);
			
			return;
		}
		else if(gameBoard[mid][mid]!=team && gameBoard[mid+rand_a][mid+rand_b]==0){
			n[x] = mid+rand_a;//horizontal
			n[y] = mid+rand_b;//vertical
			m[x] = mid+rand_a;
			m[y] = mid+rand_b;
			BoardTools.makeMove(mid+rand_a, mid+rand_b, team);

			return;
		}
		
		//Add the edges as pairs after we've reached both sides of the map
		if(n[0]-1 == 0 && n[1]-1 >= 0 && n[1]+1 <= gameBoard.length-1){
			List<List<Integer>> pair = new ArrayList<List<Integer>>();
			List<Integer> cord1 = new ArrayList<Integer>();
			List<Integer> cord2 = new ArrayList<Integer>();
			cord1.add(n[0]-1);
			cord1.add(n[1]);
			pair.add(cord1);
			cord2.add(n[0]-1);
			cord2.add(n[1]+1);
			pair.add(cord2);
			pairs.add(pair);
			
			n[0] = n[0]-1;
		}
		if(m[0]+1 == gameBoard.length-1 && m[1]+1 <= gameBoard.length-1 && m[1]-1 >= 0){
			List<List<Integer>> pair = new ArrayList<List<Integer>>();
			List<Integer> cord1 = new ArrayList<Integer>();
			List<Integer> cord2 = new ArrayList<Integer>();
			cord1.add(m[0]+1);
			cord1.add(m[1]);
			pair.add(cord1);
			cord2.add(m[0]+1);
			cord2.add(m[1]-1);
			pair.add(cord2);
			pairs.add(pair);
			
			m[0] = m[0]+1;
		}
		
		//Check if one of our pairs is being attacked, and fill in the alternate if so
		for(int i=0; i<pairs.size(); i++){
			System.out.println("Pair listing: "+pairs);
			if(gameBoard[pairs.get(i).get(0).get(x)][pairs.get(i).get(0).get(y)]==0 || gameBoard[pairs.get(i).get(1).get(x)][pairs.get(i).get(1).get(y)]==0){
				if(gameBoard[pairs.get(i).get(0).get(x)][pairs.get(i).get(0).get(y)]!=0){
					System.out.println("Oh no, they played here: "+pairs.get(i).get(0).get(x)+","+pairs.get(i).get(0).get(y));
					System.out.println("I'll be playing here: "+pairs.get(i).get(1).get(x)+","+pairs.get(i).get(1).get(y));
					BoardTools.makeMove(pairs.get(i).get(1).get(x), pairs.get(i).get(1).get(y), team);
					pairs.remove(i);
					
					return;
				}
				else if(gameBoard[pairs.get(i).get(1).get(x)][pairs.get(i).get(1).get(y)]!=0){
					System.out.println("Oh no, they played here: "+pairs.get(i).get(1).get(x)+","+pairs.get(i).get(1).get(y));
					System.out.println("I'll be playing here: "+pairs.get(i).get(0).get(x)+","+pairs.get(i).get(0).get(y));
					BoardTools.makeMove(pairs.get(i).get(0).get(x), pairs.get(i).get(0).get(y), team);
					pairs.remove(i);
					
					return;
				}
			}
			else{
				pairs.remove(i);
			}
		}
		
		//Check if they were sneaky and played in front of us
		if(right() && gameBoard[m[x]+0*x+1*y][m[y]+1*x+0*y]!=0){
			if(gameBoard[m[x]-1*x+1*y][m[y]-1*y+1*x]==0){
				m[0] = m[0]+1;
				m[1] = m[1]-1;

				BoardTools.makeMove(m[x], m[y], team);
				return;
			}
			else if(gameBoard[m[x]+1*x+0*y][m[y]+1*y+0*x]==0){
				m[0] = m[0];
				m[1] = m[1]+1;

				BoardTools.makeMove(m[x], m[y], team);
				return;
			}
		}
		if(right() && (gameBoard[m[x]-1*x+1*y][m[y]-1*y+1*x]!=0 || gameBoard[m[x]+1*x+0*y][m[y]+1*y+0*x]!=0) && gameBoard[m[x]+0*x+1*y][m[y]+0*y+1*x]==0){
			m[0] = m[0]+1;
			m[1] = m[1];

			BoardTools.makeMove(m[x], m[y], team);
			return;
		}
		//Check if they were sneakier and played behind us
		if(left() && gameBoard[n[x]+0*x-1*y][n[y]+0*y-1*x]!=0){
			if(gameBoard[n[x]+1*x-1*y][n[y]+1*y-1*x]==0){
				n[0] = n[0]-1;
				n[1] = n[1]+1;

				BoardTools.makeMove(n[x], n[y], team);
				return;
			}
			else if(gameBoard[n[x]-1*x+0*y][n[y]-1*y+0*x]==0){
				n[0] = n[0];
				n[1] = n[1]-1;

				BoardTools.makeMove(n[x], n[y], team);
				return;
			}
		}
		if(left() && (gameBoard[n[x]+1*x-1*y][n[y]+1*y-1*x]!=0 || gameBoard[n[x]-1*x+0*y][n[y]-1*y+0*x]!=0) && gameBoard[n[x]+0*x-1*y][n[y]+0*y-1*x]==0){
			n[0] = n[0]-1;
			n[1] = n[1];

			BoardTools.makeMove(n[x], n[y], team);
			return;
		}
		
		
		//Check if we should extend to the left
		if(left()){
			if(gameBoard[n[x]-1*x-1*y][n[y]-1*y-1*x]!=0 && gameBoard[n[x]+1*x-2*y][n[y]+1*y-2*x]==0){
				List<List<Integer>> pair = new ArrayList<List<Integer>>();
				List<Integer> cord1 = new ArrayList<Integer>();
				List<Integer> cord2 = new ArrayList<Integer>();
				cord1.add(n[0]-1);
				cord1.add(n[1]);
				pair.add(cord1);
				cord2.add(n[0]-1);
				cord2.add(n[1]+1);
				pair.add(cord2);
				pairs.add(pair);
				
				n[0] = n[0]-2;
				n[1] = n[1]+1;

				BoardTools.makeMove(n[x], n[y], team);
				return;
			}
			else if(gameBoard[n[x]+1*x-2*y][n[y]+1*y-2*x]!=0 && gameBoard[n[x]-1*x-1*y][n[y]-1*y-1*x]==0){
				List<List<Integer>> pair = new ArrayList<List<Integer>>();
				List<Integer> cord1 = new ArrayList<Integer>();
				List<Integer> cord2 = new ArrayList<Integer>();
				cord1.add(n[0]);
				cord1.add(n[1]-1);
				pair.add(cord1);
				cord2.add(n[0]-1);
				cord2.add(n[1]);
				pair.add(cord2);
				pairs.add(pair);

				n[0] = n[0]-1;
				n[1] = n[1]-1;

				BoardTools.makeMove(n[x], n[y], team);
				return;
			}
		}
		
		//Check if we should extend to the right
		if(right()){
			if(gameBoard[m[x]-1*x+2*y][m[y]-1*y+2*x]!=0 && gameBoard[m[x]+1*x+1*y][m[y]+1*y+1*x]==0){
				List<List<Integer>> pair = new ArrayList<List<Integer>>();
				List<Integer> cord1 = new ArrayList<Integer>();
				List<Integer> cord2 = new ArrayList<Integer>();
				cord1.add(m[0]+1);
				cord1.add(m[1]);
				pair.add(cord1);
				cord2.add(m[0]);
				cord2.add(m[1]+1);
				pair.add(cord2);
				pairs.add(pair);

				m[0] = m[0]+1;
				m[1] = m[1]+1;

				BoardTools.makeMove(m[x], m[y], team);
				return;
			}
			else if(gameBoard[m[x]+1*x+1*y][m[y]+1*y+1*x]!=0 && gameBoard[m[x]-1*x+2*y][m[y]-1*y+2*x]==0){
				List<List<Integer>> pair = new ArrayList<List<Integer>>();
				List<Integer> cord1 = new ArrayList<Integer>();
				List<Integer> cord2 = new ArrayList<Integer>();
				cord1.add(m[0]+1);
				cord1.add(m[1]);
				pair.add(cord1);
				cord2.add(m[0]+1);
				cord2.add(m[1]-1);
				pair.add(cord2);
				pairs.add(pair);

				m[0] = m[0]+2;
				m[1] = m[1]-1;

				BoardTools.makeMove(m[x], m[y], team);
				return;
			}
		}
		int rand = 2;
		rand*=Math.random();
		
		//Extend left if we haven't gone right
		if(left() && rand==0 && gameBoard[n[x]+1*x-2*y][n[y]+1*y-2*x]==0){
			List<List<Integer>> pair = new ArrayList<List<Integer>>();
			List<Integer> cord1 = new ArrayList<Integer>();
			List<Integer> cord2 = new ArrayList<Integer>();
			cord1.add(n[0]-1);
			cord1.add(n[1]);
			pair.add(cord1);
			cord2.add(n[0]-1);
			cord2.add(n[1]+1);
			pair.add(cord2);
			pairs.add(pair);

			n[0] = n[0]-2;
			n[1] = n[1]+1;

			BoardTools.makeMove(n[x], n[y], team);
			return;
		}
		else if(left() && rand==1 && gameBoard[n[x]-1*x-1*y][n[y]-1*y-1*x]==0){
			List<List<Integer>> pair = new ArrayList<List<Integer>>();
			List<Integer> cord1 = new ArrayList<Integer>();
			List<Integer> cord2 = new ArrayList<Integer>();
			cord1.add(n[0]);
			cord1.add(n[1]-1);
			pair.add(cord1);
			cord2.add(n[0]-1);
			cord2.add(n[1]);
			pair.add(cord2);
			pairs.add(pair);

			n[0] = n[0]-1;
			n[1] = n[1]-1;

			BoardTools.makeMove(n[x], n[y], team);
			return;
		}
		//Extend right if we haven't gone left
		if(right() && rand==0 && gameBoard[m[x]-1*x+2*y][m[y]-1*y+2*x]==0){
			List<List<Integer>> pair = new ArrayList<List<Integer>>();
			List<Integer> cord1 = new ArrayList<Integer>();
			List<Integer> cord2 = new ArrayList<Integer>();
			cord1.add(m[0]+1);
			cord1.add(m[1]);
			pair.add(cord1);
			cord2.add(m[0]+1);
			cord2.add(m[1]-1);
			pair.add(cord2);
			pairs.add(pair);

			m[0] = m[0]+2;
			m[1] = m[1]-1;

			BoardTools.makeMove(m[x], m[y], team);
			return;
		}
		else if(right() && rand==1 && gameBoard[m[x]+1*x+1*y][m[y]+1*y+1*x]==0){
			List<List<Integer>> pair = new ArrayList<List<Integer>>();
			List<Integer> cord1 = new ArrayList<Integer>();
			List<Integer> cord2 = new ArrayList<Integer>();
			cord1.add(m[0]+1);
			cord1.add(m[1]);
			pair.add(cord1);
			cord2.add(m[0]);
			cord2.add(m[1]+1);
			pair.add(cord2);
			pairs.add(pair);

			m[0] = m[0]+1;
			m[1] = m[1]+1;

			BoardTools.makeMove(m[x], m[y], team);
			return;
		}
		
		//Fill in the pairs after we've reached both sides of the map
		if( !left() && !right() && pairs.size() > 0){
			//Play a random pair

			BoardTools.makeMove(pairs.get(0).get(1).get(x), pairs.get(0).get(1).get(y), team);
			pairs.remove(0);
			
			return;
		}
		
		//Sam's AI
		int moves=1;
		for(int a=0; a<gameBoard.length; a++){
			for(int b=0; b<gameBoard[a].length; b++){
				if(gameBoard[a][b]==0) moves++;
			}
		}
		moves*=Math.random();
		for(int a=0; a<gameBoard.length; a++){
			for(int b=0; b<gameBoard[a].length; b++){
				if(gameBoard[a][b]==0) {
					moves--;
				}
				if(moves==0) {
					BoardTools.makeMove(a, b, team);
					moves=-10;
				}	
			}
		}
		
		return;
	}
	@SuppressWarnings("unused")
	private void badMove(){
		int moves=1;
		for(int x=0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length; y++){
				if(gameBoard[x][y]==0) moves++;
			}
		}
		moves*=Math.random();
		for(int x=0; x<gameBoard.length; x++)
			for(int y=0; y<gameBoard[x].length; y++){
				if(gameBoard[x][y]==0) {
					moves--;
				}
				if(moves==0) {
					BoardTools.makeMove(x, y, team);
					moves=-10;
				}	
			}

	}	
	/*  Bah, ignore this for now.
	private void nickMove(){
		int[][] moves=new int[gameBoard.length][gameBoard.length];
		 
		 for(int x=0; x<gameBoard.length; x++){
			for(int y=0; y<gameBoard[x].length; y++){
				if(gameBoard[x][y]!=0){
					moves[x][y]=-10000;
				}
				else{
					//Actually calculate value
				}
				if(gameBoard[x][y]!=0 && gameBoard[x][y]!=team){
					
				}
				if(gameBoard[x][y]==team){
					//Look through all pieces and find distance from my piece
					for(int i=0; i<gameBoard.length; i++)
						for(int j=0; j<gameBoard[i].length; j++){
							if(gameBoard[i][j]==0){
								//TODO: calculate distance
								if(team==1){
									
								}
								if(team==2){
									
								}
							}
							
						}
				}
			}
		}
		 
		 int max=moves[0][0];
		 ArrayList<RegularPolygonGameObject> possible=new ArrayList<RegularPolygonGameObject>();
		 possible.add(Global.gamePiece[0][0]);
		 for(int x=0; x<gameBoard.length; x++){
				for(int y=0; y<gameBoard[x].length; y++){
					if (moves[x][y]==max){
						possible.add(Global.gamePiece[x][y]);
					}
					else if(moves[x][y]>max){
						 max=moves[x][y];
						 possible=new ArrayList<RegularPolygonGameObject>();
						 possible.add(Global.gamePiece[x][y]);
					}
				}
			}
		 int move=(int)(possible.size()*Math.random());
		 possible.get(move).setTeam(team);
	} */ 
}