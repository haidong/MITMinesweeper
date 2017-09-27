package minesweeper;

public class Square {

    private enum Status {
        FLAGGED, DUG, UNTOUCHED
    };

    private Status status;
    private boolean bomb;

    public Square() {
        status = Status.UNTOUCHED;
        // assign each square to contain a bomb with probability .25
        bomb = (Math.random() >= 0.75);
    }

    public String getStatus() {
        return status.toString();
    }

    public boolean hasBomb() {
        return bomb;
    }

    public void setBomb(boolean b) {
        bomb = b;
    }

    public synchronized void dig() {
        if (status == Status.UNTOUCHED) {
            status = Status.DUG;
            bomb = false;
        }
    }

    public void flag() {
        if (status == Status.UNTOUCHED) {
            status = Status.FLAGGED;
        }
    }

    public void deflag() {
        if (status == Status.FLAGGED) {
            status = Status.UNTOUCHED;
        }
    }

}
