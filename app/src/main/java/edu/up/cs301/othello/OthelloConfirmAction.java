package edu.up.cs301.othello;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-confirm action that an Othello player sends to the game to confirm a piece placement.
 *
 * @author Aaron Leung
 * @author Kieran Losh,
 * @author Austin Moehnke
 * @author Ryan Kane
 *
 * @date 3/31/2016
 */
public class OthelloConfirmAction extends GameAction implements Serializable{
    private static final long serialVersionUID = 404182016l;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public OthelloConfirmAction(GamePlayer player) {
        super(player);
    }
}
