package ua.groupvertex.game;

import ua.groupvertex.playground.Board;
import ua.groupvertex.playground.Seed;
import ua.groupvertex.playground.State;

import java.util.Scanner;

public class GameMain {
    private Board board;            // the game board
    private State currentState; // the current state of the game (of enum GameState)
    private Seed currentPlayer;     // the current player (of enum Seed)

    private static Scanner in = new Scanner(System.in);  // input Scanner

    /**
     * Constructor to setup the game
     */
    public GameMain() {
        board = new Board();  // allocate game-board

        // Initialize the game-board and current status
        initGame();
        // Play the game once. Players CROSS and NOUGHT move alternately.
        do {
            playerMove(currentPlayer); // update the content, currentRow and currentCol
            board.paint();             // ask the board to paint itself
            updateGame(currentPlayer); // update currentState
            // Print message if game-over
            if (currentState == State.CROSS_WON) {
                System.out.println("'X' won! Bye!");
            } else if (currentState == State.NOUGHT_WON) {
                System.out.println("'0' won! Bye!");
            } else if (currentState == State.DRAW) {
                System.out.println("It's Draw! Bye!");
            }
            // Switch player
            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
        } while (currentState == State.PLAY);  // repeat until game-over
    }

    /**
     * Initialize the game-board contents and the current states
     */
    public void initGame() {
        currentPlayer = Seed.CROSS;       // CROSS plays first
        currentState = State.PLAY; // ready to play
    }

    /**
     * The player with "theSeed" makes one move, with input validation.
     * Update Cell's content, Board's currentRow and currentCol.
     */
    public void playerMove(Seed theSeed) {
        boolean validInput = false;  // for validating input
        do {
            if (theSeed == Seed.CROSS) {
                System.out.print("Player 'X', enter your move (row[1-3] column[1-3]): ");
            } else {
                System.out.print("Player 'O', enter your move (row[1-3] column[1-3]): ");
            }
            int row = in.nextInt() - 1;
            int col = in.nextInt() - 1;
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                    && board.cells[row][col].content == Seed.EMPTY) {
                board.cells[row][col].content = theSeed;
                board.currentRow = row;
                board.currentCol = col;
                validInput = true; // input okay, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);   // repeat until input is valid
    }

    /**
     * Update the currentState after the player with "theSeed" has moved
     */
    public void updateGame(Seed theSeed) {
        if (board.hasWon(theSeed)) {  // check for win
            currentState = (theSeed == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (board.isDraw()) {  // check for draw
            currentState = State.DRAW;
        }
        // Otherwise, no change to current state (still GameState.PLAYING).
    }

    /**
     * The entry main() method
     */
    public static void main(String[] args) {
        new GameMain();  // Let the constructor do the job
    }
}