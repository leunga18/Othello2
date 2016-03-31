package edu.up.cs301.game;

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
public abstract class OthelloComputerPlayer implements GamePlayer {

    protected abstract void receiveInfo(GameInfo info);

}
