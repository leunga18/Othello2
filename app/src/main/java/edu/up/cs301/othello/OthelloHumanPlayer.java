package edu.up.cs301.othello;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;

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
    private BoardView board;

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
        return (View)myActivity.findViewById(R.id.top_gui_layout);
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
        board = (BoardView)activity.findViewById(R.id.boardView);
        Button confirmButton = (Button)activity.findViewById(R.id.confirmButton);
        TextView counterBottom = (TextView)activity.findViewById(R.id.playerCountBottom);
        TextView counterTop = (TextView)activity.findViewById(R.id.playerTopCount);
        board.setConfirmButtons(confirmButton, null);
        board.setTextView(counterBottom, counterTop);
        confirmButton.setOnClickListener(this);
    }

    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public void onClick(View v) {
        if (game == null) return;

        GameAction action = null;

        if (v.getId() == R.id.confirmButton) {
            action = new OthelloConfirmAction(this);
            getTopView().setBackgroundResource(R.drawable.board);
            super.flash(0xFFFF0000, 100);
            Log.i("HumanPlayer", "flash");
        }
        else {
            return;
        }

        game.sendAction(action);

    }


    /**
     *
     * @param color
     * 			the color to flash
     * @param ms
     */
    @Override
    public void flash(int color, int ms){
        /**
         * External Citation
         * http://stackoverflow.com/questions/11737607/how-to-set-the-image-from-drawable-dynamically-in-android
         * @TODO add citation
         */
        View top = getTopView();
        top.setBackgroundResource(R.drawable.board_flash);
        board.invalidate();
        try{
            Thread.sleep(ms);
        }
        catch (InterruptedException ie){
            //don't care
        }
        top.setBackgroundColor(0x00000000);

    }


}
