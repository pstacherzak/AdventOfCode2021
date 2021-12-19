package utils;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class InputUtil {

    public static List<List<String>> splitByNewlines(List<String> input) {
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

    public static List<String> toLetters(String s) {
        return s.chars().boxed()
            .map(Character::toString)
            .toList();
    }
}
