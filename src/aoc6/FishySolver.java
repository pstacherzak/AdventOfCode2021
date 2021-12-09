package aoc6;

import utils.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class FishySolver {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc6/input.txt");

        List<Integer> initialState = Arrays.stream(input.get(0).split(","))
            .map(Integer::parseInt).toList();

        int population = new FishySolver().simulateSmall(initialState, 80);
        System.out.println("Population in 80 days: " + population);

        long bigPopulation = new FishySolver().simulateBig(initialState, 256);
        System.out.println("Population in 256 days: " + bigPopulation);
    }

    public int simulateSmall(List<Integer> initialState, int days) {
        List<Fish> fishes = initialState.stream().map(Fish::create).collect(Collectors.toList());

        for (int i = 0; i < days; i++) {
            final List<Fish> newborns = fishes.stream()
                .map(Fish::grow)
                .filter(Optional::isPresent)
                .map(Optional::get).toList();
            fishes.addAll(newborns);
        }

        return fishes.size();
    }

    public long simulateBig(List<Integer> initialState, int days) {
        Map<Integer, Long> timersToFishes = new HashMap<>();
        initialState.forEach(state -> timersToFishes.compute(state, (k, v) -> v == null ? 1 : v + 1));

        for (int i = 0; i < days; i++) {
            Map<Integer, Long> newTimerToFishes = new HashMap<>();

            for (var timerToFishes : timersToFishes.entrySet()) {
                int timer = timerToFishes.getKey();
                long fishes = timerToFishes.getValue();
                if (timer == 0) {
                    newTimerToFishes.compute(6, (k, v) -> v == null ? fishes : v + fishes);
                    newTimerToFishes.compute(8, (k, v) -> v == null ? fishes : v + fishes);
                } else {
                    newTimerToFishes.compute(timer - 1, (k, v) -> v == null ? fishes : v + fishes);
                }
            }

            timersToFishes.clear();
            timersToFishes.putAll(newTimerToFishes);
        }

        return timersToFishes.values().stream().reduce(0L, Long::sum);
    }
}

class Fish {
    private int timer;
    private static int newbornTimer = 8;
    private static int fullCycleTimer = 6;

    Fish(int timer) {
        this.timer = timer;
    }

    static Fish create(int timer) {
        return new Fish(timer);
    }

    static Fish newborn() {
        return create(newbornTimer);
    }

    Optional<Fish> grow() {
        if (timer == 0) {
            timer = fullCycleTimer;
            return Optional.of(newborn());
        }

        timer--;
        return Optional.empty();
    }
}
