package edu.up.cs301.othello;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by losh18 on 3/31/2016.
 */
public class OthelloLocalGame extends LocalGame{
    private OthelloState os;

    public OthelloLocalGame(){
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
        int playerIdx = os.checkIfGameOver();
        return null;
    }

    @Override
    protected boolean makeMove(GameAction action) {
        if (action instanceof OthelloConfirmAction){
            //check if correct player
            //check if piece is placed
            //flip pieces
            //change player
        }
        else if (action instanceof OthelloPassAction){
            //check if correct player
            //change player
        }
        else if (action instanceof OthelloPlacePieceAction){
            //check if correct player
            //check if legal move
            //place piece
        }
        return false;
    }
}
