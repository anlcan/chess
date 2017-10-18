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

    public Player start(){
        Player next = one.getType() == Type.WHITE? one : two;
        while(true) {
            Move nextMove = one.getMove(board);
            board.apply(nextMove);
            if (board.isCheck()){

                if (board.isMate()) {
                    return next;
                }
            }  else if (board.isDraw()){
                return null;
            }

            next = next == one? two : one;
        }
    }

    public Board getBoard() {
        return board;
    }
}
