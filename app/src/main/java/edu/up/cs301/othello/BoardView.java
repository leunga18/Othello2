package edu.up.cs301.othello;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.up.cs301.game.R;
import edu.up.cs301.othello.Piece;

/**
 * @author Aaron Leung
 * @author Ryan Kane
 * @author Austin Moehnke
 * @author Kieran Losh
 *
 * Custom surfaceView class designed to act as the board in othello. The board has an array of pieces,
 * and draws them on its canvas. The board handles touch inputs and the presses of the confirm button.
 *
 */
public class BoardView extends SurfaceView implements View.OnTouchListener, View.OnClickListener{

    protected Piece pieces[][];
    protected float height;
    protected float width;
    protected long downTime;
    protected TextView counterBottom = null;
    protected TextView counterTop = null;
    protected Button confirmButton1 = null;
    protected Button confirmButton2 = null;
    protected int lastX = -1;
    protected int lastY;

    protected boolean color;


    /**
     * Constructors
     * (overridden)
     */
    public BoardView(Context context) {
        super(context);
        setWillNotDraw(false);
        initPieces();
        this.setOnTouchListener(this);
        color = Piece.BLACK; //Black goes first, so start with black

    }
    public BoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
        initPieces();
        this.setOnTouchListener(this);
        color = Piece.BLACK;
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        initPieces();
        this.setOnTouchListener(this);
        color = Piece.BLACK;
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        initPieces();
        this.setOnTouchListener(this);
        color = Piece.BLACK;
    }

    /**
     *  Initializes pieces array with empty-state pieces.
     *  This must be done before drawing
     */
    private void initPieces(){

        pieces = new Piece[8][8];

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                pieces[i][j] = new Piece();
            }

        }

    }

    @Override
    /**
     * Draws the pieces on the board using the 2-d array of pieces.
     */
    public void onDraw(Canvas canvas) {
        //get surfaceview size
        float width = this.getWidth();
        float height = this.getHeight();

        //we have 8 pieces, so the size of each piece will be 1/8th of each dimension
        height = height/8.0f;
        width = width/8.0f;

        //create paints
        Paint whitePaint = new Paint();
        whitePaint.setColor(Color.WHITE);
        whitePaint.setStyle(Paint.Style.FILL);
        Paint blackPaint = new Paint();
        blackPaint.setColor(Color.BLACK);
        blackPaint.setStyle(Paint.Style.FILL);
        Paint clearPaint = new Paint();
        clearPaint.setColor(0x00000000);
        clearPaint.setStyle(Paint.Style.FILL);

        //Draw the pieces. Uses array indices to offset the pieces,
        // size is determined based on view dimensions
        //@// TODO: 2/1/2016 Replace hard-coded padding values with variable
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece current = pieces[i][j];
                if (current.isEmpty() == true){
                    canvas.drawRect(5+i*width, 5+j*height, (i+1)*width -5, (j+1)*height -5, clearPaint);
                }
                else if (current.getColor() == Piece.WHITE) {
                    canvas.drawOval(5 + i * width, 5 + j * height, (i + 1) * width - 5, (j + 1) * height - 5, whitePaint);

                }
                else if (current.getColor() == Piece.BLACK) {
                    canvas.drawOval(5+ i*width, 5+ j*height, (i+1)*width -5, (j+1)*height -5, blackPaint);
                }

            }
        }


    }
    /**
     * onTouch handles the placement of pieces, it converts x,y inputs to i,j array values,
     * then toggles the piece at i,j to be visible/invisible.
     */
    public boolean onTouch(View v, MotionEvent event) {
        //make sure that we only process one touch per drag
        if (event.getDownTime() == this.downTime){
            //if it's the same press, ignore the touch event
            return false;
        }
        this.downTime = event.getDownTime();

        float x = event.getX();
        float y = event.getY();

        float width = this.getWidth();
        float height = this.getHeight();

        height = height/8.0f;
        width = width/8.0f;

        x /= width;
        y /= height;

        int i = (int) x;
        int j = (int) y;

        //if there isn't a piece where clicked, make the piece visible.
        //if the is a piece, make it invisible
        pieces[i][j].setEmpty(!pieces[i][j].isEmpty());
        //set the color of the piece clicked to the current turn's color
        pieces[i][j].setColor(color);
        //remove previous piece placed : you can only place one piece per turn
        //check to make sure it's not the first move (lastX = -1) or  that the piece you're removing
        //isn't the same piece that was just placed
        if(lastX != -1 && !(lastX == i && lastY == j)) {
            pieces[lastX][lastY].setEmpty(true);
        }
        //set last variable so we can remove the piece if another one is placed
        lastX = i;
        lastY = j;
        counterBottom.setText("" + i + ", " + j);
        //Log.i("board", "" + i + " " + j);

        //tell the board to redraw
        this.invalidate();


        return true;
    }

    /**
     * setTextView takes a reference to a TextView so that the board can modify that view.
     * If it is not passed from main, BoardView cannot get a reference to the TextView.
     * @param counterBottom - reference to the TextView that will display the count for black
     * @param counterTop - reference to the TextView the will display the white counter
     */
    public void setTextView(TextView counterBottom, TextView counterTop) {
        this.counterBottom = counterBottom;
        this.counterTop = counterTop;
    }

    /**
     * Takes button references that need to be monitored by BoardView (using the onClick listener)
     * and saves them as local private variables.
     * @param confirm1 - Confirm button one at the bottom of the screen
     * @param confirm2 - Confirm button two is at the top of the screen
     */
    public void setConfirmButtons(Button confirm1, Button confirm2){
        this.confirmButton1 = confirm1;
        this.confirmButton2 = confirm2;
    }

    /**
     * Detects when the confirm buttons are pressed, and changes the turn to the other player when
     * the current player presses their confirm button.
     */
    public void onClick(View v) {
        //Black player presses their confirm button and it is their turn:
        if (v.getId() == R.id.confirmButton){
        }
        //White player presses their confirm button and it is their turn:
        if (v.getId() == R.id.confirmTopButton){
        }
    }
}

