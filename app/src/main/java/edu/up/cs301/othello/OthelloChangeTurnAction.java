package edu.up.cs301.othello;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by losh18 on 4/14/2016.
 */
public class OthelloChangeTurnAction extends GameAction implements Serializable{
    private static final long serialVersionUID = 204182016l;


    public OthelloChangeTurnAction(GamePlayer p){
        super(p);
    }
}
