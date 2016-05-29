/*************************************
 * Hamza Masud
 * BS(CS)-3A
 * Reg#4582
 * Snakes and Ladders
 * Advanced Programming - Assignment 2
 *************************************/

import java.util.Random;

public class SnakesAndLadders {

	Position[] board;
	byte currentTurn;
	public byte totalPlayers;
	public int totalTurns;
	boolean[] isLocked;
	public int rounds;
	
	public short[] playerPositions;
	
	public SnakesAndLadders(byte num_of_players)
	{
		board = new Position[100];
		isLocked = new boolean[num_of_players];
		for(int i=0; i<num_of_players; i++){	isLocked[i] = false;	}	//Every one can move freely
		initializeBoard();
		currentTurn = 0;
		totalTurns = 0;
		rounds = 0;
		totalPlayers = num_of_players;
		playerPositions = new short[totalPlayers];	//zero initializes by default
		play();	
	}
	
	private void initializeBoard()
	{
		//Constructing the actual positions with just numbers for now
		for(short i=0; i<100; i++){
			board[i] = new Position((short)(i+1));
		}
		
		//Assigning ladders
		board[3].setPosition(false, (short)13);
		board[8].setPosition(false, (short)30);
		board[19].setPosition(false, (short)37);
		board[27].setPosition(false, (short)83);
		board[39].setPosition(false, (short)58);
		board[50].setPosition(false, (short)66);
		board[62].setPosition(false, (short)80);
		board[70].setPosition(false, (short)90);
		
		//Assigning snakes
		board[16].setPosition(true, (short)6);
		board[53].setPosition(true, (short)33);
		board[61].setPosition(true, (short)18);
		board[63].setPosition(true, (short)59);
		board[86].setPosition(true, (short)23);
		board[92].setPosition(true, (short)72);
		board[94].setPosition(true, (short)74);
		board[98].setPosition(true, (short)77);
	}
	
	public byte diceRoll()
	{
		Random rand = new Random();
		byte randomNum = (byte)(rand.nextInt(6) + 1);
		return randomNum;
	}
	
	void nextTurn()
	{
		if(currentTurn+1 == totalPlayers){
			currentTurn=0;
			rounds++;
		}
		else
			currentTurn++;
		
		totalTurns++;
	}
	
	public void play()
	{
		boolean gameWon = false;
		byte winner = -1;
		
		//Game loop
		while(gameWon==false){
			//Game winning check
			for(int i=0; i<totalPlayers; i++){
				if(playerPositions[i]==99){
					gameWon = true;
					winner = (byte)(i+1);
					break;
				}
			}
			
			//Break out of the main loop to prevent further turns
			if(gameWon) {	break;	}
			
			//Roll the dice for current player turn
			System.out.println("Player " +(currentTurn+1) +"'s turn...");
			byte playerRoll = diceRoll();
			System.out.println("Player " +(currentTurn+1) +" rolled a " +playerRoll +"!");
			
			//Where the player ends up
			System.out.println("Player " +(currentTurn+1) +"'s current position: " +(playerPositions[currentTurn]+1));
		
			//Move the player. Don't if the move isn't legal or is locked due to a snake
			if((playerPositions[currentTurn]+playerRoll)<100 && !isLocked[currentTurn]){
				playerPositions[currentTurn] += playerRoll;
			}
			else{
				System.out.println("Unable to move :(");
			}
				//For ladder +(&Snake)
				if(board[playerPositions[currentTurn]].special){
					if(board[playerPositions[currentTurn]].isLadder){
						System.out.println("Going up a ladder!");
					}
					else{
						System.out.println("Going down a snake :( Unable to move now");
						isLocked[currentTurn] = true;
						
						
					}
					//Jump to wherever the snake/ladder leads
					playerPositions[currentTurn] = board[playerPositions[currentTurn]].goesTo;
					
				}
						
			//Handle a 6 dice roll
			if(playerRoll==6){
				if(isLocked[currentTurn]){
					isLocked[currentTurn] = false; //Release in case it was locked due to snake
					System.out.println("Player " +(currentTurn+1) +" is now free to move!");
				}
				System.out.println("Getting a bonus turn!");
				playerRoll = diceRoll();
				System.out.println("Rolled a " +playerRoll +" in the bonus turn!");
				if(board[playerPositions[currentTurn]].special){
					playerPositions[currentTurn] = board[playerPositions[currentTurn]].goesTo;
				}
				
				if((playerPositions[currentTurn]+playerRoll)<100){
					playerPositions[currentTurn] += playerRoll;
				}
				else{
					System.out.println("Can't move further. Better luck next time!");
				}
			}
			System.out.println("Player " +(currentTurn+1) +"'s new position: " +(playerPositions[currentTurn]+1) +"\n");
			
			//Next Turn
			nextTurn();
				
		}
		
		System.out.println("Game over! Winner is Player:"+winner);	
		System.out.println("This match took " +totalTurns +" turns to finish");
		
	}
	
	public static void main(String[] args) {
		byte num_of_players = 2; //2 by default
		//For random
		Random rand = new Random();
num_of_players = (byte) (rand.nextInt(3)+2);
		
		//Stats
		int maxRounds = 0;
		int minRounds = 10000;
		int avgRounds = 0;
		
		//Run a 100 times
		int i=0;
		while(i++<100){
		System.out.println("Starting game# " +(i+1) + " between " +num_of_players +" players...");
		SnakesAndLadders game = new SnakesAndLadders(num_of_players);
		
		if(game.totalTurns > maxRounds){	maxRounds = game.rounds; }
		if(game.totalTurns < minRounds){	minRounds = game.rounds; }
		avgRounds = (maxRounds + minRounds)/2;
		}
	
		//Print stats
		System.out.println("MAX ROUNDS = " +maxRounds
				+"\nMIN ROUNDS = " +minRounds
				+"\nAVERAGE ROUNDS = " +avgRounds);
		
	}

}
