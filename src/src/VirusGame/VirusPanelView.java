package PoisonGame;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

/**
 * @author Joyce
 *Our main GUI where everything gets drawn
 */
public class VirusPanelView extends JPanel {
	

	private JButton		button[][] = new JButton[5][5];
	GridLayout layout;	

	Game game;
	
	// This constructor builds the panel
	public VirusPanelView(int n, int m, Game game) {
		this.game = game;
		//this is to show who's online at the moment
    	layout = new GridLayout(n,m,2,2);
		
		setLayout(layout);
    	
    	for(int i = 0; i < n; i++){
    		for(int j=0; j < m; j++){
    			button[i][j] = new JButton();
    			button[i][j].setBackground(Color.white);
    			final int col = i;
    			final int row = j;
    			
    			button[i][j].addActionListener(new ActionListener(){
    				public void actionPerformed (ActionEvent e){
    					
    					
    					System.out.println("You clicked button at " + col + " , " + row);
    					
    					
    					
    					assesMove(col, row);
    					
    				}
    			});
    			
    			add(button[i][j]);
    			
    		}
    	}
    	
    }


	public void assesMove(int col, int row){
		Player player = game.getCurrentPlayer();
		Piece temp = game.allPieces[col][row];
		//dont mess around before the AI makes his move
		if(!game.getCurrentPlayer().isHuman()){
			System.out.println("it's not your turn");
			return;
		}

		System.out.println(temp.hasOwner(player));
		if(temp.hasOwner(player) || game.isValidMove(player, temp)){
			Piece piece = game.allPieces[temp.getX()][temp.getY()];
			player.setSelectedPiece(piece);
			game.infect();
		}
		else
			System.out.println("not a legal move");
	}

    
	public void highlightInfection(Player player, int x, int y){
		if (player.getPlayerNumber() == 0){
			button[x][y].setBorder(BorderFactory.createLineBorder(Color.orange, 5));
		}
		else{
			button[x][y].setBorder(BorderFactory.createLineBorder(Color.green, 5));
		}
		
	}
	
	public void removeBorder(int x, int y){
		button[x][y].setBorder(BorderFactory.createLineBorder(Color.white, 0));
	}
	
     public void updatePlayerButtons(Player player){
    	 
    	 if (player.getPlayerNumber() == 0){
    		
    		 for(Piece p: player.pieces){
    			 
    			 int x = p.getX();
    			 int y = p.getY();
    			try{ 
    				button[x][y].setBackground(Color.red);
    			}
    			catch(Exception ArrayIndexOutOfBoundsException){
    				continue;
    			}
    		 }
    	 }
    	 else{
    		 for(Piece p: player.pieces){
    			 
    			 int x = p.getX();
    			 int y = p.getY();
    			 try{
    				 button[x][y].setBackground(Color.blue);
    			 }
    			 catch(Exception ArrayIndexOutOfBoundsException){
     				continue;
     			}
    		 }
    	 }
    	 
    	 
     }
    
     
     //THE GET METHODS
     public JButton getButon(int i , int j) {return button[i][j];}


     public void resetButtons(){
    	 for(int i = 0; i < game.MAXGAMECOL; i++){
     		for(int j=0; j < game.MAXGAMEROW; j++){
     			button[i][j].setBackground(Color.white);
     			
     		}
     	}
     }
    
     
}