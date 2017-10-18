package com.baykal.model;


import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:43
 */
public class Player {

    private final String name;
    private final Type type;

    public Player(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Move getMove(Board board) {
        // GAME LOGIC GOES HERE
        List<Move> collect = board.getPieces().stream()
                .filter(p -> p.getType().equals(type))
                .map(p -> p.moves(board))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return collect.get(ThreadLocalRandom.current().nextInt(collect.size()));
    }
}
