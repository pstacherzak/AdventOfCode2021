package aoc6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class FishySolverTest {

    @Test
    void simulateLittleFishes() {
        List<Integer> initialState = List.of(3, 4, 3, 1, 2);

        final int after18 = new FishySolver().simulateSmall(initialState, 18);
        assertEquals(26, after18);

        final int after80 = new FishySolver().simulateSmall(initialState, 80);
        assertEquals(5934, after80);
    }

    @Test
    void simulateBigFishes() {
        List<Integer> initialState = List.of(3, 4, 3, 1, 2);

        final long after18 = new FishySolver().simulateBig(initialState, 18);
        assertEquals(26, after18);

        final long after80 = new FishySolver().simulateBig(initialState, 80);
        assertEquals(5934, after80);

        final long after256 = new FishySolver().simulateBig(initialState, 256);
        assertEquals(26984457539L, after256);
    }
}