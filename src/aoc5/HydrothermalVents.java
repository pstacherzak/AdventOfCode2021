package aoc5;

import utils.FileReader;

import java.util.*;

public class HydrothermalVents {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc5/input.txt");

        long overlaps = new HydrothermalVents().countOverlapsPoints(input, false);
        System.out.println("Overlaps: " + overlaps);

        long overlapsWithDiagonal = new HydrothermalVents().countOverlapsPoints(input, true);
        System.out.println("Overlaps with diagonal: " + overlapsWithDiagonal);
    }

    public long countOverlapsPoints(List<String> input, boolean includeDiagonal) {
        Map<Point, Integer> pointToOccurrences = new HashMap<>();

        for (String line : input) {
            String[] edges = line.split(" -> ");
            Point edge1 = Point.toPoint(edges[0]);
            Point edge2 = Point.toPoint(edges[1]);

            var pointsBetween = getPointsBetween(edge1, edge2, includeDiagonal);
            pointsBetween.forEach(point -> pointToOccurrences.compute(point, (k, v) -> v == null ? 1 : v+1));
        }

        return pointToOccurrences.values().stream().filter(i -> i > 1).count();
    }

    private List<Point> getPointsBetween(Point first, Point second, boolean includeDiagonal) {
        List<Point> points = new ArrayList<>();
        if (onHorizontalAxis(first, second)) {
            if (first.x() > second.x()) {
                //Swap
                var temp = Point.copy(first);
                first = Point.copy(second);
                second = temp;
            }

            for (int i = first.x(); i <= second.x(); i++) {
                points.add(new Point(i, first.y()));
            }
        } else if (onVerticalAxis(first, second)) {
            if (first.y() > second.y()) {
                //Swap
                var temp = Point.copy(first);
                first = Point.copy(second);
                second = temp;
            }

            for (int i = first.y(); i <= second.y(); i++) {
                points.add(new Point(first.x(), i));
            }
        } else if (includeDiagonal) {
            if (first.x() > second.x()) {
                //Swap
                var temp = Point.copy(first);
                first = Point.copy(second);
                second = temp;
            }

            for (int i = first.x(), j = 0; i <= second.x(); i++, j++) {
                if (first.y() > second.y()) {
                    points.add(new Point(i, first.y() - j));
                } else {
                    points.add(new Point(i, first.y() + j));
                }
            }
        }

        return points;
    }

    private boolean onVerticalAxis(Point first, Point second) {
        return first.x() == second.x();
    }

    private boolean onHorizontalAxis(Point first, Point second) {
        return first.y() == second.y();
    }
}

record Point(int x, int y) {
    static Point toPoint(String edge) {
        final String[] coords = edge.split(",");
        return new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
    }

    static Point copy(Point point) {
        return new Point(point.x, point.y);
    }
}
