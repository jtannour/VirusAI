package PoisonGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;




/**
 * @author Joyce
 *This is the main application, which is the controller
 */
public class VirusApp extends JFrame{


	// This is the VIEW which we are controlling
	private VirusPanelView  			view;
	Game theGame;
	VirusApp app = this;

	
	private static int shift = 40;

	// This constructor builds the window
	public VirusApp(String title) {
		super(title);
		//Set the client
		theGame = new Game(this);
		buildWindow();
		addMenus();
		
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension dim = toolkit.getScreenSize();
		  
        setDefaultCloseOperation(EXIT_ON_CLOSE);       
        setSize(600, 500);
        setLocation((dim.width/5 + (shift+=40)), dim.height/5);
        setVisible(true);
        
        
		//starting the game
        theGame.startGame();
/*
		Player player1 = new Player(0, Player.playerType.AI, true, theGame, 0);
		Player player2 = new Player(1, Player.playerType.HUMAN, true, theGame, 1);
		theGame.playerList.add(player1);
		theGame.playerList.add(player2);
		theGame.initiateBoardTest();
		
		theGame.setUpPiece(player1, 2, 2);
		theGame.setUpPiece(player1, 0, 0);
		
		theGame.setUpPiece(player2, 2, 1);
		theGame.setUpPiece(player2, 4, 4);
		
		
		theGame.initiateBoardTest();
		theGame.startGameTest();
		*/
    }



    private void buildWindow(){
    	 // Here we add all the components to the window accordingly
		view = new VirusPanelView(5,5, theGame);
    	// Layout the components using a gridbag
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints layoutConstraints = new GridBagConstraints();
	//	GridLayout layout = new GridLayout(1,1,1,1);
     //   getContentPane().setLayout(layout);
        
   
        
        layoutConstraints.gridx = 0; layoutConstraints.gridy = 0;
        layoutConstraints.gridwidth = 0; layoutConstraints.gridheight = 0;
        layoutConstraints.fill = GridBagConstraints.NONE;
        layoutConstraints.insets = new Insets(0, 0, 0, 0);
        layoutConstraints.anchor = GridBagConstraints.NORTH;
        layoutConstraints.weightx = 1.0; layoutConstraints.weighty = 1.0;
        layout.setConstraints(view, layoutConstraints);
        getContentPane().add(view);
        
        add(view);


  
    }
    
    
    public VirusPanelView getView(){
    	return view;
    }
    private void addMenus() {
    	// Menu item
    	
     	JMenuBar menubar = new JMenuBar();
     	setJMenuBar(menubar);

     	// Make the file menu
     	JMenu file = new JMenu("File");
     	file.setMnemonic('F');
     	menubar.add(file);

     	JMenuItem	playAgain = new JMenuItem("New Game");
     	playAgain.setMnemonic('N');
     	
     	file.add(playAgain);
     	
     	//setup the listner
     	playAgain.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
				System.out.println("oyu clicked on start a new game");
				theGame = new Game(app);
				view.resetButtons();
				theGame.startGame();
				
			}
     	});
     	
     	
     }
    
    
   //Main
     public static void main(String args[]) {  	new VirusApp("Poison Game");}
  
     
}