package edu.up.cs301.game;

import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * Created by AaronLeung on 3/30/16.
 */
public abstract class OthelloComputerPlayer implements GameComputerPlayer {

    protected abstract void receiveInfo(GameInfo info);

}
