package edu.up.cs301.othello;

import android.app.Activity;
import android.view.View;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
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
public class OthelloHumanPlayer extends GameHumanPlayer {
    protected OthelloState os;
    private Activity myActivity;
    /**
     * constructor
     *
     * @param name
     */
    public OthelloHumanPlayer(String name) {
        super(name);
    }

    @Override
    public View getTopView() {
        return null;
    }

    @Override
    public void receiveInfo(GameInfo info) {

    }

    public void setAsGui(GameMainActivity activity){
        myActivity = activity;
        activity.setContentView(R.layout.othello_layout);
    }
    /**
     * Returns the GUI's top object; used for flashing.
     *
     * @return the GUI's top object.
     */

    /**
     * Callback method, called when player gets a message
     *
     * @param info
     * 		the message
     */

}
