package edu.up.cs301.othello;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by losh18 on 3/31/2016.
 */
public class PlacePieceAction extends GameAction {
    private GamePlayer player;
    private int x;
    private int y;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public PlacePieceAction(GamePlayer player) {
        super(player);
    }
}
