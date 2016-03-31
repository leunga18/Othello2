package edu.up.cs301.game;

import android.view.View;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by AaronLeung on 3/30/16.
 */
public abstract class OthelloHumanPlayer implements GameHumanPlayer{
    /**
     * Returns the GUI's top object; used for flashing.
     *
     * @return the GUI's top object.
     */
    public abstract View getTopView();

    /**
     * Callback method, called when player gets a message
     *
     * @param info
     * 		the message
     */
    public abstract void receiveInfo(GameInfo info);

}
