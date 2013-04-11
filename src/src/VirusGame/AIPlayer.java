package PoisonGame;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;


/**
 * @author Joyce
 *Our AI player that uses alpha beta pruning to take their turn
 *
 *
 */
public class AIPlayer { 
	private Piece currentBestMove;

	private Game currentBoard;
	int maxDepth;
	int maxTime;
	
	//keeps track of the state of the board
	Set<Piece> maxPlayer;
	Set<Piece> minPlayer;


	int type; //0 for heuristic type AI and 1 for random AI
	
	public Player currentPlayer;
	
	public int heuristicType;
	

	public AIPlayer(int type){
		this.type = type;
		this.maxDepth = 3; 
	//	this.maxTime = 3000;		
		heuristicType = type;
		this.currentBestMove = null;
//		currentBestMoveValue = -99999;	
	}

	private int heuristic1(Set<Piece> maxSize, Set<Piece> minSize){	
		return maxSize.size() - minSize.size();

	}
	
	//returns a random move
	private Piece heuristic2(){
		boolean isValid = false;
		Random rand = new Random();
		
		int rand1 = 0 ;
		int rand2 = 0;
		
		while(!isValid){
			rand1 = rand.nextInt(currentBoard.MAXGAMECOL);
			rand2 = rand.nextInt(currentBoard.MAXGAMECOL);
			
			if(currentBoard.isValidMove(currentBoard.getCurrentPlayer(), currentBoard.allPieces[rand1][rand2]))
				isValid = true;
			
		}
		
		return currentBoard.allPieces[rand1][rand2];
	}
	
	
	public Piece alphaBeta(Player player){
		
		currentPlayer = player;
		Piece bestMove = null;
		int bestAlpha = -99999;
	
		
		maxPlayer = new HashSet<Piece>();	
		maxPlayer.addAll(currentPlayer.pieces);

		minPlayer = new HashSet<Piece>();
		minPlayer.addAll(currentPlayer.game.getOpponent().pieces);

		
		int currentVal;
		
		
		//get your current possible pieces that belong to you
		System.out.println("I'm thinking...");
		for(Piece p: currentPlayer.pieces){
			
			for(Piece child: p.children){
				
				maxPlayer.add(child);						
				minPlayer.remove(child);

				maxPlayer.addAll(child.getInfectionPath());				
				minPlayer.removeAll(child.getInfectionPath());

										
				currentVal = min(maxPlayer, minPlayer, maxDepth, -99999, 99999);

				if(currentVal > bestAlpha){
					bestAlpha = currentVal;					
					bestMove = child;
				}

			}
		}
		
		return bestMove;

	}
	
	
	
	public int max(Set<Piece> maxPieces, Set<Piece> minPieces, int depth, int alpha, int beta){
		if(depth == 0){
			return heuristic1(maxPieces, minPieces);
		}
	
		Set<Piece> maxPlayer = new HashSet<Piece>();	
		maxPlayer.addAll(maxPieces);

		Set<Piece> minPlayer = new HashSet<Piece>();
		minPlayer.addAll(minPieces);
		
		for(Piece p: maxPieces){
				
			for(Piece child:  p.children){

				maxPlayer.add(child);	
							
				minPlayer.remove(child);

				maxPlayer.addAll(child.getInfectionPath());				
				minPlayer.removeAll(child.getInfectionPath());

													
				alpha = Math.max(alpha, min(maxPlayer, minPlayer, depth-1, alpha, beta));
				
				
				if(alpha >= beta){
					return alpha;
				}

			}
		}

		return alpha;
	}
	
	
	public int min(Set<Piece> maxPieces, Set<Piece> minPieces, int depth, int alpha, int beta){
		if(depth == 0){
			return heuristic1(maxPieces, minPieces);
		}
		
		Set<Piece> maxPlayer = new HashSet<Piece>();	
		maxPlayer.addAll(maxPieces);

		Set<Piece> minPlayer = new HashSet<Piece>();
		minPlayer.addAll(minPieces);

		for(Piece p: minPieces){
			
			for(Piece child: p.children){
	
				minPlayer.add(child);	
							
				maxPlayer.remove(child);
	
				minPlayer.addAll(child.getInfectionPath());				
				maxPlayer.removeAll(child.getInfectionPath());
											
				beta = Math.min(beta, max(maxPlayer, minPlayer, depth-1, alpha, beta));
			
				
				if(beta <= alpha){
					return beta;
				}
			}
		
		}
		return beta;
		
	}	
		
		public Piece takeTurn(Player p){
			currentPlayer = p.game.getCurrentPlayer();
			currentBoard = currentPlayer.game;
			
			if(type == 1){
				return heuristic2();
			}
			else
				return alphaBeta(currentPlayer);
			
		}
	}