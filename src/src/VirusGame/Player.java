package PoisonGame;
import java.util.ArrayList;
import java.util.Random;


/**
 * @author Joyce
 *Creates a player, keeps track of their pieces and lets them take their turn
 */
public class Player {


	private int playerNumber;
	boolean isTurn;
	
	enum playerType {HUMAN, AI}
	private boolean isHuman = false;
	
	Piece selectedPiece = null;
	
	//gameboard 
	Game game;
	public ArrayList<Piece> pieces;
	
	AIPlayer playerAI;
	
	
	public Player (int playerNumber, playerType type, boolean isTurn, Game game, int aiType){
		this.playerNumber = playerNumber;
		this.isTurn = isTurn;
		pieces = new ArrayList<Piece>();
		
		if(type == playerType.HUMAN)
			isHuman = true;
		else{
			playerAI = new AIPlayer(aiType);
		}
			
		this.game = game;
	}
	
	public String toString(){
		return " Player: " + getPlayerNumber();
	}
	public int getPlayerNumber(){
		return playerNumber;
	}
	
	
	public boolean isHuman(){
		return isHuman;
	}
	
	//The pieces the individual player currently has
	public void addPlayerPieceList(Piece piece){
		pieces.add(piece);
		reOrderPieceList();
	}
	
	public void reOrderPieceList(){
		Random rand = new Random();
		for(int i=0; i<pieces.size(); i++){
			Piece tmp = pieces.get(i);
			int other=rand.nextInt(pieces.size());
			pieces.set(i,pieces.get(other));
			pieces.set(other,tmp);
		}
	}
	

	public boolean hasPiece(int x, int y){
		for(Piece p : pieces){
			if (p.getX() == x && p.getY() == y){
				return true;
			}
			
		}
		
		return false;
	}
	
	public void setSelectedPiece(Piece piece){
		selectedPiece = piece;
	}
	
	public Piece getSelectedPiece(){
		return selectedPiece;
	}
	
	public void setIsTurn(boolean isTurn){
		this.isTurn = isTurn;
		
	}
	
	public boolean isTurn(){
		return isTurn;
	}
	
	
	public void printPlayerPieceList(){
		
		for(Piece p : pieces){
			System.out.print(" " + p.getX() + " , " + p.getY());
			
			
		}
		System.out.println();
	}
	
	public Piece takeTurn(){
	
		if(isHuman()){
			while(isTurn && game.gameOver() == false){
				
			}
			return null;
		}
		else{
			
			return playerAI.takeTurn(this);
			
		}
	}
			

	

}
