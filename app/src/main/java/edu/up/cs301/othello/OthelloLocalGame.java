package edu.up.cs301.othello;

import android.util.Log;

import java.io.Serializable;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by losh18 on 3/31/2016.
 */
public class OthelloLocalGame extends LocalGame implements Serializable {
    private static final long serialVersionUID = 604182016l;

    private OthelloState os;

    public OthelloLocalGame() {
        super();
        os = new OthelloState();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        p.sendInfo(new OthelloState(os));
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return playerIdx == os.whoseTurn();
    }

    @Override
    protected String checkIfGameOver() {
        int color;
        if (os.whoseTurn() == 1){
            color = OthelloState.WHITE;
        }
        else{
            color = OthelloState.BLACK;
        }
        int status = os.canMakeMove(color);
        switch(status){
            case 0:
                //game is proceeding normally
                return null;
            case 2:
                //first player won
                return "" + playerNames[0] + " won!";
            case 3:
                //2nd player won
                return "" + playerNames[1] + " won!";
            case 4:
                //tie
                return "Tie!";
            default:
                //we don't care
        }
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        //Log.i("action", action.getClass().toString());
        if (action instanceof OthelloPassAction) {
            //check if correct player
            if (canMove(os.whoseTurn())) {
                    sendAllUpdatedState();
                    return true;
            } else { //not the correct player
                action.getPlayer().sendInfo(new NotYourTurnInfo());
            }
        } else if (action instanceof OthelloPlacePieceAction) {
            //check if correct player
            OthelloPlacePieceAction place = (OthelloPlacePieceAction) action;
            if (canMove(getPlayerIdx(place.getPlayer()))) {
                os.placePiece(place.getX(), place.getY(), place.getColor(), true);
                Log.i("LocalGame", "" + place.getColor() + " placed at " + place.getX() + ", " + place.getY() + "");
                place.getPlayer().sendInfo(new OthelloState(os));
                return true;
            } else {
                place.getPlayer().sendInfo(new NotYourTurnInfo());
            }
            //place piece
        }
        else if (action instanceof OthelloConfirmAction){
            os.finalizeBoard();
            sendAllUpdatedState();
        }
        else if (action instanceof OthelloChangeTurnAction){
            changeTurn();
            sendAllUpdatedState();
        }
        else if (action instanceof OthelloChangeAIDelayAction)
        {
            os.setDelay(((OthelloChangeAIDelayAction) action).getDelay());
            sendAllUpdatedState();
        }
        else if (action instanceof OthelloChangeAITypeAction){
            // Log.i("LocalGame", "Action ai type: " + ((OthelloChangeAITypeAction) action).getType() + " OS AI type: " + os.getAiType());
            os.setAiType(((OthelloChangeAITypeAction) action).getType());
            os.setAiTypeChanged(true);
            sendAllUpdatedState();
            os.setAiTypeChanged(false);
        }
        else if (action instanceof OthelloResetGameAction){
            os = new OthelloState();
            sendAllUpdatedState();
        }

        return false;
    }

    //changes turn
    public void changeTurn(){
        //Log.i("LocalGame", "Current Turn: " + os.whoseTurn());
        if (os.whoseTurn() == 0){
            os.setTurn(1);
        }
        else if (os.whoseTurn() == 1){
            os.setTurn(0);
        }
        //Log.i("LocalGame", "Next Turn: " + os.whoseTurn());
    }

}

