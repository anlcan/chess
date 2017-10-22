package com.baykal;

import com.baykal.model.Game;
import com.baykal.model.Player;
import com.baykal.model.Type;
import com.baykal.strategy.AbstractStrategy;
import com.baykal.strategy.CheckOptimizer;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StrategyTest {

    public static final Player BLACK = new Player("b", Type.BLACK);
    public static final Player WHITE = new Player("a", Type.WHITE);

    @Test
    public void strategyTest(){

        int winW = 0;
        int winB = 0;
        for (int i = 0; i < 10; i++) {
            //Game game = vsRandom(new CaptureOptimizeStrategy(Type.WHITE));
            Game game = vsRandom(new CheckOptimizer(Type.WHITE));
            if (game.isMate()){
                int k = game.nextTurn == WHITE? winW++ : winB++;
            }
        }

        assertTrue(winW > winB);
    }

    private Game vsRandom(AbstractStrategy strategy){
        WHITE.setStategy(strategy);
        Game game = new Game(BLACK, WHITE);

        Player winner = game.start();
        return game;
    }
}
