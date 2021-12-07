package aoc4;

import static java.util.stream.Collectors.toList;

import utils.FileReader;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BingoSolver {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc4/input.txt");

        int firstWinner = new BingoSolver().solveFirstWinner(input);
        System.out.println("Score first: " + firstWinner);

        int lastWinner = new BingoSolver().solveLastWinner(input);
        System.out.println("Score last: " + lastWinner);
    }

    public int solveFirstWinner(List<String> input) {
        List<Integer> numbers = Arrays.stream(input.get(0).split(","))
            .map(String::trim)
            .map(Integer::parseInt)
            .toList();

        List<Board> boards = splitByNewlines(input).stream()
            .filter(parts -> parts.size() > 1)
            .map(Board::new).toList();

        for (Integer number: numbers) {
            for (Board board: boards) {
                board.matchNumber(number);

                if (board.checkBingo()) {
                    return board.unmarkedSum() * number;
                }
            }
        }

        return -1;
    }

    public int solveLastWinner(List<String> input) {
        List<Integer> numbers = Arrays.stream(input.get(0).split(","))
            .map(String::trim)
            .map(Integer::parseInt)
            .toList();

        List<Board> boards = splitByNewlines(input).stream()
            .filter(parts -> parts.size() > 1)
            .map(Board::new).collect(toList());

        Board lastWinner = null;
        Integer lastNumber = 0;
        for (Integer number: numbers) {
            for (Iterator<Board> it = boards.iterator(); it.hasNext(); ) {
                Board board = it.next();
                board.matchNumber(number);

                if (board.checkBingo()) {
                    lastWinner = board;
                    lastNumber = number;
                    it.remove();
                }
            }
        }

        return lastWinner != null ? lastWinner.unmarkedSum() * lastNumber : -1;
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

