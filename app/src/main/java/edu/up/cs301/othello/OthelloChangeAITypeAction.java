package edu.up.cs301.othello;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by losh18 on 3/31/2016.
 */
public class OthelloChangeAITypeAction extends GameAction implements Serializable{
    private static final long serialVersionUID = 22304182016l;

    public int getType() {
        return type;
    }

    public void setType(int t) {
        this.type = t;
    }

    private int type;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public OthelloChangeAITypeAction(GamePlayer player, int t) {
        super(player);
        this.type = t;
    }
}
