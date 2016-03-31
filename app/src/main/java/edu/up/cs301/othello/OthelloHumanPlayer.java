package edu.up.cs301.othello;

import android.view.View;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Human player for Othello Game
 *
 * @author Aaron Leung
 * @author Ryan Kane
 * @author Kieran Losh
 * @author Austin Moehnke
 *
 * @date 30 March 2016
 */
public abstract class OthelloHumanPlayer extends GameHumanPlayer {
    /**
     * constructor
     *
     * @param name
     */
    public OthelloHumanPlayer(String name) {
        super(name);
    }

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
