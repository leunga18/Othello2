package edu.up.cs301.othello;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * An AI delay change action that an Othello human player sends to the game to change the delay of the AI.
 *
 * @author Aaron Leung
 * @author Kieran Losh,
 * @author Austin Moehnke
 * @author Ryan Kane
 *
 * @date 3/31/2016
 */
public class OthelloChangeAIDelayAction extends GameAction implements Serializable{
    private static final long serialVersionUID = 12304182016l;

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private int delay;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public OthelloChangeAIDelayAction(GamePlayer player, int delay) {
        super(player);
        this.delay = delay;
    }
}
