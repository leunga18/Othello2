package edu.up.cs301.othello;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * A game-pass action that an Othello player sends to the game to pass a turn
 * when no moves can be made during the turn.
 *
 * @author Aaron Leung
 * @author Kieran Losh,
 * @author Austin Moehnke
 * @author Ryan Kane
 *
 * @date 3/31/2016
 */
public class OthelloPassAction extends GameAction implements Serializable {
    private static final long serialVersionUID = 804182016l;

    private GamePlayer player;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public OthelloPassAction(GamePlayer player) {
        super(player);
    }
}
