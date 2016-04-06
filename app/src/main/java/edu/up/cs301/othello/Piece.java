package edu.up.cs301.othello;

/**
 * @author Kieran Losh
 * @author Austin Moehnke
 * @author Aaron Leung
 * @author Ryan Kane
 */
public class Piece {

    //used instead of hard-coded false and true
    //@TODO change out color and empty bools and create state enum

    public static final boolean BLACK = false;
    public static final boolean WHITE = true;

    protected boolean empty;
    protected boolean color;

    /**
     * Default constructor. Sets empty to true
     */
    public Piece() {
        empty = true;
        color = false;

    }
    //Getters and setters for color and empty state

    /**
     * Returns whether the piece is empty (not on the board yet)
     * * @return boolean empty
     */
    public boolean isEmpty() {
        return empty;
    }

    /**
     * Gets the color of the piece
     * @return boolean color: white is true, black is false.
     */
    public boolean getColor() {
        return color;
    }

    /**
     * Sets whether the piece is empty (true) or on the board (false)
     * @param changeEmpty - set empty state
     */
    public void setEmpty(boolean changeEmpty) {
        empty = changeEmpty;
    }

    /**
     * Changes the color of the piece
     * @param changeColor - color to change the piece to (use Piece.WHITE or Piece.BLACK)
     */
    public void setColor(boolean changeColor) {
        color = changeColor;
    }
}
