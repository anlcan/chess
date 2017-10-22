package com.baykal.model;


import com.baykal.strategy.CaptureOptimize;
import com.baykal.strategy.Random;

import java.util.function.Function;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:43
 */
public class Player {

    private final String name;
    private final Type type;

    private Function<Board, Move> moveStrategy;

    public Player(String name, Type type) {
        this.name = name;
        this.type = type;
        this.moveStrategy = new Random(type);
    }

    public Move getMove(Board board){
        return moveStrategy.apply(board);
    }

    public Type getType() {
        return type;
    }

    public void setStategy(CaptureOptimize stategy) {
        this.moveStrategy = stategy;
    }
}
