package com.baykal;


import com.baykal.model.Board;
import com.baykal.model.Game;
import com.baykal.model.Player;
import com.baykal.model.Position;
import com.baykal.model.Type;

import org.junit.Test;

import java.util.List;

import javafx.geometry.Pos;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
    public void positionDiff(){

        Position p1 = new Position("a", 7);
        Position p2 = new Position("a", 5);

        List<Position> diff1 = p1.diff(p2);
        assertTrue(diff1.size() == 1);
        assertTrue(diff1.get(0).toString().equals("a6"));


        Position p11 = new Position("c", 1);
        Position p22 = new Position("a", 1);

        List<Position> diff2 = p11.diff(p22);
        assertTrue(diff2.size() == 1);
        assertTrue(diff2.get(0).toString().equals("b1"));

    }
}
