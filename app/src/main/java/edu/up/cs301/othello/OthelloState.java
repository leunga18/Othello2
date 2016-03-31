package edu.up.cs301.othello;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * @author Aaron Leung, Kieran Losh, Aaron Moehnke, Ryan Kane on 3/3/2016.
 * @version 3/14/2016
 */
public class OthelloState extends GameState{


    private int whiteCount;
    private int blackCount;
    private int[][] board;

    private int turn;
    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    /**
     * Constructor
     */
    public OthelloState() {
        turn = 0;
        whiteCount = 2;
        blackCount = 2;
        board = new int[8][8];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = EMPTY;
            }
        }
        //starting layout
        board[3][3] = WHITE;
        board[4][3] = BLACK;
        board[3][4] = BLACK;
        board[4][4] = WHITE;
    }

    /**
     * Copy constructor that does a deep copy
     * @param o OthelloState to copy from
     */
    public OthelloState(OthelloState o){
        this.turn = o.whoseTurn();
        this.whiteCount = o.getWhiteCount();
        this.blackCount = o.getBlackCount();
        board = new int[8][8];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = o.getBoard()[i][j];
            }
        }
    }

    /**
     * Getters and setters. Self-explanatory
     */
    public int whoseTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getWhiteCount() {
        return whiteCount;
    }

    public void setWhiteCount(int whiteCount) {
        this.whiteCount = whiteCount;
    }

    public int getBlackCount() {
        return blackCount;
    }

    public void setBlackCount(int blackCount) {
        this.blackCount = blackCount;
    }

    /**
     * Places a piece at the coordinates if it is within the board bounds and the board exists
     * @param x x coord of the piece
     * @param y y coord of the piece
     * @param pieceColor color of the piece
     * @return true if the piece was placed, false if it was unable to be placed for some reason
     */
    public boolean placePiece(int x, int y, int pieceColor){
        //out of bounds
        if (x > 7 || y > 7 || x < 0 || y < 0){
            return false;
        }
        //color must be valid
        if (pieceColor != BLACK && pieceColor != WHITE && pieceColor != EMPTY) {
            return false;
        }
        //make sure that the board exists
        if (board == null)
            return false;
        //if this statement is reached, the piece can be safely placed.
        board[x][y] = pieceColor;

        return true;
    }

    /**
     *  Gets the color of the piece at the specified location, or returns an error code
     * @param x x coordinate
     * @param y y coordinate
     * @return -1 if invalid location or board, and the color of the piece otherwise
     */
    public int getPiece(int x, int y){
        //out of bounds
        if (x > 7 || y > 7 || x < 0 || y < 0){
            return -1;
        }
        //make sure the board exists
        if (board == null) {
            return -1;
        }
        return board[x][y];
    }

    /**
     * Checks to see if the piece placement is a valid, legal move
     * @param x x coordinate
     * @param y y coordinate
     * @return True if placement is valid, False if placement is not valid.
     */
    public boolean validPiece(int x, int y) {
        return true;
    }

    /**
     * Flips the pieces that need to be flipped accordingly
     * @param x x coordinate
     * @param y y coordinate
     */
    public void flipPiece(int x, int y){

    }

    /**
     * Checks the pieces that need to be flipped upon piece placement
     * @param x x coordinate of placed piece
     * @param y y coordinate of placed piece
     */
    public void checkFlipPieces(int x, int y){
        //checks pieces that need to be flipped


        //calls flipPiece method for pieces that needed to be flipped
    }

    /**
     * Returns the number of pieces of the color given in the parameter
     * @param color Color of piece that needs to be counted
     * @return Number of pieces for the color
     */
    public int countPieces(int color){
        return 1;
    }


    /**
     * Checks if the game is over
     * @return index of the player who won,
     *         -1 if game is not over
     */
    public int checkIfGameOver(){
        return -1;
    }

}
