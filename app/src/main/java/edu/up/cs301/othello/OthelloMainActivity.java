package edu.up.cs301.othello;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * This is the primary activity for the Othello game
 *
 * @author Aaron Leung
 * @author Kieran Losh,
 * @author Austin Moehnke
 * @author Ryan Kane
 *
 * @date 3/31/2016
 */
public class OthelloMainActivity extends GameMainActivity implements Serializable{
    private static final long serialVersionUID = 704182016l;

    public static final int PORT_NUMBER = 15555;
    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // yellow-on-blue GUI
        playerTypes.add(new GamePlayerType("Local Human Player") {
            public GamePlayer createPlayer(String name) {
                return new OthelloHumanPlayer(name);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new OthelloComputerPlayer(name, 0) {
                };
            }
        });

        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new OthelloComputerPlayer(name, 1) {
                };
            }
        });

        if (playerTypes.get(1) == null || playerTypes.get(0) == null){
            Log.i("Main", "player is null");
        }

        // Create a game configuration class for Tic-tac-toe
        GameConfig defaultConfig = new GameConfig(playerTypes, 2,2, "Othello", PORT_NUMBER);

        // Add the default players
        defaultConfig.addPlayer("Human", 0); // yellow-on-blue GUI
        defaultConfig.addPlayer("Computer", 1); // dumb computer player

        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1); // red-on-yellow GUI

        //done!
        return defaultConfig;

    }

    @Override
    public LocalGame createLocalGame() {
        return new OthelloLocalGame();
    }
}
