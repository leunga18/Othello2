package edu.up.cs301.othello;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.actionMsg.GameAction;
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
public class OthelloHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener{
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


    /**
     * Returns the GUI's top object; used for flashing.
     *
     * @return the GUI's top object.
     */
    @Override
    public View getTopView() {
        return null;
    }

    /**
     * Callback method, called when player gets a message
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {
        if (info instanceof OthelloState){
            os = (OthelloState)info;
        }
    }

    public void setAsGui(GameMainActivity activity){
        myActivity = activity;
        activity.setContentView(R.layout.othello_layout);
        Button confirmButton = (Button)activity.findViewById(R.id.confirmButton);
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public void onClick(View v) {
        if (game == null) return;

        GameAction action = null;

        if (v.getId() == R.id.confirmButton) {
            action = new OthelloConfirmAction(this);
        }
        else {
            return;
        }

        game.sendAction(action);

    }




}
