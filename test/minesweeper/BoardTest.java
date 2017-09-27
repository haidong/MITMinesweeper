/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import minesweeper.server.MinesweeperServer;

/**
 * TODO: Description
 */
public class BoardTest {

    // TODO: Testing strategy

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testBoardDimensions() {
        Board b = new Board(5, 6);
        assertEquals(b.getRowSize(), 6);
        assertEquals(b.getColumnSize(), 5);
    }

    @Test
    public void testBoardSize1() {
        Board b = new Board(5, 6);
        List<Square> l = b.getSquareList();
        assertEquals(30, l.size());
    }

    @Test
    public void testBoardFlag1() {
        Board b = new Board(5, 6);
        b.flag(0, 0);
        List<Square> l = b.getSquareList();
        assertEquals("FLAGGED", l.get(0).getStatus());
    }

    @Test
    public void testBoardFlag2() {
        Board b = new Board(5, 6);
        b.flag(1, 0);
        List<Square> l = b.getSquareList();
        assertEquals("FLAGGED", l.get(1).getStatus());
        b.deflag(1, 0);
        l = b.getSquareList();
        assertEquals("UNTOUCHED", l.get(1).getStatus());
    }

    @Test
    public void testBoardDig1() {
        Board b = new Board(5, 6);
        b.flag(2, 3);
        List<Square> l = b.getSquareList();
        assertEquals("FLAGGED", l.get(17).getStatus());
        b.deflag(2, 3);
        l = b.getSquareList();
        assertEquals("UNTOUCHED", l.get(17).getStatus());
        b.dig(2, 3);
        l = b.getSquareList();
        assertEquals("DUG", l.get(17).getStatus());
    }

    @Test
    public void testBoardDig2() {
        Board b = new Board(5, 6);
        b.flag(2, 3);
        List<Square> l = b.getSquareList();
        assertEquals("FLAGGED", l.get(b.getListIndex(2, 3)).getStatus());
        b.deflag(2, 3);
        l = b.getSquareList();
        assertEquals("UNTOUCHED", l.get(b.getListIndex(2, 3)).getStatus());
        b.dig(2, 3);
        l = b.getSquareList();
        assertEquals("DUG", l.get(b.getListIndex(2, 3)).getStatus());
        assertFalse(l.get(b.getListIndex(2, 3)).hasBomb());
    }

    @Test
    public void testBoardDig3() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7_2.txt"));
        assertEquals(1, b.badNeighborCount(4, 1));
        assertEquals(0, b.badNeighborCount(0, 1));
        assertEquals(2, b.badNeighborCount(3, 1));
        assertEquals(1, b.badNeighborCount(4, 0));
        assertEquals(2, b.badNeighborCount(5, 2));

        b.dig(0, 1);
        assertEquals(1, b.badNeighborCount(4, 1));
        assertEquals(0, b.badNeighborCount(0, 1));
        assertEquals(2, b.badNeighborCount(3, 1));
        assertEquals(1, b.badNeighborCount(4, 0));
        assertEquals(2, b.badNeighborCount(5, 2));

        b.dig(4, 1);
        assertEquals(1, b.badNeighborCount(4, 1));
        assertEquals(0, b.badNeighborCount(0, 1));
        assertEquals(1, b.badNeighborCount(3, 1));
        assertEquals(0, b.badNeighborCount(4, 0));
        assertEquals(1, b.badNeighborCount(5, 2));
    }

    @Test
    public void testBoardDig4() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7_3.txt"));
        assertEquals(1, b.badNeighborCount(0, 5));
        assertEquals(1, b.badNeighborCount(1, 5));
        assertEquals(1, b.badNeighborCount(1, 6));
        assertEquals(0, b.badNeighborCount(6, 0));
        assertEquals(0, b.badNeighborCount(3, 3));
        b.dig(0, 6);
        assertEquals(0, b.badNeighborCount(0, 5));
        assertEquals(0, b.badNeighborCount(1, 5));
        assertEquals(0, b.badNeighborCount(1, 6));
        assertEquals(0, b.badNeighborCount(6, 0));
        assertEquals(0, b.badNeighborCount(3, 3));
        assertEquals("DUG", b.getSquareList().get(b.getListIndex(0, 6)).getStatus());
//        assertEquals("DUG", b.getSquareList().get(b.getListIndex(3, 3)).getStatus());
    }

    @Test
    public void testBoardDig5() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7_3.txt"));
        b.dig(0, 0);
        assertEquals("DUG", b.getSquareList().get(b.getListIndex(3, 3)).getStatus());
        assertEquals("DUG", b.getSquareList().get(b.getListIndex(2, 6)).getStatus());
        assertEquals("DUG", b.getSquareList().get(b.getListIndex(1, 6)).getStatus());
        assertEquals("UNTOUCHED", b.getSquareList().get(b.getListIndex(0, 6)).getStatus());
    }

    @Test
    public void testBoardIndexToX1() {
        Board b = new Board(7, 7);
        assertEquals(4, b.indexToX(11));
    }

    @Test
    public void testBoardIndexToY1() {
        Board b = new Board(7, 7);
        assertEquals(1, b.indexToY(11));
    }

    @Test
    public void testBoardIndexToX2() {
        Board b = new Board(5, 6);
        assertEquals(4, b.indexToX(9));
    }

    @Test
    public void testBoardIndexToY2() {
        Board b = new Board(5, 6);
        assertEquals(1, b.indexToY(9));
    }

    @Test
    public void testBoardIndexToX3() {
        Board b = new Board(5, 6);
        assertEquals(1, b.indexToX(11));
    }

    @Test
    public void testBoardIndexToY3() {
        Board b = new Board(5, 6);
        assertEquals(2, b.indexToY(11));
    }

    @Test
    public void testBoardGetNeighbors1() {
        Board b = new Board(5, 6);
        List<Integer> l = b.getNeighbors(0, 0);
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(5);
        expected.add(1);
        expected.add(6);
        assertEquals(expected, l);
    }

    @Test
    public void testBoardGetNeighbors2() {
        Board b = new Board(5, 6);
        List<Integer> l = b.getNeighbors(4, 0);
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(3);
        expected.add(8);
        expected.add(9);
        assertEquals(expected, l);
    }

    @Test
    public void testBoardGetNeighbors3() {
        Board b = new Board(5, 6);
        List<Integer> l = b.getNeighbors(0, 5);
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(20);
        expected.add(26);
        expected.add(21);
        assertEquals(expected, l);
    }

    @Test
    public void testBoardGetNeighbors4() {
        Board b = new Board(5, 6);
        List<Integer> l = b.getNeighbors(4, 5);
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(28);
        expected.add(23);
        expected.add(24);
        assertEquals(expected, l);
    }

    @Test
    public void testBoardGetNeighbors5() {
        Board b = new Board(5, 6);
        List<Integer> l = b.getNeighbors(1, 0);
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        expected.add(5);
        expected.add(6);
        expected.add(2);
        expected.add(7);
        assertEquals(expected, l);
    }

    @Test
    public void testBoardGetNeighbors6() {
        Board b = new Board(5, 6);
        List<Integer> l = b.getNeighbors(3, 1);
        List<Integer> expected = new ArrayList<Integer>();
        expected.add(7);
        expected.add(2);
        expected.add(12);
        expected.add(3);
        expected.add(13);
        expected.add(9);
        expected.add(4);
        expected.add(14);
        assertEquals(expected, l);
    }

    @Test
    public void testBoardGetListIndex1() {
        Board b = new Board(5, 6);
        assertEquals(9, b.getListIndex(4, 1));
    }

    @Test
    public void testBoardGetListIndex2() {
        Board b = new Board(7, 7);
        assertEquals(11, b.getListIndex(4, 1));
    }

    @Test
    public void testBoardGetListIndex3() {
        Board b = new Board(7, 7);
        assertEquals(48, b.getListIndex(6, 6));
    }

    @Test
    public void testBoardBadNeighborCount1() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7.txt"));
        assertEquals(0, b.badNeighborCount(4, 1));
        assertEquals(0, b.badNeighborCount(0, 1));
        assertEquals(1, b.badNeighborCount(3, 1));
        assertEquals(1, b.badNeighborCount(4, 0));
    }

    @Test
    public void testBoardBadNeighborCount2() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7_2.txt"));
        assertEquals(1, b.badNeighborCount(4, 1));
        assertEquals(0, b.badNeighborCount(0, 1));
        assertEquals(2, b.badNeighborCount(3, 1));
        assertEquals(1, b.badNeighborCount(4, 0));
        assertEquals(2, b.badNeighborCount(5, 2));
    }

    @Test
    public void testBoardToString1() throws IOException {
        String expected = "- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n";
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7.txt"));
        assertEquals(expected, b.toString());
    }

    @Test
    public void testBoardToString2() throws IOException {
        String expected = "F - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n";
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7.txt"));
        b.flag(0, 0);
        assertEquals(expected, b.toString());
    }

    @Test
    public void testBoardToString3() throws IOException {
        String expected = "- - - - - - -%n- - - 1 - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n- - - - - - -%n";
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7.txt"));
        b.dig(3, 1);
        assertEquals(expected, b.toString());
    }

}
