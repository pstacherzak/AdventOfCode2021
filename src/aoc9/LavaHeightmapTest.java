package aoc9;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class LavaHeightmapTest {

    @Test
    public void calculateLowPointsRiskLevel() {
        String inputStr = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
            """;

        List<String> input = List.of(inputStr.split("\n"));

        int riskLevel = new LavaHeightmap().calculateLowPointsRisk(input);
        assertEquals(15, riskLevel);
    }

    @Test
    public void calculateBasins() {
        String inputStr = """
            2199943210
            3987894921
            9856789892
            8767896789
            9899965678
            """;

        List<String> input = List.of(inputStr.split("\n"));

        int basins = new LavaHeightmap().calculateTopSizeBasins(input);
        assertEquals(1134, basins);
    }
}