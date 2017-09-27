/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Specification
 */
public class Board {

    private final int numRows;
    private final int numColumns;
    private final List<Square> squareList = new ArrayList<Square>();
    private final List<Integer> bombList = new ArrayList<Integer>();

    public Board(int x, int y) {
        numRows = y;
        numColumns = x;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                squareList.add(new Square());
            }
        }

        calculateBadNeighbors();
    }

    private void calculateBadNeighbors() {
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                bombList.add(neighborBombs(i, j));
            }
        }
    }

    public void updateBadNeighborCount() {
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                bombList.set(getListIndex(i, j), neighborBombs(i, j));
            }
        }
    }

    private int neighborBombs(int x, int y) {
        int sum = 0;
        List<Integer> l = getNeighbors(x, y);
        for (int i : l) {
            if (squareList.get(i).hasBomb())
                sum++;
        }
        return sum;
    }

    public int badNeighborCount(int x, int y) {
        return bombList.get(getListIndex(x, y));
    }

    public int getRowSize() {
        return numRows;
    }

    public int getColumnSize() {
        return numColumns;
    }

    public List<Square> getSquareList() {
        return new ArrayList<Square>(squareList);
    }

    public void flag(int x, int y) {
        squareList.get(getListIndex(x, y)).flag();
    }

    public int getListIndex(int x, int y) {
        return x + y * numColumns;
    }

    public void deflag(int x, int y) {
        squareList.get(getListIndex(x, y)).deflag();
    }

    public void setBomb(int x, int y, boolean b) {
        squareList.get(getListIndex(x, y)).setBomb(b);
    }

    public synchronized void dig(int x, int y) {
        if (squareList.get(getListIndex(x, y)).getStatus().equals("UNTOUCHED")) {
            List<Integer> l = getNeighbors(x, y);
            boolean tempBombOrNot = squareList.get(getListIndex(x, y)).hasBomb();
            squareList.get(getListIndex(x, y)).dig();
            if (tempBombOrNot) {
                for (int i : l) {
                    bombList.set(i, bombList.get(i) - 1);
                }
            } else {
                for (int i : l) {
                    if (!squareList.get(i).hasBomb() && squareList.get(i).getStatus().equals("UNTOUCHED")) {
                        dig(indexToX(i), indexToY(i));
                    }
                }
            }
        }
    }

    public int indexToX(int i) {
        return i % numColumns;
    }

    public int indexToY(int i) {
        return i / numColumns;
    }

    public List<Integer> getNeighbors(int x, int y) {
        List<Integer> l = new ArrayList<Integer>();
        if (x - 1 >= 0) {
            l.add(getListIndex(x - 1, y));
            if (y - 1 >= 0) {
                l.add(getListIndex(x - 1, y - 1));
            }
            if (y + 1 < numRows) {
                l.add(getListIndex(x - 1, y + 1));
            }
        }
        if (y - 1 >= 0) {
            l.add(getListIndex(x, y - 1));
        }
        if (y + 1 < numRows) {
            l.add(getListIndex(x, y + 1));
        }
        if (x + 1 < numColumns) {
            l.add(getListIndex(x + 1, y));
            if (y - 1 >= 0) {
                l.add(getListIndex(x + 1, y - 1));
            }
            if (y + 1 < numRows) {
                l.add(getListIndex(x + 1, y + 1));
            }
        }
        return l;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (squareList.get(getListIndex(i, j)).getStatus().equals("UNTOUCHED")) {
                    sb.append("- ");
                } else if (squareList.get(getListIndex(i, j)).getStatus().equals("FLAGGED")) {
                    sb.append("F ");
                } else if (squareList.get(getListIndex(i, j)).getStatus().equals("DUG")) {
                    if (bombList.get(getListIndex(i, j)) == 0) {
                        sb.append("  ");
                    } else {
                        sb.append(bombList.get(getListIndex(i, j)) + " ");
                    }
                }
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("%n");
        }
        return sb.toString();
    }
    // TODO: Abstraction function, rep invariant, rep exposure, thread safety

    // TODO: Specify, test, and implement in problem 2

}
