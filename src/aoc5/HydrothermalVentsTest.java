package aoc5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class HydrothermalVentsTest {

    @Test
    void countOverlapsPoints() {
        String inputStr = """
            0,9 -> 5,9
            8,0 -> 0,8
            9,4 -> 3,4
            2,2 -> 2,1
            7,0 -> 7,4
            6,4 -> 2,0
            0,9 -> 2,9
            3,4 -> 1,4
            0,0 -> 8,8
            5,5 -> 8,2
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long overlapsPoints = new HydrothermalVents().countOverlapsPoints(input, false);
        assertEquals(5, overlapsPoints);

        long overlapsPointsWithDiagonal = new HydrothermalVents().countOverlapsPoints(input, true);
        assertEquals(12, overlapsPointsWithDiagonal);
    }
}