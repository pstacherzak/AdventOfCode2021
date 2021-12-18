package aoc13;

import static java.util.stream.Collectors.toList;

import utils.FileReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Folding {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc13/input.txt");

        long dotsAfterFirstFold = new Folding().countDots(input, true);
        System.out.println("Dots after 1 fold: " + dotsAfterFirstFold);

        long dotsAfterAllFolds = new Folding().countDots(input, false);
        System.out.println("Dots after all folds: " + dotsAfterAllFolds);
    }

    public long countDots(List<String> input, boolean firstFoldOnly) {
        List<List<String>> inputs = splitByNewlines(input);
        List<String> dots = inputs.get(0);
        List<String> folds = inputs.get(1);

        int[][] paper = new int[2000][2000];

        for (String dot : dots) {
            String[] coords = dot.split(",");
            paper[Integer.parseInt(coords[1])][Integer.parseInt(coords[0])] = 1;
        }

        int[][] newPaper = paper;

        for (String fold : folds) {
            String[] foldLine = fold.split(" ")[2].split("=");
            boolean foldUp = "y".equals(foldLine[0]);
            int foldValue = Integer.parseInt(foldLine[1]);

            newPaper = fold(newPaper, foldUp, foldValue);

            if (firstFoldOnly) {
                break;
            }
        }

        if (!firstFoldOnly) {
            for (int y = 0; y < newPaper.length; y++) {
                for (int x = 0; x < newPaper[y].length; x++) {
                    if (newPaper[y][x] == 1) {
                        System.out.print(1);
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.println("");
            }
        }

        return Stream.of(newPaper).flatMapToInt(Arrays::stream).filter(i -> i > 0).count();
    }

    private int[][] fold(int[][] paper, boolean foldUp, int foldValue) {
        int[][] newPaper;

        if (foldUp) {
            newPaper = new int[foldValue][paper[0].length];
            for (int y = 0; y < foldValue; y++) {
                System.arraycopy(paper[y], 0, newPaper[y], 0, paper[0].length);
            }
        } else {
            newPaper = new int[paper.length][foldValue];
            for (int y = 0; y < paper.length; y++) {
                System.arraycopy(paper[y], 0, newPaper[y], 0, foldValue);
            }
        }

        if (foldUp) {
            for (int y = foldValue + 1, index = 1; y < 2 * foldValue + 1; y++, index++) {
                for (int x = 0; x < paper[0].length; x++) {
                    boolean isDotInTarget = newPaper[foldValue - index][x] == 1;
                    newPaper[foldValue - index][x] = isDotInTarget ? 1 : paper[y][x];
                }
            }
        } else {
            for (int y = 0; y < paper.length; y++) {
                for (int x = foldValue + 1, index = 1; x < 2 * foldValue + 1; x++, index++) {
                    boolean isDotInTarget = newPaper[y][foldValue - index] == 1;
                    newPaper[y][foldValue - index] = isDotInTarget ? 1 : paper[y][x];
                }
            }
        }

        return newPaper;
    }

    static List<List<String>> splitByNewlines(List<String> input) {
        int[] indexes = Stream.of(
                IntStream.of(-1),
                IntStream.range(0, input.size())
                    .filter(i -> input.get(i) == null || input.get(i).trim().isEmpty()),
                IntStream.of(input.size()))
            .flatMapToInt(s -> s)
            .toArray();

        return IntStream.range(0, indexes.length-1)
            .mapToObj(i -> input.subList(indexes[i]+1, indexes[i+1]))
            .collect(toList());
    }
}
