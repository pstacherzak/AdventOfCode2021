package aoc11;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.List;

class DumboOctopusesTest {

    @Test
    public void calculateTotalFlashes() {
        String inputStr = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
            """;

        List<String> input = List.of(inputStr.split("\n"));

        int totalFlashes = new DumboOctopuses().totalFlashes(input);
        assertEquals(1656, totalFlashes);
    }

    @Test
    public void testOptimalStep() {
        String inputStr = """
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
            """;

        List<String> input = List.of(inputStr.split("\n"));

        int step = new DumboOctopuses().stepForAllFlashes(input);
        assertEquals(195, step);
    }
}