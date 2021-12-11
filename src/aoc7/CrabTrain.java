package aoc7;

import utils.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class CrabTrain {

    public static void main(String[] args) {
        List<Integer> crabs = Arrays.stream(FileReader.read("src/aoc7/input.txt").get(0).split(","))
            .map(Integer::parseInt).collect(Collectors.toList());

        final Cargo optimalFuelAtConstantBurnRate = new CrabTrain().calculateOptimalFuelAtConstantBurnRate(crabs);
        System.out.println(
            "Optimal fuel (fixed): " +
            optimalFuelAtConstantBurnRate.fuel() +
            " for position: " +
            optimalFuelAtConstantBurnRate.position()
        );

        final Cargo optimalFuelAtChangingBurnRAte = new CrabTrain().calculateOptimalFuelAtChangingBurnRate(crabs);
        System.out.println(
            "Optimal fuel (changing): " +
            optimalFuelAtChangingBurnRAte.fuel() +
            " for position: " +
            optimalFuelAtChangingBurnRAte.position()
        );
    }

    public Cargo calculateOptimalFuelAtConstantBurnRate(List<Integer> crabs) {
        Map<Integer, Long> positionToFuel = new HashMap<>();

        for (int i = 0; i <= Collections.max(crabs); i++) {
            long fuel = 0L;

            for (Integer crab : crabs) {
                fuel += Math.abs( crab - i );
            }

            positionToFuel.put(i, fuel);
        }

        return positionToFuel.entrySet()
            .stream()
            .min(Map.Entry.comparingByValue())
            .map(e -> new Cargo(e.getValue(), e.getKey()))
            .orElseGet(() -> new Cargo(0L, 0));
    }

    public Cargo calculateOptimalFuelAtChangingBurnRate(List<Integer> crabs) {
        Map<Integer, Long> positionToFuel = new HashMap<>();

        for (int i = 0; i <= Collections.max(crabs); i++) {
            long fuel = 0L;

            for (Integer crab : crabs) {
                final int positionChanges = Math.abs(crab - i);
                for (int j = 1; j <= positionChanges; j++) {
                    fuel += j;
                }
            }

            positionToFuel.put(i, fuel);
        }

        return positionToFuel.entrySet()
            .stream()
            .min(Map.Entry.comparingByValue())
            .map(e -> new Cargo(e.getValue(), e.getKey()))
            .orElseGet(() -> new Cargo(0L, 0));
    }
}

record Cargo(long fuel, int position) {}
