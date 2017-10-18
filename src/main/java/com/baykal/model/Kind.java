package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:56
 */
public enum Kind {

    KING("N"),
    QUEEN("Q"),
    ROOK("R"),
    BISHOP("B"),
    KNIGHT("N"),
    PAWN(" ");

    private final String PGN_SIGN;

    Kind(String sign) {
        PGN_SIGN = sign;
    }

    public String getSign() {
        return PGN_SIGN;
    }
}
