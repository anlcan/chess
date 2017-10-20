package com.baykal;


import com.baykal.model.Kind;
import com.baykal.model.Position;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

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

    @Test
    public void positionBishop() {

        Position p = new Position("d", 5);
        List<Position> positions = Kind.BISHOP.positions.apply(p);
        assertTrue(positions.size() == 13);

    }

    @Test
    public void diagonalDiff() {

        Position p1 = new Position("a", 1);
        Position p2 = new Position("h", 8);
        List<Position> diff2 = p1.diff(p2);
        assertTrue(diff2.size() == 6);

        List<Position> diff1 = p2.diff(p1);
        assertTrue(diff2.size() == 6);

        assertTrue(diff1.containsAll(diff2));


        Position p3 = new Position("g", 2);
        Position p4 = new Position("b", 7);

        List<Position> diff3 = p3.diff(p4);
        assertTrue(diff3.size() == 4);

        List<Position> diff4 = p4.diff(p3);
        assertTrue(diff4.size() == 4);

        assertTrue(diff3.containsAll(diff4));
    }

}
