package aoc11;

import utils.FileReader;

import java.util.*;

public class DumboOctopuses {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc11/input.txt");

        int totalFlashes = new DumboOctopuses().totalFlashes(input);
        System.out.println("Total flashes: " + totalFlashes);

        int optimalStep = new DumboOctopuses().stepForAllFlashes(input);
        System.out.println("Optimal step: " + optimalStep);
    }

    public int totalFlashes(List<String> input) {
        int flashes = 0;
        int steps = 100;
        OctopusesMap octopuses = toOctopuses(input);

        for (int i = 0; i < steps; i++) {
            flashes += octopuses.charge();
            octopuses.reset();
        }

        return flashes;
    }

    public int stepForAllFlashes(List<String> input) {
        int flashes = 0;
        int step = 0;
        OctopusesMap octopuses = toOctopuses(input);

        while (flashes != 100) {
            step++;
            flashes = octopuses.charge();
            octopuses.reset();
        }

        return step;
    }

    private OctopusesMap toOctopuses(List<String> input) {
        int[][] heightmap = new int[input.size()][input.get(0).length()];

        for (int y = 0; y < input.size(); y++) {
            String row = input.get(y);
            List<Integer> heights = row.chars().map(c -> Character.digit(c, 10)).boxed().toList();

            for (int x = 0; x < heights.size(); x++) {
                int height = heights.get(x);
                heightmap[y][x] = height;
            }
        }

        return new OctopusesMap(heightmap);
    }
}

class OctopusesMap {

    private final Octopus[][] octopuses = new Octopus[10][10];

    public OctopusesMap(int[][] values) {

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                this.octopuses[y][x] = new Octopus(y, x, values[y][x]);
            }
        }
    }

    Set<Octopus> getNeighbours(Octopus o) {
        Set<Octopus> neighbours = new HashSet<>();

        getLeft(o).ifPresent(left -> {
            getUp(left).ifPresent(neighbours::add);
            getDown(left).ifPresent(neighbours::add);
            neighbours.add(left);
        });
        getRight(o).ifPresent(right -> {
            getUp(right).ifPresent(neighbours::add);
            getDown(right).ifPresent(neighbours::add);
            neighbours.add(right);
        });
        getUp(o).ifPresent(up -> {
            getLeft(up).ifPresent(neighbours::add);
            getRight(up).ifPresent(neighbours::add);
            neighbours.add(up);
        });
        getDown(o).ifPresent(down -> {
            getLeft(down).ifPresent(neighbours::add);
            getRight(down).ifPresent(neighbours::add);
            neighbours.add(down);
        });

        return neighbours;
    }

    public int charge() {
        int flashed = 0;

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Octopus octopus = octopuses[y][x];
                chargeOctopus(octopus);
            }
        }

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Octopus octopus = octopuses[y][x];

                if (octopus.flashed) {
                    flashed++;
                }
            }
        }

        return flashed;
    }

    public void chargeOctopus(Octopus o) {
        if (o.charge()) {
            final Set<Octopus> neighbours = getNeighbours(o);
            neighbours.forEach(this::chargeOctopus);
        }
    }

    public void reset() {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                Octopus octopus = octopuses[y][x];
                octopus.reset();
            }
        }
    }

    Optional<Octopus> getLeft(Octopus o) {
        return o.x == 0 ? Optional.empty() : Optional.of(octopuses[o.y][o.x - 1]);
    }

    Optional<Octopus> getRight(Octopus o) {
        return o.x == octopuses[o.y].length - 1 ? Optional.empty()
                                                : Optional.of(octopuses[o.y][o.x + 1]);
    }

    Optional<Octopus> getUp(Octopus o) {
        return o.y == 0 ? Optional.empty()
                        : Optional.of(octopuses[o.y - 1][o.x]);
    }

    Optional<Octopus> getDown(Octopus o) {
        return o.y == octopuses.length - 1 ? Optional.empty()
                                        : Optional.of(octopuses[o.y + 1][o.x]);
    }
}

class Octopus {

    int y;
    int x;
    int value;
    boolean flashed;

    public Octopus(int y, int x, int value) {
        this.y = y;
        this.x = x;
        this.value = value;
    }

    public boolean charge() {
        if (!flashed) {
            value++;
        }

        if (value == 10) {
            flashed = true;
            value = 0;
            return true;
        }

        return false;
    }

    public void reset() {
        flashed = false;
    }
}
