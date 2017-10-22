package com.baykal.model;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:45
 */
public class Game {

    private Player one;
    private Player two;

    private Board board;

    public Game(Player one, Player two) {
        if (one.getType() == Type.BLACK) {
            assert (Type.WHITE == two.getType());
        } else {
            assert (Type.WHITE == one.getType());
        }

        this.one = one;
        this.two = two;
        this.board = new Board();
    }

    private Player opposite(Player p1) {
        return p1 == one? two : one;
    }

    public Player start() {
        Player nextTurn = one.getType() == Type.WHITE? one : two;
        while(board.moveTexts.size() < 120) {
            Move nextMove = nextTurn.getMove(board);
            if ( null == nextMove) {
                // mate
                return opposite(nextTurn);
            }

            board.apply(nextMove);
            nextTurn = opposite(nextTurn);
        }

        return nextTurn;
    }

    public Board getBoard() {
        return board;
    }
}
