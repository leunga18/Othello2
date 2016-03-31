package edu.up.cs301.othello;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.infoMsg.GameInfo;

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

    public OthelloComputerPlayer(String name) {
        super(name);
    }

    @Override
    protected void receiveInfo(GameInfo info) {

    }

    public boolean supportsGui() {
        return false;
    }
}
