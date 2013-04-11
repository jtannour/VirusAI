

A simple virus game in java demonstrating alpha beta pruning.
You can still do a lot with this like improving the GUI for changes instead of going in the code to edit changes.

============================
How the virus game works   |
============================
This is an experimental two player game, although it can easily be extended to N-players. It
is played on a K x K board, where each player starts with three pieces randomly placed, as

During a specific player’s turn, he may “infect” one square that is adjacent to a piece that
he controls. In this setting, the word “adjacent” means any one of the (at most) 8 neighboring
squares. The player claims the square that he has infected, as well as the squares directly above,
below, and to the right and left of that particular square.
Any pieces on the claimed squares are removed from the board, and replaced with this
player’s own pieces. When one player is completely removed from the board, the other is
declared the winner.

The play continues until one player has lost all of his pieces, at which point he is eliminated
and the other player wins.


============================
	HOW TO RUN	   |	
============================

The main method to run the program is in VirusApp.java. Upon execution, the GUI will pop up and 2 AI players will start playing.

There is a delay in the graphics of 3 seconds to allow to see the moves being made. The red player is the one using
the minimax and the blue player is just picking at random.

You can play with a human player but you have to change it manually in the code.
If you would like to play again an AI then in startGame() in Game.java


change the line :
//AI that does random movements
Player player2 = new Player(1, Player.playerType.AI, false, this, 1);
		playerList.add(player2);
		setUpPieces(player2);

to
Player player2 = new Player(1, Player.playerType.HUMAN, false, this, 1);
		playerList.add(player2);
		setUpPieces(player2);


The minimax algorithm with the alpha beta pruning can be found in AIPlayer
Right now I have the depth set to 3 but if you want to change it
just change the maxDepth field in the AIPlayer constructor.


