package edu.up.cs301.othello;

import org.junit.Test;

import dalvik.annotation.TestTargetClass;
import edu.up.cs301.othello.OthelloState;

import static org.junit.Assert.*;

/**
 * @author Aaron Leung, Kieran Losh, Aaron Moehnke, Ryan Kane on 3/3/2016.
 * @version 3/14/2016
 */
public class OthelloStateTest {

    /**
     * External Citation
     * Date: 3/14/2016 Pi Day
     * Problem: We couldn't remember how to set up unit testing
     * Resource: https://learning.up.edu/moodle/pluginfile.php/222737/mod_resource/content/2/CS371_JUnit_ver04.pdf
     * Solution: Read the lab instructions on the subject
     */


    @Test//test constructor
    public void testConstructor() {
        OthelloState o = new OthelloState();
        assertEquals(0, o.whoseTurn());
        assertEquals(2, o.getBlackCount());
        assertEquals(2, o.getWhiteCount());
        int[][] board = o.getBoard();
        assertEquals(OthelloState.WHITE, board[3][3]);
        assertEquals(OthelloState.BLACK, board[4][3]);
        assertEquals(OthelloState.BLACK, board[3][4]);
        assertEquals(OthelloState.WHITE, board[4][4]);
    }

    @Test
    public void testCopyConstructor(){
        OthelloState o = new OthelloState();
        //change some default variables
        int[][] board = new int[8][8];
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                board[i][j] = i%2; //change pieces in columns
            }
        }
        o.setBoard(board);
        o.setTurn(1);
        o.setWhiteCount(32);
        o.setBlackCount(0);
        //create a new object using copy constructor
        OthelloState copy = new OthelloState(o);
        //check variable states
        assertEquals(1, copy.whoseTurn());
        assertEquals(0, copy.getBlackCount());
        assertEquals(32, copy.getWhiteCount());
        board = copy.getBoard();
        assertEquals(OthelloState.EMPTY, board[0][0]);
        assertEquals(OthelloState.WHITE, board[1][0]);
        //make sure that the board pointers are different
        assertEquals(false, o.getBoard() == copy.getBoard());
    }

    @Test //test a valid piece placement
    public void testValidPlacePiece(){
        OthelloState o = new OthelloState();
        assertEquals(true, o.placePiece(0,0,OthelloState.BLACK));
    }

    @Test //test piece placement if the board isn't initialized
    public void testInvalidPlacePieceNoBoard(){
        OthelloState o = new OthelloState();
        o.setBoard(null);
        assertEquals(false, o.placePiece(0,0,OthelloState.BLACK));
    }

    @Test //test piece placement if the location is out of bounds
    public void testInvalidPlacePieceOutOfBounds(){
        OthelloState o = new OthelloState();
        assertEquals(false, o.placePiece(-1,-1, OthelloState.BLACK));
    }

    @Test //test piece placement if the piece color is location
    public void testInvalidPlacePieceInvalidColor(){
        OthelloState o = new OthelloState();
        assertEquals(false, o.placePiece(0, 0, 5));
    }

    @Test //test piece getting if the location is valid
    public void testValidGetPiece(){
        OthelloState o = new OthelloState();
        assertEquals(OthelloState.WHITE, o.getPiece(3, 3));
    }

    @Test //test piece getting if the location is invalid
    public void testInvalidGetPieceOutOfBounds(){
        OthelloState o = new OthelloState();
        assertEquals(-1, o.getPiece(8, 10));
    }

}