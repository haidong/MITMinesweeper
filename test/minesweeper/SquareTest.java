package minesweeper;

import static org.junit.Assert.*;

import org.junit.Test;

public class SquareTest {

    @Test
    public void testSquareUntouched() {
        Square s = new Square();
        assertEquals(s.getStatus(), "UNTOUCHED");
    }

    @Test
    public void testSquareDig() {
        Square s = new Square();
        s.dig();
        assertEquals(s.getStatus(), "DUG");
        assertFalse(s.hasBomb());
    }

    @Test
    public void testSquareFlag() {
        Square s = new Square();
        s.flag();
        assertEquals(s.getStatus(), "FLAGGED");
    }

    @Test
    public void testSquareDeflag() {
        Square s = new Square();
        s.flag();
        assertEquals(s.getStatus(), "FLAGGED");
        s.deflag();
        assertEquals(s.getStatus(), "UNTOUCHED");
    }

}
