package edu.up.cs301.othello;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by losh18 on 3/31/2016.
 */
public class OthelloResetGameAction extends GameAction implements Serializable{
    private static final long serialVersionUID = 404182016l;

    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public OthelloResetGameAction(GamePlayer player) {
        super(player);
    }
}
