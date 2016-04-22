package edu.up.cs301.othello;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-change-turn action that an Othello player sends to the game to change a player's turn.
 *
 * @author Aaron Leung
 * @author Kieran Losh,
 * @author Austin Moehnke
 * @author Ryan Kane
 *
 * @date 3/31/2016
 */
public class OthelloChangeTurnAction extends GameAction implements Serializable{
    private static final long serialVersionUID = 204182016l;


    public OthelloChangeTurnAction(GamePlayer p){
        super(p);
    }
}
