package PoisonGame;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;



/**
 * @author Joyce
 *Where the environment gets set up and where players are prompted to take their turn
 */

public class Game {

	final static int STARTPIECES = 3;
	
	final static int MAXGAMEROW = 5;
	final static int MAXGAMECOL = 5;
	
	final static int MAXPOSITIONS = MAXGAMEROW * MAXGAMECOL;
	
	//who's playing the game?
	ArrayList<Player> playerList;
	
	//just to keep track of start up locations and make sure not same
	ArrayList<Piece> startUpPositions = new ArrayList<Piece>();
	
	
	//the main board. What pieces are currently on the board
	Piece[][] allPieces = new Piece[MAXGAMEROW][MAXGAMECOL];
	
	
	int currentPlayerTurn = 0;
	private long boardID;
	
	static VirusApp app;
	
	
	//constructor for testing purposes
	public Game(){
		playerList = new ArrayList<Player>();
	}
	
	
	public Game(VirusApp app){
		playerList = new ArrayList<Player>();
		this.app = app;
		boardID = 0;

	}
	
	public void startGame(){
		
		//set up the board pieces
		for(int i =0; i<MAXGAMEROW; i++){
			for(int j=0; j < MAXGAMECOL; j++){
				allPieces[i][j] = new Piece(boardID, i,j,null, this);
			}
			boardID++;
		}
		
		//get all the neighbours
		for(int i =0; i<MAXGAMEROW; i++){
			for(int j=0; j < MAXGAMECOL; j++){
				allPieces[i][j].generateChildren();
			}
			boardID++;
		}
		
		//AI that uses Alpha-beta
		Player player = new Player(0, Player.playerType.AI, true, this, 0);
		playerList.add(player);
		setUpPieces(player);
		
		//AI that does random movements
		Player player2 = new Player(1, Player.playerType.AI, false, this, 1);
		playerList.add(player2);
		setUpPieces(player2);
		
		
		//just for clarity...
		boolean gameOver = false;
		
		while(!gameOver){
			
			Player currentPlayer = getCurrentPlayer();
			Piece piece;
			
			System.out.println("TURN = " + currentPlayer);
			
			if (currentPlayer.isHuman()){
				piece = currentPlayer.takeTurn();
			}
			else{
				piece = currentPlayer.takeTurn();
			}
			
			if(piece == null)
				continue;
			else{
				currentPlayer.setSelectedPiece(piece);
				infect();
			}
			
			gameOver = gameOver();
							
		}
		
		
		
	}
	
	
	//prints out hte current status of the board. For testing
	public void printBoard(){
		System.out.println("The board so far");
		for(int i = 0; i < allPieces.length; i ++){
			for(int j= 0; j < allPieces.length; j ++){
				System.out.println(allPieces[i][j]);
			}
			
		}
	}
	
	
	//sets up the players initial 3 pieces each randomly on the board
	public void setUpPieces(Player p){
		
		Piece piece;
		Random randX = new Random();
		Random randY = new Random();
		
		
		for(int i=0; i < STARTPIECES; i++){
			boolean genrateRand = true;

			while(genrateRand){
				boolean wasSame = false;
				int x = randX.nextInt(MAXGAMECOL);
				int y = randY.nextInt(MAXGAMECOL);
				
			
				for(Piece pieces: startUpPositions){
					if(pieces.getX() == x && pieces.getY() == y){
						System.out.println("these are the same!");
						wasSame = true;
						break;
					}
				}
				
				if(wasSame){
					continue;
				}
				else{
	
					//piece = new Piece(x,y,p);
					allPieces[x][y].setOwner(p);
					piece = allPieces[x][y];
					startUpPositions.add(piece);
					p.addPlayerPieceList(piece);
					app.getView().updatePlayerButtons(p);
					genrateRand = false;
				}

			
			}

		}
			
	}
	
	public boolean isValidMove(Player player, Piece selectedPiece){
		
		if(selectedPiece.isAdjacent(player)){
		//	player.pieces.add(selectedPiece);
			return true;
		}
			
		return false;
	}
	
	public List<Piece> getValidMoves(Player player){
		
		List<Piece> validMoves = new ArrayList<Piece>();
				
//	System.out.println(player + " has pieces " + player.pieces);
		
		for(Piece p: player.pieces){
			p.generateChildren();
			for(Piece children: p.children){
				validMoves.add(children);
			}
		}
		
		
		if(validMoves.isEmpty()){
			System.out.println("EMPTY");
		}
		
		Collections.shuffle(validMoves);
//		System.out.println(validMoves);
		return validMoves;
		
	}
	
	//this will take care of the actual infecting
	//infect the blocks that are above, below, right and left of infected square
	public void infect(){

		//the selected infected piece's coordinates
		Player currentPlayer = getCurrentPlayer();
		Piece selectedPiece = currentPlayer.getSelectedPiece();
		Player opponentCheck;
		Piece infectedPiece;
		
		//for testing
		boolean removed = false;
		
		Piece selectedPieceCheck = allPieces[selectedPiece.getX()][selectedPiece.getY()];
		
		opponentCheck = selectedPieceCheck.getPieceOwner();
		
		if(currentPlayer != opponentCheck && opponentCheck != null){
				removed = opponentCheck.pieces.remove(selectedPiece);
				System.out.println("REMOVING " + removed);
				
				allPieces[selectedPiece.getX()][selectedPiece.getY()].setOwner(currentPlayer);
				currentPlayer.addPlayerPieceList(selectedPiece);
		}
		else if(opponentCheck == null){
			allPieces[selectedPiece.getX()][selectedPiece.getY()].setOwner(currentPlayer);
			currentPlayer.addPlayerPieceList(selectedPiece);
		}
		
		//highlight the root of infection
		app.getView().highlightInfection(currentPlayer, selectedPiece.getX(), selectedPiece.getY());
		
		if(!currentPlayer.isHuman())
		{
			
			/*Just creates a delay between the AI selection and the actual move taken
			 * so that you can actually see the thought behind the move*/
			try {
				Thread.sleep(300);
				app.getView().removeBorder(selectedPiece.getX(), selectedPiece.getY());
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		int x = selectedPiece.getX();
		int y = selectedPiece.getY();
		
//		System.out.println("Infecting from " + selectedPiece);

		try{	
			
			infectedPiece = allPieces[x][y-1];
			opponentCheck = infectedPiece.getPieceOwner();
			if(currentPlayer != opponentCheck && opponentCheck != null){
					removed = opponentCheck.pieces.remove(infectedPiece);
					System.out.println("REMOVING " + removed);
					//get the above
					allPieces[x][y-1].setOwner(currentPlayer);
					currentPlayer.addPlayerPieceList(infectedPiece);
			}
			else if(opponentCheck == null){
				allPieces[x][y-1].setOwner(currentPlayer);
				currentPlayer.addPlayerPieceList(infectedPiece);
			}
		}catch(Exception ArrayIndexOutOfBoundsException){
			
		}
		
		
		
		try{
			//below
			infectedPiece = allPieces[x][y+1];
			opponentCheck = infectedPiece.getPieceOwner();
			if(currentPlayer != opponentCheck && opponentCheck != null){
					removed = opponentCheck.pieces.remove(infectedPiece);
					System.out.println("REMOVING " + removed);
					
					allPieces[x][y+1].setOwner(currentPlayer);
					currentPlayer.addPlayerPieceList(infectedPiece);
			}
			else if(opponentCheck == null){
				allPieces[x][y+1].setOwner(currentPlayer);
				currentPlayer.addPlayerPieceList(infectedPiece);
			}
		}catch(Exception ArrayIndexOutOfBoundsException){
			
		}
		
		try{
			
			//right
			infectedPiece = allPieces[x+1][y];
			opponentCheck = infectedPiece.getPieceOwner();
			if(currentPlayer != opponentCheck && opponentCheck != null){
					removed = opponentCheck.pieces.remove(infectedPiece);
					System.out.println("REMOVING " + removed);
					
					allPieces[x+1][y].setOwner(currentPlayer);
					currentPlayer.addPlayerPieceList(infectedPiece);
			}
			else if(opponentCheck == null){
				allPieces[x+1][y].setOwner(currentPlayer);
				currentPlayer.addPlayerPieceList(infectedPiece);
			}
		}catch(Exception ArrayIndexOutOfBoundsException){
			
		}
			
		try{	
			//left
			infectedPiece = allPieces[x-1][y];
			opponentCheck = infectedPiece.getPieceOwner();
			if(currentPlayer != opponentCheck && opponentCheck != null){
					removed = opponentCheck.pieces.remove(infectedPiece);
					System.out.println("REMOVING " + removed);
					
					allPieces[x-1][y].setOwner(currentPlayer);
					currentPlayer.addPlayerPieceList(infectedPiece);
			}
			else if(opponentCheck == null){
				allPieces[x-1][y].setOwner(currentPlayer);
				currentPlayer.addPlayerPieceList(infectedPiece);
			}
		}
		catch(Exception ArrayIndexOutOfBoundsException){
			
		}
		
		app.getView().updatePlayerButtons(currentPlayer);
		
		//next player's turn now
		nextPlayer();

	
	}
	
	//Set the next player
	public void nextPlayer(){

		playerList.get(currentPlayerTurn).setIsTurn(false);
		currentPlayerTurn++;
		
		if(currentPlayerTurn > playerList.size()-1)
			currentPlayerTurn = 0; //go back to the first player

		
		playerList.get(currentPlayerTurn).setIsTurn(true);
	}
	
	//get the currentplayer who's turn it is
	public Player getCurrentPlayer(){
		return playerList.get(currentPlayerTurn);
	}
	
	//get hte current player's opponent
	public Player getOpponent(){
		if(currentPlayerTurn == 0)
			return playerList.get(currentPlayerTurn+1);
		else
			return playerList.get(currentPlayerTurn-1); 
	}
	
	
	public boolean gameOver(){
		
		//if a player has no more pieces then he has lost
		for(Player p: playerList)
		{
			if(p.pieces.isEmpty()){
				System.out.println("the game has been won");
				return true;
			}
		}
		
		return false;
	}
	
	
	public int getRemainingPiecesOfCurrentPlayer(){
		return getCurrentPlayer().pieces.size();
	}
	
	public int getRemainingPiecesOfOpponent(){
		return getOpponent().pieces.size();
	}
	

	public long getPieceValue(Piece piece){
		long conquerCount = 0;
		long maxCount = 0;
		
		Player opp = getOpponent();
		
		if(piece == null){
			return -1;
		}

		else{	
			for(Piece children: piece.children)
			//		for(Piece oppPieces: opp.pieces){
						if(children.hasOwner(opp) || children.hasOwner(null)){
							conquerCount++;
						}
					
						if(conquerCount > maxCount){
							maxCount = conquerCount;
							conquerCount = 0;
						}
				
		//	}
			return maxCount;
		}
			
		
	}
	
	public void setUpPiece(Player p, int x, int y){
		
		allPieces[x][y].setOwner(p);
		Piece piece = allPieces[x][y];
		startUpPositions.add(piece);
		p.addPlayerPieceList(piece);
		app.getView().updatePlayerButtons(p);
		
	}

	

}
