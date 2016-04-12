package edu.up.cs301.othello;

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
        int playerIdx = os.canMakeMove(os.whoseTurn());
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof OthelloPassAction) {
            //check if correct player
            if (canMove(getPlayerIdx(action.getPlayer()))) {
                if (os.canMakeMove(1 + getPlayerIdx(action.getPlayer())) == 1) {
                    //change turn
                    if (os.whoseTurn() == 0) {
                        os.setTurn(1);
                    } else {
                        os.setTurn(0);
                    }

                    sendAllUpdatedState();
                    return true;
                }
            } else {
                action.getPlayer().sendInfo(new NotYourTurnInfo());
            }
        } else if (action instanceof OthelloPlacePieceAction) {
            //check if correct player
            OthelloPlacePieceAction place = (OthelloPlacePieceAction) action;
            if (canMove(getPlayerIdx(place.getPlayer()))) {
                os.placePiece(place.getX(), place.getY(), place.getColor(), true);
                if (os.whoseTurn() == 0) {
                    os.setTurn(1);
                } else {
                    os.setTurn(0);
                }
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

