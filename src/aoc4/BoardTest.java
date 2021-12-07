package aoc4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class BoardTest {

    @Test
    public void testMatchRow() {
        final String inputAsString = """                 
                 1 1 1 1 1
                 2 2 2 2 2
                 1 1 1 1 1
                 1 1 1 1 1
                 1 1 1 1 1
            """;

        List<String> input = List.of(inputAsString.split("\n"));
        Board board = new Board(input);

        assertFalse(board.checkBingo());
        board.matchNumber(2);
        assertTrue(board.checkBingo());
        assertEquals(20, board.unmarkedSum());
    }
    @Test
    public void testMatchColumn() {
        final String inputAsString = """                 
                 1 1 1 2 1
                 1 1 1 2 1
                 1 1 1 2 1
                 1 1 1 2 1
                 1 1 1 2 1
            """;

        List<String> input = List.of(inputAsString.split("\n"));
        Board board = new Board(input);

        assertFalse(board.checkBingo());
        board.matchNumber(2);
        assertTrue(board.checkBingo());
        assertEquals(20, board.unmarkedSum());
    }
}