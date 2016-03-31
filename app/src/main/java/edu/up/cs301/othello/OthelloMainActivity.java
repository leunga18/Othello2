package edu.up.cs301.othello;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * Created by losh18 on 3/31/2016.
 */
public class OthelloMainActivity extends GameMainActivity {
    public static final int PORT_NUMBER = 15555;
    @Override
    public GameConfig createDefaultConfig() {
        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // yellow-on-blue GUI
        playerTypes.add(new GamePlayerType("Local Human Player (blue-yellow)") {
            public GamePlayer createPlayer(String name) {
                return new OthelloHumanPlayer(name);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new OthelloComputerPlayer(name) {
                };
            }
        });

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
