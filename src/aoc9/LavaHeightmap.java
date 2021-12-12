package aoc9;

import utils.FileReader;

import java.util.*;

public class LavaHeightmap {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc9/input.txt");

        int lowPointsRiskLevel = new LavaHeightmap().calculateLowPointsRisk(input);
        System.out.println("Low points risk level: " + lowPointsRiskLevel);

        int topThreeBasins = new LavaHeightmap().calculateTopSizeBasins(input);
        System.out.println("Basins: " + topThreeBasins);
    }

    public int calculateLowPointsRisk(List<String> input) {
        Heightmap heightmap = toHeightmap(input);
        List<HeightmapPoint> lowPoints = getHeightmapLowPoints(heightmap);

        return lowPoints.stream()
            .map(HeightmapPoint::value)
            .reduce(0, (i1, i2) -> i1 + i2 + 1);
    }

    public int calculateTopSizeBasins(List<String> input) {
        Heightmap heightmap = toHeightmap(input);
        List<HeightmapPoint> lowPoints = getHeightmapLowPoints(heightmap);

        List<Integer> basins = new ArrayList<>();

        for (HeightmapPoint lowPoint : lowPoints) {
            basins.add(calculateBasinAround(heightmap, lowPoint));
        }

        return basins.stream()
            .sorted(Comparator.reverseOrder())
            .limit(3).reduce(1, (i1, i2) -> i1 * i2);
    }

    private Integer calculateBasinAround(Heightmap heightmap, HeightmapPoint lowPoint) {
        Set<Point> basin = new HashSet<>();
        fillBasin(basin, new Point(lowPoint.y(), lowPoint.x()), heightmap);
        return basin.size();
    }

    private void fillBasin(Set<Point> basin, Point point, Heightmap heightmap) {
        basin.add(point);

        List<Point> neightbours = heightmap.getNeighbours(point)
            .stream()
            .filter(n -> n.value() < 9)
            .map(n -> new Point(n.y(), n.x()))
            .filter(p -> !basin.contains(p)).toList();

        for (Point newPoint : neightbours) {
            fillBasin(basin, newPoint, heightmap);
        }
    }

    private List<HeightmapPoint> getHeightmapLowPoints(Heightmap heightmap) {
        List<HeightmapPoint> lowPoints = new ArrayList<>();

        for (int y = 0; y < heightmap.values().length; y++) {
            List<Integer> heights = Arrays.stream(heightmap.values()[y]).boxed().toList();

            for (int x = 0; x < heights.size(); x++) {
                int height = heights.get(x);
                boolean isLocalLowPoint = heightmap.isLocalLowPoint(y, x);

                if (isLocalLowPoint) {
                    lowPoints.add(new HeightmapPoint(y, x, height));
                }
            }
        }
        return lowPoints;
    }

    private Heightmap toHeightmap(List<String> input) {
        int[][] heightmap = new int[input.size()][input.get(0).length()];

        for (int y = 0; y < input.size(); y++) {
            String row = input.get(y);
            List<Integer> heights = row.chars().map(c -> Character.digit(c, 10)).boxed().toList();

            for (int x = 0; x < heights.size(); x++) {
                int height = heights.get(x);
                heightmap[y][x] = height;
            }
        }

        return new Heightmap(heightmap);
    }
}

record Heightmap(int[][] values) {

    boolean isLocalLowPoint(int y, int x) {
        int currentValue = values[y][x];

        int leftValue = x == 0 ? Integer.MAX_VALUE : values[y][x - 1];
        int rightValue = x == values[y].length - 1 ? Integer.MAX_VALUE : values[y][x + 1];
        int upValue = y == 0 ? Integer.MAX_VALUE : values[y - 1][x];
        int downValue = y == values.length - 1 ? Integer.MAX_VALUE : values[y + 1][x];

        return currentValue < leftValue && currentValue < rightValue && currentValue < upValue
               && currentValue < downValue;
    }

    List<HeightmapPoint> getNeighbours(Point point) {
        List<HeightmapPoint> neighbours = new ArrayList<>();

        Optional<HeightmapPoint> left = point.x() == 0 ? Optional.empty()
                                                       : Optional.of(new HeightmapPoint(
                                                           point.y(),
                                                           point.x() - 1,
                                                           values[point.y()][point.x() - 1]
                                                       ));

        Optional<HeightmapPoint> right = point.x() == values[point.y()].length - 1 ? Optional.empty()
                                                                                   : Optional.of(new HeightmapPoint(
                                                                                       point.y(),
                                                                                       point.x() + 1,
                                                                                       values[point.y()][point.x() + 1]
                                                                                   ));

        Optional<HeightmapPoint> up = point.y() == 0 ? Optional.empty()
                                                     : Optional.of(new HeightmapPoint(
                                                         point.y() - 1,
                                                         point.x(),
                                                         values[point.y() - 1][point.x()]
                                                     ));

        Optional<HeightmapPoint> down = point.y() == values.length - 1 ? Optional.empty()
                                                                       : Optional.of(new HeightmapPoint(
                                                                           point.y() + 1,
                                                                           point.x(),
                                                                           values[point.y() + 1][point.x()]
                                                                       ));

        left.ifPresent(neighbours::add);
        right.ifPresent(neighbours::add);
        up.ifPresent(neighbours::add);
        down.ifPresent(neighbours::add);

        return neighbours;
    }
}

record HeightmapPoint(int y, int x, int value) {}

record Point(int y, int x) {}
