package aoc14;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class PolymerizationTest {

    @Test
    void polymerizationScore() {
        String inputStr = """
            NNCB
                        
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long score = new Polymerization().polymerizationScore(input, 10);
        assertEquals(1588, score);
    }

    @Test
    void polymerizationScoreForReal() {
        String inputStr = """
            NNCB
                        
            CH -> B
            HH -> N
            CB -> H
            NH -> C
            HB -> C
            HC -> B
            HN -> C
            NN -> C
            BH -> H
            NC -> B
            NB -> B
            BN -> B
            BB -> N
            BC -> B
            CC -> N
            CN -> C
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long score = new Polymerization().polymerizationScore(input, 40);
        assertEquals(2188189693529L, score);
    }
}