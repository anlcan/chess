package com.baykal.model;

import java.util.List;
import java.util.function.Function;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:56
 */
public enum Kind {

    KING(position -> null),
    QUEEN(position -> null),
    ROOK(position -> null),
    BISHOP(position -> null),
    KNIGHT(position -> null),
    PAWN(position -> null);

    private final Function<Position, List<Position>> possiblePosition;

    Kind(Function<Position, List<Position>> positionSupplier) {
        possiblePosition = positionSupplier;
    }
}
