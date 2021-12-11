package aoc7;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class CrabTrainTest {

    @Test
    void calculateOptimalFuelConstantBurnRate() {
        List<Integer> crabs = List.of(16, 1, 2, 0, 4, 2, 7, 1, 2, 14);

        final Cargo cargo = new CrabTrain().calculateOptimalFuelAtConstantBurnRate(crabs);
        assertEquals(2, cargo.position());
        assertEquals(37, cargo.fuel());
    }

    @Test
    void calculateOptimalFuelChangingBurnRate() {
        List<Integer> crabs = List.of(16, 1, 2, 0, 4, 2, 7, 1, 2, 14);

        final Cargo cargo = new CrabTrain().calculateOptimalFuelAtChangingBurnRate(crabs);
        assertEquals(5, cargo.position());
        assertEquals(168, cargo.fuel());
    }
}