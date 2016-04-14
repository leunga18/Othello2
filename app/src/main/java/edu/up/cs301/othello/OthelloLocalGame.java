package edu.up.cs301.othello;

import android.util.Log;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * Created by losh18 on 3/31/2016.
 */
public class OthelloLocalGame extends LocalGame {
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
        Log.i("action", action.getClass().toString());
        if (action instanceof OthelloPassAction) {
            //check if correct player
            if (canMove(os.whoseTurn())) {

                    if (os.whoseTurn() == 0) {
                        os.setTurn(1);
                    } else {
                        os.setTurn(0);
                    }

                    sendAllUpdatedState();
                    return true;
            } else {
                action.getPlayer().sendInfo(new NotYourTurnInfo());
            }
        } else if (action instanceof OthelloPlacePieceAction) {
            //check if correct player
            OthelloPlacePieceAction place = (OthelloPlacePieceAction) action;
            if (canMove(getPlayerIdx(place.getPlayer()))) {
                os.placePiece(place.getX(), place.getY(), place.getColor(), true);
                Log.i("LocalGame", "" + place.getColor() + " placed at " + place.getX() + ", " + place.getY() + "");
                if (os.whoseTurn() == 0) {
                    os.setTurn(1);
                } else {
                    os.setTurn(0);
                }
                os.finalizeBoard();
                sendAllUpdatedState();
                return true;
            } else {
                place.getPlayer().sendInfo(new NotYourTurnInfo());
            }
            //place piece
        }
        return false;
    }

}

