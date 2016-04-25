package edu.up.cs301.othello;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

//@TODO remove commented out debugging logging

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
public class OthelloHumanPlayer extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener, SeekBar.OnSeekBarChangeListener, Serializable{
    private static final long serialVersionUID = 504182016l;
    protected OthelloState os;
    private Activity myActivity;
    private BoardView board;
    private long downTime;
    private TextView counterBottom;
    private TextView counterTop;
    private ImageView turnImage;
    private Button confirmButton;
    private Button menuButton;
    private boolean hasNotMoved = true;
    private boolean passNeeded = false;
    private Button smartButton;
    private Button dumbButton;
    private Button resetButton;
    private SeekBar aiSeekBar;
    private Button backButton;


    private int color;

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
            this.os = (OthelloState)info;
            if (os.whoseTurn() == playerNum){
                //if a move hasn't been made, change the confirm button to be greyed out
                if (hasNotMoved && os.isPassNeeded(getColor())){
                    confirmButton.setBackgroundResource(R.drawable.confirm_grey);
                }
                //change the confirm button to pass button if needed
                else if (!os.isPassNeeded(getColor()) && hasNotMoved == true){
                    confirmButton.setBackgroundResource(R.drawable.pass_button);
                    passNeeded = true;
                }
                else{
                    confirmButton.setBackgroundResource(R.drawable.confirm);
                    passNeeded = false;
                }
            }
            //load variables from new state and display them
            board.setPieces(os.getBoard());

            color = getColor();
            //draw board
            board.invalidate();
            os.updatePiecesCount();
            counterBottom.setText("" + os.getBlackCount());
            counterTop.setText("" + os.getWhiteCount());
            //set turn image
            if (os.whoseTurn() == 0){
                turnImage.setImageResource(R.drawable.turn_black);
            }
            else {
                turnImage.setImageResource(R.drawable.turn_white);
            }
        }
        //flash red if not your turn
        if (info instanceof NotYourTurnInfo){
            flash(0xFFFF0000, 50);
        }
    }

    public void setAsGui(GameMainActivity activity){

        myActivity = activity;

        initializeViews();
    }

    /**
     * Gets references to all the GUI views and sets listeners.
     */
    private void initializeViews(){
        myActivity.setContentView(R.layout.othello_layout);
        //lock to portrait location
        myActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //get view references
        board = (BoardView)myActivity.findViewById(R.id.boardView);
        confirmButton = (Button)myActivity.findViewById(R.id.confirmButton);
        counterBottom = (TextView)myActivity.findViewById(R.id.playerCountBottom);
        counterTop = (TextView)myActivity.findViewById(R.id.playerTopCount);
        menuButton = (Button)myActivity.findViewById(R.id.menuButton);
        //set listeners
        menuButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
        board.setOnTouchListener(this);
        turnImage = (ImageView)myActivity.findViewById(R.id.turnImage);
        board.invalidate();
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (os == null){
            return false;
        }
        //only handle event from the boardView
        if (v.getId() == R.id.boardView){
            //check to see if it's your turn
            if (os.whoseTurn() == playerNum){
                //make sure that we only process one touch per drag (we don't want to spam placements)
                if (event.getDownTime() == downTime) {
                    //if it's the same press, ignore the event
                    return false;
                }
                downTime = event.getDownTime();

                //generate a (i,j) location that corresponds to the place on the board that was touched
                float x = event.getX();
                float y = event.getY();

                float width = board.getWidth();
                float height = board.getHeight();

                height /= 8.0f;
                width /= 8.0f;

                x /= width;
                y /= height;

                int i = (int) x;
                int j = (int) y;

                //generate a place piece action
                GameAction place = null;
                if (playerNum == 0) {
                    if (os.placePiece(i, j, OthelloState.BLACK, true) == 0) {
                        flash(0xFFFF9900, 100);
                    } else {
                        hasNotMoved = false;
                        OthelloPlacePieceAction action = new OthelloPlacePieceAction(this, i, j, getColor());
                        //action = new OthelloPassAction(this);
                        game.sendAction(action);
                    }
                } else {
                    if (os.placePiece(i, j, OthelloState.WHITE, true) == 0) {
                        flash(0xFFFF9900, 100);
                    } else {
                        hasNotMoved = false;
                        OthelloPlacePieceAction action = new OthelloPlacePieceAction(this, i, j, getColor());
                        //action = new OthelloPassAction(this);
                        game.sendAction(action);
                    }
                }
                //redraw board
                board.invalidate();
                //recount pieces
                os.updatePiecesCount();
                counterBottom.setText("" + os.getBlackCount());
                counterTop.setText("" + os.getWhiteCount());
            }
            else{
                flash(0xFFFF0000, 100);
            }
        }
        return false;
    }

    public void onClick(View v) {
        if (game == null || v == null || os == null){
            flash(0xFFFF0000, 100);
            return;
        }
        GameAction action = null;

        if (v.getId() == R.id.confirmButton) {
            //if a pass is needed, make the confirm button pass
            if (passNeeded){
                game.sendAction(new OthelloPassAction(this));
                game.sendAction(new OthelloChangeTurnAction(this));
                hasNotMoved = true;
                return;
            }
            else if (os.whoseTurn() == playerNum && !hasNotMoved){
                game.sendAction(new OthelloConfirmAction(this));
                game.sendAction(new OthelloChangeTurnAction(this));
                hasNotMoved = true;
            }
            else {
                /**
                 * External Citation
                 *  Date: 4/10/2016
                 *  Problem: Flash was not restoring the background to the original image
                 *  Resource:
                 *      http://stackoverflow.com/questions/11737607/how-to-set-the-image-from-drawable-dynamically-in-android
                 *  Solution: Set the background image again after the flash happens.
                 */
                flash(0xFFFF0000, 100);
            }
        } //if the menu button is pressed, load the menu layout and get references to buttons
        else if (v.getId() == R.id.menuButton && os.getGameEnd() == null){
            myActivity.setContentView(R.layout.menu);
            resetButton = (Button)myActivity.findViewById(R.id.resetButton);
            aiSeekBar = (SeekBar)myActivity.findViewById(R.id.aiDelaySeekBar);
            backButton = (Button)myActivity.findViewById(R.id.backButton);
            dumbButton = (Button)myActivity.findViewById(R.id.dumbButton);
            smartButton = (Button)myActivity.findViewById(R.id.smartButton);
            dumbButton.setOnClickListener(this);
            smartButton.setOnClickListener(this);
            resetButton.setOnClickListener(this);
            aiSeekBar.setOnSeekBarChangeListener(this);
            backButton.setOnClickListener(this);
            aiSeekBar.setMax(2000);
            aiSeekBar.setProgress(os.getDelay());

        } //resets the board when pressed
        else if (v.getId() == R.id.resetButton){
            game.sendAction(new OthelloResetGameAction(this));
            initializeViews();
            board.setGameEnd(0);
            game.sendAction(new OthelloChangeAIDelayAction(this, os.getDelay()));
        }
        else if (v.getId() == R.id.backButton){
            //reset to te regular board
            initializeViews();
            game.sendAction(new OthelloChangeAIDelayAction(this, os.getDelay()));
        }
        else if (v.getId() == R.id.smartButton){
            //who knows why this works with 3 but not one
            game.sendAction(new OthelloChangeAITypeAction(this, 1));
            game.sendAction(new OthelloChangeAITypeAction(this, 1));
            game.sendAction(new OthelloChangeAITypeAction(this, 1));
        }
        else if (v.getId() == R.id.dumbButton){
            game.sendAction(new OthelloChangeAITypeAction(this, 0));
            game.sendAction(new OthelloChangeAITypeAction(this, 0));
            game.sendAction(new OthelloChangeAITypeAction(this, 0));
        }
    }
    //sets the color of the human player
    public void setColor(int color) {
        this.color = color;
    }
    //gets color of the human player
    private int getColor(){
        if (playerNum == 0)
            color = OthelloState.BLACK;
        else
            color = OthelloState.WHITE;
        return color;
    }

    //Sets AI delay
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.aiDelaySeekBar){
            game.sendAction(new OthelloChangeAIDelayAction(this, progress));
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
        //don't care
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        //don't care
    }

}
