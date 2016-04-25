package edu.up.cs301.othello;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.SurfaceView;

import java.io.Serializable;

import edu.up.cs301.game.R;

/**
 * Custom surfaceView class designed to act as the board in othello. The board has an array of pieces,
 * and draws them on its canvas. The board handles touch inputs and the presses of the confirm button.
 *
 * @author Aaron Leung
 * @author Ryan Kane
 * @author Austin Moehnke
 * @author Kieran Losh
 *
 * @date 3/30/16
 *
 */
public class BoardView extends SurfaceView implements Serializable{

    protected int pieces[][];
    private static final long serialVersionUID = 104182016l;
    private int gameEnd = 0;

    /**
     * Constructors
     * (overridden)
     */
    public BoardView(Context context) {
        super(context);
        setWillNotDraw(false);
        initPieces();

    }
    public BoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
        initPieces();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        initPieces();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        initPieces();
    }

    /**
     *  Initializes pieces array with empty-state pieces.
     *  This must be done before drawing
     */
    private void initPieces(){
        gameEnd = -1;
        pieces = new int[8][8];
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                pieces[i][j] = OthelloState.EMPTY;
            }
        }
        //generate bitmap objects for end game drawables
//        wonImage = BitmapFactory.decodeResource(getResources(), R.drawable.win_landscape_small);
//        lostImage = BitmapFactory.decodeResource(getResources(), R.drawable.lost_small);
//        tiedImage = BitmapFactory.decodeResource(getResources(), R.drawable.win_landscape_small);
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
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int current = pieces[i][j];
                if (current == OthelloState.EMPTY){
                    canvas.drawRect(5+i*width, 5+j*height, (i+1)*width -5, (j+1)*height -5, clearPaint);
                }
                else if (current == OthelloState.WHITE) {
                    canvas.drawOval(5 + i * width, 5 + j * height, (i + 1) * width - 5, (j + 1) * height - 5, whitePaint);

                }
                else if (current == OthelloState.BLACK) {
                    canvas.drawOval(5+ i*width, 5+ j*height, (i+1)*width -5, (j+1)*height -5, blackPaint);
                }

            }
        }
        //Clear board for end game (this didn't end up getting used)
        /**
         * External Citation
         * Date: 4/24/2016
         * Problem: We need to clear the board when the game is over to display the end game message
         * Resource: https://stackoverflow.com/questions/5729377/android-canvas-how-do-i-clear-delete-contents-of-a-canvas-bitmaps-livin
         * Solution: Set color of canvas to transparent with a clear mode
         */
        if (gameEnd == 1){ //clear board so that only the background shows
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }
    }



    //getters and setters
    public int[][] getPieces() {
        return pieces;
    }

    public void setPieces(int[][] pieces) {
        this.pieces = pieces;
    }

    public void setGameEnd(int gameEnd) {
        this.gameEnd = gameEnd;
    }
}

