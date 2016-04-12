package edu.up.cs301.othello;

import android.util.Log;

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
public class OthelloComputerPlayer extends GameComputerPlayer {



    protected OthelloState os;
    protected int AIType;

    /*
    @param name: name of the player
    @param type: 0 if dumb ai, 1 if smart ai
     */
    public OthelloComputerPlayer(String name, int type) {
        super(name);
        this.AIType = type;
    }


    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof OthelloState) {
            os = (OthelloState) info;
            if (os.whoseTurn() == playerNum) {
                //if the ai cannot move, it must pass
                if (!os.isPassNeeded(getColor())){
                       game.sendAction(new OthelloPassAction(this));
                }
                //make a move
                else {
                    makeMove(os);
                }
            }
        }
        if (info instanceof NotYourTurnInfo){
            Log.i("ComputerPlayer", "played out of turn");
        }
    }

    /**
     *
     * @param state: the GameState the AI is acting on
     * @return if the AI could successfully make it's move
     */
    public boolean makeMove(OthelloState state){
        //Temporarily delay the AI's response
        try {
            Thread.sleep(1000);
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
        //iterates through all possible moves
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                testScore = state.placePiece(i,j, getColor(), false);
                //AI is dumb (random)
                if(AIType == 0 && testScore > 0){
                    spotsSeen++;
                    int ranNum = (int)Math.random()*(spotsSeen);
                    if(ranNum > (spotsSeen -2)){
                        xTarget = i;
                        yTarget = j;
                        maxScore = testScore;
                    }
                }
                else{
                    //AI is "smart"
                    //if this move gives a higher gain, store it as the new "best move"
                    if(maxScore < testScore && AIType == 1){
                        xTarget = i;
                        yTarget = j;
                        maxScore = testScore;
                    }
                }
            }
        }
        //if a move could be made, actually make the move
        if(maxScore > 0){
            state.placePiece(xTarget, yTarget, getColor(), true);
            state.finalizeBoard();
            game.sendAction(new OthelloPlacePieceAction(this, xTarget, yTarget, getColor()));
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
