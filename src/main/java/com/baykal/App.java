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
                new Player("white", Type.WHITE),
                new Player("black", Type.BLACK));
        System.out.println((char)27 + "[31mThis text would show up red" + (char)27 + "[0m other text");
        g.start();


    }
}
