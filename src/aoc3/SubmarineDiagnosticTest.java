package aoc3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

class SubmarineDiagnosticTest {

    @Test
    void testCalculatePower() {
        final String input = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
            """;

        List<String> commands = List.of(input.split("\n"));

        int result = new SubmarineDiagnostic().calculatePower(commands);
        assertEquals(198, result);
    }

    @Test
    void testCalculateOxygen() {
        final String input = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
            """;

        List<String> commands = List.of(input.split("\n"));

        int result = new SubmarineDiagnostic().calculateOxygenRating(commands);
        assertEquals(23, result);
    }

    @Test
    void testCalculateScrubber() {
        final String input = """
            00100
            11110
            10110
            10111
            10101
            01111
            00111
            11100
            10000
            11001
            00010
            01010
            """;

        List<String> commands = List.of(input.split("\n"));

        int result = new SubmarineDiagnostic().calculateScrubberRating(commands);
        assertEquals(10, result);
    }
}