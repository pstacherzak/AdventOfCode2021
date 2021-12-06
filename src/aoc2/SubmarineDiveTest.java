package aoc2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class SubmarineDiveTest {

    @Test
    void testSolvingPart1() {
        final String input = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
            """;

        List<String> commands = List.of(input.split("\n"));

        var result = new SubmarineDive().solvePart1(commands);
        assertEquals(150, result);
    }

    @Test
    void testSolvingPart2() {
        final String input = """
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
            """;

        List<String> commands = List.of(input.split("\n"));

        var result = new SubmarineDive().solvePart2(commands);
        assertEquals(900, result);
    }
}