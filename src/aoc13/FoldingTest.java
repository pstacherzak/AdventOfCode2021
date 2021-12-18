package aoc13;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class FoldingTest {

    @Test
    void countDotsAfterFirstFold() {
        String inputStr = """
            6,10
            0,14
            9,10
            0,3
            10,4
            4,11
            6,0
            6,12
            4,1
            0,13
            10,12
            3,4
            3,0
            8,4
            1,10
            2,14
            8,10
            9,0
                        
            fold along y=7
            fold along x=5
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long dotsAfterFirstFold = new Folding().countDots(input, true);
        assertEquals(17, dotsAfterFirstFold);

        long dotsAfterAllFolds = new Folding().countDots(input, false);
        assertEquals(16, dotsAfterAllFolds);
    }
}