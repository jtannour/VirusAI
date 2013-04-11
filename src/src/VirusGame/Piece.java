package PoisonGame;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * @author Joyce
 *The piece class can also be seen as a NODE class
 */
public class Piece {

	int locX, locY;
	long ID;
	Player player = null;
	List<Piece> children;
	
	Game game;

	public Piece(long ID, int locX, int locY, Player p, Game game){
		this.ID = ID;
		this.locX = locX;
		this.locY = locY;
		player = p;
		
		children = new LinkedList<Piece>();	
		this.game = game;
		

	}
	

	//temporary piece
	public Piece(int locX, int locY){
		this.locX = locX;
		this.locY = locY;
	//	player = p;
		
		children = new LinkedList<Piece>();	
	}
	
	//temporary piece
	public Piece(int locX, int locY, Player p){
		this.locX = locX;
		this.locY = locY;
		player = p;
		
		children = new LinkedList<Piece>();	
	}
	
	
	public String toString(){
		return "Piece at " + locX + " , " + locY + "is owned by " + player;
	}
	
	public void setOwner(Player p){
		player = p;
	}
	
	public void changePiece(int locX, int locY, Player p){
		this.locX = locX;
		this.locY = locY;
		player = p;
	}
	

	public boolean isOwnedBy(int x, int y, Player p){
		if(this.locX ==x && this.locY == y && this.hasOwner(p)){
			return true;
		}
		else
			return false;
	}
	
	public boolean hasOwner(Player p){ return p == player;}
	public Player getPieceOwner(){ return player; }
	public int getX(){return locX;}
	public int getY(){return locY;}
	
	
	//is a piece adjacent to the selected piece?
	public boolean isAdjacent(Player player){

		for(Piece p: player.pieces)
		{

//			System.out.println(p);
			if(p.getX() == this.getX() && (p.getY() + 1 == this.getY() || p.getY() - 1 == this.getY()))
			{	
//				System.out.println("Adj to top and/or bottom");
				return true;
			}
			else if(p.getY() == this.getY() && (p.getX() + 1 == this.getX() || p.getX() - 1 == this.getX())){
				return true;
			}
		
			else if((p.getX() + 1 == this.getX() || p.getX() - 1 == this.getX()) && (p.getY() + 1 == this.getY() || p.getY() - 1 == this.getY()))
			{	
//				System.out.println("Adj to corners");
				return true;
			}
		}
			
		return false;
		
	}
	
	
	public List<Piece> getInfectionPath(){
		List<Piece> infectionPath = new ArrayList<Piece>();
		
		if(this.getY() != 4) 
			infectionPath.add(game.allPieces[this.getX()][this.getY()+1]) ;
		if(this.getY() != 0) 
			infectionPath.add(game.allPieces[this.getX()][this.getY()-1]);
		if(this.getX() != 0) 
			infectionPath.add(game.allPieces[this.getX()-1][this.getY()]);
		if(this.getX() != 4) 
			infectionPath.add(game.allPieces[this.getX()+1][this.getY()]);
		
		if(infectionPath.isEmpty()){
			System.out.println("Im empty");
		}
		return infectionPath;
		
	}
	
	//this will generate tempory piece states 
	public void generateChildren(){

			try{
				children.add(game.allPieces[this.getX()][this.getY()+1]);
				children.add(game.allPieces[this.getX()][this.getY()-1]);
			}catch(Exception ArrayIndexOutOfBoundsException){

			}
			
			try{
				children.add(game.allPieces[this.getX() + 1][this.getY()]);
				children.add(game.allPieces[this.getX() - 1][this.getY()]);
			}catch(Exception ArrayIndexOutOfBoundsException){

			}
			try{
				children.add(game.allPieces[this.getX() + 1][this.getY()+1]);
				children.add(game.allPieces[this.getX() + 1][this.getY()-1]);
			}catch(Exception ArrayIndexOutOfBoundsException){

			}
			try{		
				children.add(game.allPieces[this.getX() - 1][this.getY()+1]);
				children.add(game.allPieces[this.getX() - 1][this.getY()-1]);
			}
			catch(Exception ArrayIndexOutOfBoundsException){

			}

	}
	
	
	public void printChildrenList(){
		
		if(children.size() <= 0){
			System.out.println ("List is empty!");
		}
		else{
			for(Piece p: children){
				System.out.println(p);
			}
		}
	}
	


}
