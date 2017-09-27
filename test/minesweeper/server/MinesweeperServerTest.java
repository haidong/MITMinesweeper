/* Copyright (c) 2007-2017 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package minesweeper.server;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import minesweeper.Board;

/**
 * TODO
 */
public class MinesweeperServerTest {
    
    @Test
    public void testGetBoardFromFile1() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7.txt"));
        assertEquals(b.getRowSize(), 7);
        assertEquals(b.getColumnSize(), 7);
    }

    @Test
    public void testGetBoardFromFile2() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7.txt"));
        assertTrue(b.getSquareList().get(b.getListIndex(4, 1)).hasBomb());
    }

    @Test
    public void testGetBoardFromFile3() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7.txt"));
        assertFalse(b.getSquareList().get(b.getListIndex(3, 1)).hasBomb());
    }

    @Test(expected=RuntimeException.class)
    public void testGetBoardFromFileInvalidFile1() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7WrongX.txt"));
        assertFalse(b.getSquareList().get(b.getListIndex(3, 1)).hasBomb());
    }

    @Test(expected=RuntimeException.class)
    public void testGetBoardFromFileInvalidFile2() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7WrongColumn.txt"));
        assertFalse(b.getSquareList().get(b.getListIndex(3, 1)).hasBomb());
    }

    @Test(expected=RuntimeException.class)
    public void testGetBoardFromFileInvalidFile3() throws IOException {
        Board b = MinesweeperServer.getBoardFromFile(new File("test/minesweeper/server/board7x7WrongRow.txt"));
        assertFalse(b.getSquareList().get(b.getListIndex(3, 1)).hasBomb());
    }
    
}
