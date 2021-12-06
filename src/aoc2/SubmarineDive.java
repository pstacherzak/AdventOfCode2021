package aoc2;

import utils.FileReader;

import java.util.List;

public class SubmarineDive {

    public static void main(String[] args) {
        List<String> commands = FileReader.read("src/aoc2/input.txt");

        int position = new SubmarineDive().solvePart1(commands);
        System.out.println("Submarine depth is: " + position);

        position = new SubmarineDive().solvePart2(commands);
        System.out.println("Submarine depth with aim is: " + position);
    }

    public int solvePart1(List<String> commands) {
        int depth = 0, horizontal = 0;

        for (var command : commands) {
            String[] directionToValue = command.split(" ");

            switch (directionToValue[0]) {
                case "forward" -> horizontal += Integer.parseInt(directionToValue[1]);
                case "up" -> depth = Math.max(0, depth - Integer.parseInt(directionToValue[1]) );
                case "down" -> depth += Integer.parseInt(directionToValue[1]);
                default -> throw new IllegalStateException("Invalid direction");
            }
        }

        return depth * horizontal;
    }

    public int solvePart2(List<String> commands) {
        int depth = 0, horizontal = 0, aim = 0;

        for (var command : commands) {
            String[] directionToValue = command.split(" ");
            final String direction = directionToValue[0];
            final int value = Integer.parseInt(directionToValue[1]);

            switch (direction) {
                case "forward" -> {
                    horizontal += value;
                    depth += aim * value;
                }
                case "up" -> aim = Math.max(0, aim - value);
                case "down" -> aim += value;
                default -> throw new IllegalStateException("Invalid direction");
            }
        }

        return depth * horizontal;
    }
}
