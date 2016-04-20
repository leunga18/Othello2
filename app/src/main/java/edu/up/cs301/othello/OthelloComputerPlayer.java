package edu.up.cs301.othello;

import android.util.Log;

import java.io.Serializable;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Computer player for Othello Game
 *
 * @author Aaron Leung
 * @author Ryan Kane
 * @author Kieran Losh
 * @author Austin Moehnke
 *
 * @date 30 March 2016
 */
public class OthelloComputerPlayer extends GameComputerPlayer implements Serializable{

    private static final long serialVersionUID = 304182016l;

    private int AIDelay = 0;
    protected OthelloState os;
    protected int AIType;
    protected boolean hasMoved = false;
    protected boolean firstRun = true;

    /*
     * getters and setters self explanatory
     */
    public int getAIDelay() {
        return AIDelay;
    }

    public void setAIDelay(int newDelay){
        AIDelay = newDelay;
    }

    public void setAIType(int newType){
        AIType = newType;
    }

    public int getAIType(){
        return AIType;
    }

    /*
    @param name: name of the player
    @param type: 0 if dumb ai, 1 if smart ai
     */
    public OthelloComputerPlayer(String name, int type) {
        super(name);
        this.AIType = type;
        hasMoved = false;
        firstRun = true;
    }


    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof OthelloState) {
            os = (OthelloState) info;
            AIDelay = os.getDelay();

            if (os.isAiTypeChanged()){
                AIType = os.getAiType();
            }

            if (os.whoseTurn() == playerNum && !hasMoved) {
                //make a move
                makeMove(os);
                game.sendAction(new OthelloChangeTurnAction(this));
                hasMoved = false;
            }
        }
        if (info instanceof NotYourTurnInfo){
            //Log.i("ComputerPlayer", "played out of turn");
        }
    }

    /**
     *
     * @param state: the GameState the AI is acting on
     * @return if the AI could successfully make it's move
     */
    public boolean makeMove(OthelloState state){
        //Temporarily delay the AI's response
        hasMoved = true;
        try {
            Thread.sleep(AIDelay);
        }
        catch (InterruptedException e) {
            //do nothing
        }
        //stores "best move" data
        int xTarget = -1;
        int yTarget = -1;
        int maxScore = 0;
        //stores testing Variable
        int testScore = 0;
        int spotsSeen = 0;
        int ranNum = (int) (Math.random() * 10000);
        //iterates through all possible moves
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                testScore = state.placePiece(i,j, getColor(), false);
                if(testScore > 0) {
                    //AI is dumb (random)
                    if (AIType == 0) {
                        spotsSeen++;
                        ranNum = (int) (Math.random() * 10000);
                        if (ranNum < (10000 / spotsSeen)) {
                            xTarget = i;
                            yTarget = j;
                            maxScore = testScore;
                        }
                    }
                    //AI is "smart"
                    else {
                        //if the move is on the outskirts of the board
                        //the move is made more favorable
                        if(i == 0 || i == 7){
                            testScore++;
                        }
                        if(j == 0 || j == 7){
                            testScore++;
                        }
                        //if this move gives a higher gain, store it as the new "best move"
                        if (maxScore < testScore && AIType == 1) {
                            xTarget = i;
                            yTarget = j;
                            maxScore = testScore;
                        }
                    }
                }
            }
        }
        //if a move could be made, actually make the move
        if(maxScore > 0){
            //state.placePiece(xTarget, yTarget, getColor(), true);
            //state.finalizeBoard();
            game.sendAction(new OthelloPlacePieceAction(this, xTarget, yTarget, getColor()));
            game.sendAction(new OthelloConfirmAction(this));
            return true;
        }
        //no move could be made, so pass
        game.sendAction(new OthelloPassAction(this));
        return false;
    }

    private int getColor(){
        if (playerNum == 0)
            return OthelloState.BLACK;
        else
            return OthelloState.WHITE;
    }

    public boolean supportsGui() {
        return false;
    }
}
