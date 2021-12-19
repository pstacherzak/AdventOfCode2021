package aoc14;

import utils.FileReader;
import utils.InputUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Polymerization {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc14/input.txt");

        long score = new Polymerization().polymerizationScore(input, 10);
        System.out.println("Score: " + score);
    }

    public long polymerizationScore(List<String> input, int steps) {
        List<List<String>> inputs = InputUtil.splitByNewlines(input);

        String polymer = inputs.get(0).get(0);
        Map<String, String> rules = parseRules(inputs.get(1));

        for (int i = 0; i < steps; i++) {
            System.out.println("Step: " + (i + 1));
            List<String> chunks = dividePolymerIntoPieces(polymer, rules);

            StringBuilder newPolymer = new StringBuilder();
            for (String chunk : chunks) {
                newPolymer.append(buildNewPolymer(chunk, rules));
            }

            polymer = newPolymer.toString();
        }

        Map<String, Integer> letters = countLetters(polymer);
        Integer max = letters.values().stream().max(Comparator.naturalOrder()).orElse(0);
        Integer min = letters.values().stream().min(Comparator.naturalOrder()).orElse(0);
        return max - min;
    }

    private Map<String, String> parseRules(List<String> input) {
        return input.stream()
            .map(line -> line.split(" -> "))
            .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
    }

    private String buildNewPolymer(String pattern, Map<String, String> rules) {
        StringBuilder patternBuilder = new StringBuilder();

        for (int i = 0; i < pattern.length() - 1; i++) {
            String first = Character.toString(pattern.charAt(i));
            String second = Character.toString(pattern.charAt(i + 1));
            String replacement = rules.get(first + second);

            if (replacement != null) {
                patternBuilder.append(first).append(replacement);
            } else {
                patternBuilder.append(first);
            }
        }

        patternBuilder.append(pattern.charAt(pattern.length() - 1));

        return patternBuilder.toString();
    }

    private Map<String, Integer> countLetters(String base) {
        return base.chars().boxed()
            .map(Character::toString)
            .collect(Collectors.toMap(
                s -> s,
                s -> 1,
                (i1, i2) -> i1 + 1
            ));
    }

    private List<String> dividePolymerIntoPieces(String polymer, Map<String, String> rules) {
        List<StringBuilder> chunks = splitIntoChunks(polymer, 25000);

        for (int i = 0; i < chunks.size(); i++) {
            if (i + 1 == chunks.size()) {
                break;
            }

            StringBuilder current = chunks.get(i);
            StringBuilder next = chunks.get(i + 1);

            String lastCurrent = Character.toString(current.charAt(current.length() - 1));
            String firstNext = Character.toString(next.charAt(0));
            if ( rules.containsKey(lastCurrent + firstNext) ) {
                current.append(rules.get(lastCurrent + firstNext));
            }
        }

        return chunks.stream().filter(sb -> !sb.isEmpty()).map(StringBuilder::toString).collect(Collectors.toList());
    }

    private List<StringBuilder> splitIntoChunks(String s, int size) {
        List<String> ret = new ArrayList<>((s.length() + size - 1) / size);

        for (int start = 0; start < s.length(); start += size) {
            ret.add(s.substring(start, Math.min(s.length(), start + size)));
        }
        return ret.stream().map(StringBuilder::new).collect(Collectors.toList());
    }
}
