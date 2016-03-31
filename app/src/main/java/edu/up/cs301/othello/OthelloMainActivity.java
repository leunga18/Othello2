package edu.up.cs301.othello;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;

/**
 * Created by losh18 on 3/31/2016.
 */
public class OthelloMainActivity extends GameMainActivity {
    @Override
    public GameConfig createDefaultConfig() {
        return null;
    }

    @Override
    public LocalGame createLocalGame() {
        return new OthelloLocalGame();
    }
}
