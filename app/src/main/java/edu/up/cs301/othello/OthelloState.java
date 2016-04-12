package edu.up.cs301.othello;

import edu.up.cs301.game.infoMsg.GameState;

/**
 * @author Aaron Leung, Kieran Losh, Aaron Moehnke, Ryan Kane on 3/3/2016.
 * @version 3/14/2016
 */
public class OthelloState extends GameState {

    private int whiteCount = 2;
    private int blackCount = 2;
    private int[][] board = null;
    private int[][] preBoard = null;
    private boolean[][] justFlipped = null;
    private boolean madeMove = false;

    //if turn == 1 White player turn
    //if turn == 0 Black player turn
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
        preBoard = new int[8][8];
        justFlipped = new boolean[8][8];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = EMPTY;
            }
        }
        for (int i = 0; i < preBoard.length; i++){
            for (int j = 0; j < preBoard[i].length; j++){
                preBoard[i][j] = EMPTY;
            }
        }
        for (int i = 0; i < justFlipped.length; i++){
            for (int j = 0; j < justFlipped[i].length; j++){
                justFlipped[i][j] = false;
            }
        }
        //starting layout
        board[3][3] = WHITE;
        board[4][3] = BLACK;
        board[3][4] = BLACK;
        board[4][4] = WHITE;
        saveBoard();
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
        preBoard = new int[8][8];
        justFlipped = new boolean[8][8];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = o.getBoard()[i][j];
            }
        }
        for (int i = 0; i < preBoard.length; i++){
            for (int j = 0; j < preBoard[i].length; j++){
                preBoard[i][j] = o.getPreBoard()[i][j];
            }
        }
        for (int i = 0; i < justFlipped.length; i++){
            for (int j = 0; j < justFlipped[i].length; j++){
                justFlipped[i][j] = o.getJustFlipped()[i][j];
            }
        }
    }

    /**
     * Getters and setters. Self-explanatory
     */
    public int whoseTurn() { return turn; }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] getPreBoard(){
        return preBoard;
    }

    public boolean[][] getJustFlipped(){
        return justFlipped;
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
     * Gets the color of the piece at the specified location, or returns an error code
     * @param x x coordinate
     * @param y y coordinate
     * @return -1 if invalid location on board, or the color of the piece otherwise
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
     * Give coordinates and if the piece was flipped return true
     * @param x x coordinate
     * @param y y coordinate
     * @return if the piece was flipped this turn
     */
    public boolean getJustFlipped(int x, int y){
        return justFlipped[x][y];
    }

    /**
     * Ryan Kane
     * saves the board array to preBoard
     */
    private void saveBoard(){
        for (int i = 0; i < preBoard.length; i++){
            for (int j = 0; j < preBoard[i].length; j++){
                preBoard[i][j] = board[i][j];
            }
        }
    }

    /**
     * Ryan Kane
     * loads the preBoard to the board
     */
    private void loadBoard(){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = preBoard[i][j];
            }
        }
    }

    /**
     * finalizes the move;
     */
    public void finalizeBoard(){
        this.saveBoard();
        madeMove = false;
    }

    /**
     * Ryan Kane
     * Places a piece at the coordinates if it is within the board bounds and the board exists
     * @param x x cord of the piece
     * @param y y cord of the piece
     * @param pieceColor color of the piece
     * @param wantflip: if you want this method to only test have flip = false
     * 			        if you want it to also flip the pieces flip = true
     * @return Number of pieces that will be flipped by making this move
     * 0 = failure
     */
    public int placePiece(int x, int y, int pieceColor, boolean wantflip){
        //out of bounds
        if (x > 7 || y > 7 || x < 0 || y < 0){
            return 0;
        }
        //make sure that the board exists
        if (board == null) {
            return 0;
        }
        if(board[x][y] != EMPTY){
            //a piece is already there
            return 0;
        }
        //color must be valid
        if (pieceColor != BLACK && pieceColor != WHITE) {
            return 0;
        }
        //loads the old board
        if(madeMove == true && wantflip == true){
            loadBoard();
        }
        else if (wantflip == true){
            madeMove = true;
        }
        //the placement will flip at least 1 piece
        if (checkFlipPieces(x,y,pieceColor, false) < 1){
            return 0;
        }
        //if this statement is reached, the piece can be safely placed.
        int temp = checkFlipPieces(x, y, pieceColor,wantflip);
        //this will flip the first piece if this is not a test
        if(wantflip == true){
            justFlipped[x][y] = true;
            board[x][y] = pieceColor;
        }
        return temp;
    }

    /**
     * Ryan Kane
     * Checks the num of pieces that need to be flipped upon piece placement
     * @param x x coordinate of placed piece
     * @param y y coordinate of placed piece
     * @param wantflip: if you want this method to only test have flip = false
     * 			        if you want it to also flip the pieces flip = true
     * @return Number of pieces that will be flipped
     */
    private int checkFlipPieces(int x, int y, int origColor, boolean wantflip){
        int flipped = 0;
        //checks pieces that need to be flipped
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++) {
                //do not try to check for a row without any direction
                if(i==0 && j == 0){
                }
                //check this whole direction to see if any pieces get flipped
                else{
                    int rowFlip = flipRow(x,y,i,j,origColor,wantflip);
                    if(rowFlip != -1){
                        flipped = flipped + rowFlip;
                    }
                }
            }
        }
        return flipped;
    }


    /**
     * Ryan Kane
     * checks and flips all pieces in a given row
     * @param x x coordinate of placed piece
     * @param y y coordinate of placed piece
     * @param xDir x direction row is checking
     * @param yDir x direction row is checking
     * @param origColor color of the original placed piece
     * @param wantflip: if you want this method to only test have wantflip = false
     * 			        if you want it to also flip the pieces wantflip = true
     * @return Number of pieces that will be flipped in this row
     */
    private int flipRow(int x, int y, int xDir, int yDir, int origColor, boolean wantflip){
        int piecesFlipped = -1;
        //updates cur location
        x = x + xDir;
        y = y + yDir;
        //out of bounds
        if (x > 7 || y > 7 || x < 0 || y < 0){
            return -1;
        }
        //this piece is same color as first
        if(board[x][y] == origColor){
            return 0;
        }
        //this piece is empty
        else if (board[x][y] == EMPTY) {
            return -1;
        }
        //this piece is of the opposite color and piece may be flipped
        else if (board[x][y] == oppositeColor(origColor)) {
            piecesFlipped = flipRow(x, y, xDir, yDir, origColor,wantflip);
            //no pieces flipped
            if (piecesFlipped == -1){
                return -1;
            }
            if (piecesFlipped == 0){
                if(wantflip == true){
                    justFlipped[x][y]= true;
                    board[x][y] = origColor;
                }
                return 1;
            }
            //a piece will be flipped
            else{
                if(wantflip == true){
                    justFlipped[x][y]= true;
                    board[x][y] = origColor;
                }
                return ++piecesFlipped;
            }
        }
        //some unexpected situation occurred
        return -1;
    }

    /**
     * Ryan Kane
     * Will tell the requester if the game needs to be ended or turn skipped
     * 0 = no problems, inputted player can make a move
     * 1 = should skip turn
     * 2-4: End Game no player can make a move
     * 2 = black won
     * 3 = white won
     * 4 = tie
     * @param playerColor: the color of the player you want to test
     * @return : (see above lines)
     */
    public int canMakeMove(int playerColor){
        if(isPassNeeded(playerColor) == true){
            //player can make move
            return 0;
        }
        if(isPassNeeded(oppositeColor(playerColor)) == false){
            //No player can make a move
            updatePiecesCount();
            if(blackCount > whiteCount){
                //black won
                return 2;
            }
            else if(blackCount < whiteCount){
                //white won
                return 3;
            }
            else{
                //tie
                return 4;
            }
        }
        //only given player can't make a move
        return 1;
    }

    /**
     * Ryan Kane
     * Support method for isPassNeeded
     * @param playerColor
     * @return if given playerColor can make a move
     */
    public boolean isPassNeeded(int playerColor){
        int testScore = 0;
        //iterates through all possible moves
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                testScore = this.placePiece(i,j,playerColor, false);
                //if this move gives a higher gain, store it as the new "best move"
                if(0 < testScore){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Ryan Kane
     * @param origColor: BLACK or WHITE
     * @return the opposite color of the given color
     */
    private int oppositeColor (int origColor){
        if(origColor == BLACK){
            return WHITE;
        }
        else if(origColor == WHITE){
            return BLACK;
        }
        else{
            return 0;
        }
    }

    /**
     * Ryan Kane
     * Updates both the blackCount and the whiteCount to the correct values
     */
    public void updatePiecesCount(){
        blackCount = countPieces(BLACK);
        whiteCount = countPieces(WHITE);
    }

    /**
     * Ryan Kane
     * Support method for updatePiece count
     * Returns the number of pieces of the color given in the parameter
     * @param color Color of piece that needs to be counted
     * @return Number of pieces for the color
     */
    private int countPieces(int color){
        int total = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(this.board[i][j] == color){
                    total++;
                }
            }
        }
        return total;
    }

    /**
     * sets the whole array of booleans to false
     * Makes it so no pieces are in the justFlipped state
     */
    public void resetJustFlipped(){
        for (int i = 0; i < justFlipped.length; i++){
            for (int j = 0; j < justFlipped[i].length; j++){
                justFlipped[i][j] = false;
            }
        }
    }

    /**
     * Ryan Kane
     * test method "forces" a piece into a location without changing any other piece on the board
     * @param x x coordinate of placed piece
     * @param y y coordinate of placed piece
     * @param color
     */
    public void forcePlacement(int x, int y, int color){
        board[x][y] = color;
    }


}


/* @TODO delete if the code above is working
package edu.up.cs301.othello;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.infoMsg.GameState;

*/
/**
 * @author Aaron Leung, Kieran Losh, Aaron Moehnke, Ryan Kane on 3/3/2016.
 * @version 3/14/2016
 *//*

public class OthelloState extends GameState{


    private int whiteCount;
    private int blackCount;
    private int[][] board;

    private int turn;
    public static final int EMPTY = 0;
    public static final int WHITE = 1;
    public static final int BLACK = 2;

    */
/**
     * Constructor
     *//*

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

    */
/**
     * Copy constructor that does a deep copy
     * @param o OthelloState to copy from
     *//*

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

    */
/**
     * Getters and setters. Self-explanatory
     *//*

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

    */
/**
     * Places a piece at the coordinates if it is within the board bounds and the board exists
     * @param x x coord of the piece
     * @param y y coord of the piece
     * @param pieceColor color of the piece
     * @return true if the piece was placed, false if it was unable to be placed for some reason
     *//*

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

    */
/**
     *  Gets the color of the piece at the specified location, or returns an error code
     * @param x x coordinate
     * @param y y coordinate
     * @return -1 if invalid location or board, and the color of the piece otherwise
     *//*

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

    */
/**
     * Checks to see if the piece placement is a valid, legal move
     * @param x x coordinate
     * @param y y coordinate
     * @return True if placement is valid, False if placement is not valid.
     *//*

    public boolean validPiece(int x, int y) {
        return true;
    }

    */
/**
     * Flips the pieces that need to be flipped accordingly
     * @param x x coordinate
     * @param y y coordinate
     *//*

    public void flipPiece(int x, int y){

    }

    */
/**
     * Checks the pieces that need to be flipped upon piece placement
     * @param x x coordinate of placed piece
     * @param y y coordinate of placed piece
     *//*

    public void checkFlipPieces(int x, int y){
        //checks pieces that need to be flipped


        //calls flipPiece method for pieces that needed to be flipped
    }

    */
/**
     * Returns the number of pieces of the color given in the parameter
     * @param color Color of piece that needs to be counted
     * @return Number of pieces for the color
     *//*

    public int countPieces(int color){
        return 1;
    }


    */
/**
     * Checks if the game is over
     * @return index of the player who won,
     *         -1 if game is not over
     *//*

    public int checkIfGameOver(){
        return -1;
    }

}
*/
