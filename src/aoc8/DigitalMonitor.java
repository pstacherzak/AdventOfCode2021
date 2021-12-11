package aoc8;

import utils.FileReader;

import java.util.*;
import java.util.stream.Stream;

public class DigitalMonitor {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc8/input.txt");

        int totalEasyNumbers = new DigitalMonitor().totalEasyNumbers(input);
        System.out.println("Easy numbers: " + totalEasyNumbers);

        int sumOfDigits = new DigitalMonitor().sumOfDigits(input);
        System.out.println("Sum of numbers: " + sumOfDigits);
    }

    public int totalEasyNumbers(List<String> input) {
        int total = 0;

        for (String line : input ) {
            final String[] parts = line.trim().split(" \\| ");
            final String lineWithDigits = parts[1].trim();

            final String[] digits = lineWithDigits.split(" ");

            for (String digit : digits) {
                int length = digit.trim().length();
                if ( length == 2 || length == 3 || length == 4 || length == 7) {
                    total++;
                }
            }
        }

        return total;
    }

    public int sumOfDigits(List<String> input) {
        int total = 0;

        for (String line : input ) {
            final String[] parts = line.trim().split(" \\| ");
            final String[] pattern = parts[0].trim().split(" ");
            final String[] toDecode = parts[1].trim().split(" ");
            total += solveLine(
                new ArrayList<>(Arrays.asList(pattern)),
                new ArrayList<>(Arrays.asList(toDecode))
            );
        }

        return total;
    }

    private int solveLine(List<String> patterns, List<String> toDecode) {
        Map<String, Integer> patternToDigit = new HashMap<>();

        String onePattern = patterns.stream().filter(p -> p.length() == 2).findFirst().orElse("");
        patterns.remove(onePattern);
        patternToDigit.put(sorted(onePattern), 1);

        String fourPattern = patterns.stream().filter(p -> p.length() == 4).findFirst().orElse("");
        patterns.remove(fourPattern);
        patternToDigit.put(sorted(fourPattern), 4);

        String sevenPattern = patterns.stream().filter(p -> p.length() == 3).findFirst().orElse("");
        patterns.remove(sevenPattern);
        patternToDigit.put(sorted(sevenPattern), 7);

        String eightPattern = patterns.stream().filter(p -> p.length() == 7).findFirst().orElse("");
        patterns.remove(eightPattern);
        patternToDigit.put(sorted(eightPattern), 8);

        String ninePattern = patterns.stream().filter(p -> p.length() == 6)
            .filter(p -> containsLetters(p, fourPattern)).findFirst().orElse("");
        patterns.remove(ninePattern);
        patternToDigit.put(sorted(ninePattern), 9);

        String zeroPattern = patterns.stream().filter(p -> p.length() == 6)
            .filter(p -> containsLetters(p, onePattern)).findFirst().orElse("");
        patterns.remove(zeroPattern);
        patternToDigit.put(sorted(zeroPattern), 0);

        String sixPattern = patterns.stream().filter(p -> p.length() == 6)
            .findFirst().orElse("");
        patterns.remove(sixPattern);
        patternToDigit.put(sorted(sixPattern), 6);

        String threePattern = patterns.stream().filter(p -> p.length() == 5)
            .filter(p -> containsLetters(p, onePattern))
            .findFirst().orElse("");
        patterns.remove(threePattern);
        patternToDigit.put(sorted(threePattern), 3);

        String twoPattern = patterns.stream().filter(p -> p.length() == 5)
            .filter(p -> countMissingLetters(p, fourPattern) == 2)
            .findFirst().orElse("");
        patterns.remove(twoPattern);
        patternToDigit.put(sorted(twoPattern), 2);

        patternToDigit.put(sorted(patterns.get(0)), 5);

        StringBuilder decoded = new StringBuilder();
        for (String digit : toDecode) {
            decoded.append(patternToDigit.get(sorted(digit)));
        }

        return Integer.parseInt(decoded.toString());
    }

    private String sorted(String unordered) {
        final char[] chars = unordered.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private boolean containsLetters(String base, String letters) {
        return letters.chars().mapToObj(c -> (char) c)
            .allMatch(c -> base.contains(String.valueOf(c)));
    }

    private long countMissingLetters(String base, String letters) {
        return letters.chars().mapToObj(c -> (char) c)
            .filter(c -> base.contains(String.valueOf(c)))
            .count();
    }
}
