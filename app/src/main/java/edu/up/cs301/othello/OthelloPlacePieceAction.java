package edu.up.cs301.othello;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-place-piece action that an Othello player sends to the game to place a piece on the board.
 *
 * @author Aaron Leung
 * @author Kieran Losh,
 * @author Austin Moehnke
 * @author Ryan Kane
 *
 * @date 3/31/2016
 */
public class OthelloPlacePieceAction extends GameAction implements Serializable{
    private static final long serialVersionUID = 904182016l;

    private GamePlayer player;
    private int x;
    private int y;


    private int color;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public OthelloPlacePieceAction(GamePlayer player, int x, int y, int color) {
        super(player);
        this.x = x;
        this.y = y;
        this.color = color;
    }

    //Getters and Setters

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}
