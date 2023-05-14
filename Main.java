/**
 *  @Alyana Barrera 
 *  CS1180, Summer 2020
 *  Project 3
 */

import java.util.Random;
import java.util.Scanner;

/**
 * This program prompts a secret code and asks for user to guess the secret code.
 * The user gets 10 attempts to guess the secret code correct. If incorrect, program 
 * prompts guess history counting number correct within the guess and asks user for 
 * re-enter. If correct, program prompts congratulations message.
 */
class Main {
  static final int BOARDSIZE = 4;
    static final int MAXGUESSES = 10;

    /** plays the 'MasterMind' guessing game; computer vs. player
     * main method and all method headers written by V. Starkey
     * @param args the command line arguments
     */
  public static void main(String[] args) {
    Scanner keyboard = new Scanner(System.in);
        
        char[] gameBoard = new char[BOARDSIZE];
        char[][] guessBoard = new char[MAXGUESSES][BOARDSIZE];
        int[] numCorrectArray = new int[MAXGUESSES];
        int numGuesses;
        char playAgain;
        boolean gameOver;
        boolean debugging = true;
        
        do{
        initBoard(gameBoard);

        if (debugging)
        {
            System.out.println("\nThe 'secret code' is:");
            for (int i=0; i<BOARDSIZE; i++)
            {
                System.out.print(gameBoard[i] + " ");
            }
            System.out.println("\n");
        }

        initCorrectBoard(numCorrectArray);
        numGuesses = 0;

        //play game until user is correct or 10 incorrect moves are made
        do
        {
            getPlayerGuess(guessBoard, numGuesses);
            numCorrectArray[numGuesses] = getNumCorrect(gameBoard, guessBoard, numGuesses);
            numGuesses++;

            gameOver = isGameOver(numCorrectArray, numGuesses);

            if (gameOver)
            {
                displayGameOverMessage(numCorrectArray, numGuesses);
            }
            else
            {
                displayGuessHistory(guessBoard, numGuesses, numCorrectArray);
            }            
        }while (numGuesses < MAXGUESSES && numCorrectArray[numGuesses-1] != BOARDSIZE);
        System.out.println();
        System.out.println("Would you like to play another game?");
        playAgain = keyboard.next().charAt(0);
        keyboard.nextLine();
        System.out.println();
        }while (playAgain == 'y' || playAgain == 'Y');

    }//end main method
    

    /**
     * post: each position in the board will be populated with one of the 
     *       characters R, B, G, or Y (generated randomly)
     * @param board is an array of characters with dimension BOARDSIZE
     */
    public static void initBoard (char[] board)
    {
        String characters = "RBGY";
        
        Random rand = new Random();
        
        for (int i=0; i<board.length; i++)
        {
            board[i] = characters.charAt(rand.nextInt(characters.length()));
        }
        
    }

     /**
     * each position in the board will be initialized with a zero
     * @param board is an array of integers with dimension MAXGUESSES
     */
    public static void initCorrectBoard (int[] board)
    {
        for (int i=0; i<board.length; i++)
        {
            board[i] = 0;
        }
        
    }

     /**
     * player is asked for a guess. 
     * uses results of isValidMove method to determine if guess is valid
     * if valid, places the move on the game board
     * if not valid, asks the user to re-enter the move
     * @param guessBoard is a 2d array of characters with
     *        first dimension MAXGUESSES and second dimension BOARDSIZE
     * @param guessNumber is the current row on the guessBoard in which to
     *        place the player's guess
     */
    public static void getPlayerGuess (char[][] guessBoard, int guessNumber)
    {
        Scanner keyboard = new Scanner(System.in);
        
        String guess;
        do {
        System.out.println("Enter 4 letters for your move. Allowable entries are "
        + "R, G, B, and Y (upper and lower case).\n" + "Do not use spaces between letters.");
        guess = keyboard.nextLine().toUpperCase();
        } while (!isValidMove(guess));
        for (int i=0; i<guess.length(); i++)
        {
            guessBoard[guessNumber][i] = guess.charAt(i);
        }
        System.out.println();
    }

    /**
     * checks if guess is valid move for this game
     * @param guess is a string, all uppercase
     * @returns true if guess is correct number of characters and all
     *          characters are R, G, B, or Y.  Else returns false.
     */
    public static boolean isValidMove(String guess)
    {
        boolean flag = false;
        
        if (guess.length() != BOARDSIZE)
        {
            return false;
        }
        for (int i=0; i<guess.length(); i++)
        {
            if (!('R' == guess.charAt(i) || 'G' == guess.charAt(i) || 'B' == guess.charAt(i) || 'Y' == guess.charAt(i)))
            {
                flag = true;
            }
        }
        if (flag)
        {
            return false;
        }
        return true; 
    }

    /**
     * compares 'pegs' on current row of guessBoard to pegs on gameBoard
     * @param gameBoard is an array of characters of size BOARDSIZE
     * @param guessBoard is a 2d array of characters with
     *        first dimension MAXGUESSES and second dimension BOARDSIZE
     * @param guessNumber is the row on the guessBoard containing the player's
     *        current guess
     * @return returns number of pegs that exactly match, in value and position
     */
    public static int getNumCorrect(char[] gameBoard, char[][] guessBoard,
                                   int guessNumber)
    {
        int totalCorrect = 0;
        for (int i=0; i<gameBoard.length; i++)
        {
            if (gameBoard[i] == guessBoard[guessNumber][i])
            {
                totalCorrect =  totalCorrect + 1;
            }
        }
        return totalCorrect; 
    }

     /**
     * displays all guesses made so far by the user.  Each guess is displayed
     * on one line of the output screen, along with the guess number and the
     * the number of correct pegs for that guess.
     * @param guessBoard is a 2d array of characters with
     *        first dimension MAXGUESSES and second dimension BOARDSIZE
     * @param guessNumber is the row on the guessBoard containing the player's
     *        current guess
     * @param numCorrectArray is an array indicating number of pegs the user
      *       had correct for each guess
     */
    public static void displayGuessHistory(char[][] guessBoard, int guessNumber,
                                      int[] numCorrectArray)
    {
       System.out.println("Guess History: ");
       System.out.printf(" %5s %15s %15s\n", "Attempt", "Colors Guessed", "Number Correct");
        for (int i=0; i<guessNumber; i++) 
        {
            System.out.print("  ");
            System.out.print(i+1);
            System.out.print("\t   ");
            for (int j = 0; j<guessBoard[i].length; j++) {
                System.out.print(" "); 
                System.out.print(guessBoard[i][j]);
                
            }
            System.out.println("\t     " + numCorrectArray[i]);
        }
        System.out.println();
    }
    
    
    /**
     * determines if the game is over and returns true if over, false if not
     * game is over if the user's last guess was completely correct (number of
     * correct guesses is equal to the BOARDSIZE) or game is over
     * if numGuesses is equal to MAXGUESSES
     */
    public static boolean isGameOver(int[] numCorrectArray, int numGuesses)
    {
        if (numGuesses == MAXGUESSES)
        {
            return true;
        }
        else if (numCorrectArray[numGuesses-1] == BOARDSIZE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * displays a congratulations message if the last guess was completely correct
     * displays a "sorry, game over" message if the last guess was not completely correct
     * @param numCorrectArray numCorrectArray is an array indicating number of pegs the user
       had correct for each guess
     * @param guessNumber is number of guesses player used
     */
    public static void displayGameOverMessage(int[] numCorrectArray, int guessNumber)
    {
        if (numCorrectArray[guessNumber-1] == BOARDSIZE)
        {
            System.out.println("Congratulations--you have cracked the secret code in " 
                + guessNumber + " move(s)!");
        }
        else
        {
            System.out.println("Sorry, you did not guess the secret code.");
        }
    }



}//end class
