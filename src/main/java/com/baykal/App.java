package com.baykal;

import com.baykal.model.Board;
import com.baykal.model.Game;
import com.baykal.model.Player;
import com.baykal.model.Type;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Game g = new Game(
                new Player("white" , Type.WHITE),
                new Player("black", Type.BLACK));
        g.start();
        g.getBoard().toString();

    }
}
