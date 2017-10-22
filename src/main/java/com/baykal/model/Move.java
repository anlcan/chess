package com.baykal.model;

import java.util.Optional;

/**
 * User: anlcan Date: 17/10/2017 Time: 21:55
 */
public class Move implements Comparable<Integer>{
    private final Piece origin;
    private final Position current;
    private final Position next;
    private final Optional<Piece> target;
    private boolean check;
    private Kind promote = null;

    public Move(Piece origin , Position next, Optional<Piece> target) {
        this.next = next;
        this.origin = origin;
        this.current = origin.getCurrent();
        this.target = target;

        assert !target.isPresent() || target.get().getType() != origin.getType();
    }

    public Position getCurrent() {
        return current;
    }

    public Position getNext() {
        return next;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCapture() {
        return target.isPresent();
    }

    public Piece getTarget() {
        assert target.isPresent();
        return target.get();
    }

    public void setPromote(Kind promote) {
        this.promote = promote;
    }

    public Piece getOrigin() {
        return origin;
    }

    @Override
    public String toString() {
        String sep = isCapture()? "x" : "";
        String check = isCheck()? "+" : "";
        String pro = promote != null? "=" + promote.getSign() : "";
        return origin.getKind().getSign()
                + sep
                + getCurrent().x + getCurrent().y
                + next.toString()
                + check
                + pro
                + " ";
    }

    public static Move read(String pgn) {

        Optional<Kind> kind = Kind.fromPgnSign(String.valueOf(pgn.charAt(0)));
        assert kind.isPresent();
        Kind k = kind.get();

        return new Move(null, null, null);

    }

    @Override
    public int compareTo(Integer value) {
        return Integer.compare(this.origin.getKind().value, value);
    }

    public static int compareCaptureValue(Move m1, Move m2){
        return m1.compareTo(m2.getTarget().getKind().value);
    }
}
