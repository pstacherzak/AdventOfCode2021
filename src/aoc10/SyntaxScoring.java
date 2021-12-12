package aoc10;

import utils.FileReader;

import java.util.*;
import java.util.stream.Stream;

public class SyntaxScoring {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc10/input.txt");

        final int scoreCorrupted = new SyntaxScoring().scoreCorrupted(input);
        System.out.println("Score corrupted: " + scoreCorrupted);

        final long scoreMiddleCompletion = new SyntaxScoring().scoreMiddleCompletion(input);
        System.out.println("Score completion: " + scoreMiddleCompletion);
    }

    public int scoreCorrupted(List<String> input) {
        List<String> corrupted = input.stream()
            .map(this::sanitize)
            .filter(s -> !s.isEmpty())
            .filter(s -> !incomplete(s)).toList();

        corrupted.forEach(System.out::println);

        return corrupted.stream()
            .map(this::findCorruption)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .map(c -> switch (c) {
                case ')' -> 3;
                case ']' -> 57;
                case '}' -> 1197;
                case '>' -> 25137;
                default -> throw new IllegalStateException("Unexpected value: " + c);
            })
            .reduce(0, Integer::sum);
    }

    public long scoreMiddleCompletion(List<String> input) {
        List<String> incompleted = input.stream()
            .map(this::sanitize)
            .filter(s -> !s.isEmpty())
            .filter(this::incomplete).toList();

        incompleted.forEach(System.out::println);

        List<String> completions = incompleted.stream()
            .map(s -> new StringBuilder(s).reverse().toString())
            .map(s -> s.replaceAll("\\[", "]")
                .replaceAll("\\(", ")")
                .replaceAll("<", ">")
                .replaceAll("\\{", "}")).toList();

        completions.forEach(System.out::println);

        List<Long> scores = completions.stream()
            .map(this::calculateCompletionScore)
            .sorted(Comparator.naturalOrder()).toList();

        scores.forEach(System.out::println);

        return scores.get(scores.size() / 2);
    }

    private boolean incomplete(String line) {
        return !line.contains(")")
               && !line.contains(">")
               && !line.contains("}")
               && !line.contains("]");
    }

    public String sanitize(String line) {
        int beforeSanitize = 0;
        int afterSanitize = -1;
        while (beforeSanitize != afterSanitize) {
            beforeSanitize = line.length();
            line = line.replaceAll("\\[]", "")
                .replaceAll("\\(\\)", "")
                .replaceAll("\\{}", "")
                .replaceAll("<>", "");
            afterSanitize = line.length();
        }
        return line;
    }

    private Optional<Character> findCorruption(String corrupted) {
        int firstClosingTagIndex = Stream.of(
            corrupted.indexOf(">"),
            corrupted.indexOf("}"),
            corrupted.indexOf(")"),
            corrupted.indexOf("]")
        ).filter(i -> i > 0).min(Comparator.naturalOrder()).orElse(-1);

        if (firstClosingTagIndex > 0) {
            return Optional.of(corrupted.charAt(firstClosingTagIndex));
        }
        return Optional.empty();
    }

    private long calculateCompletionScore(String s) {
        return s.chars().mapToObj(c -> switch (c) {
            case ')' -> 1L;
            case ']' -> 2L;
            case '}' -> 3L;
            case '>' -> 4L;
            default -> throw new IllegalStateException("Unexpected value: " + c);
        }).reduce(0L, (acc, i) -> acc * 5L + i);
    }
}
