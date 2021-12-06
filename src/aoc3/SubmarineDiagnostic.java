package aoc3;

import utils.FileReader;

import java.util.*;
import java.util.stream.Collectors;

public class SubmarineDiagnostic {

    public static void main(String[] args) {
        List<String> logs = FileReader.read("src/aoc3/input.txt");

        int power = new SubmarineDiagnostic().calculatePower(logs);
        System.out.println("Submarine power rate is: " + power);

        int oxygenRating = new SubmarineDiagnostic().calculateOxygenRating(logs);
        System.out.println("Submarine oxygen rate is: " + oxygenRating);

        int scrubberRating = new SubmarineDiagnostic().calculateScrubberRating(logs);
        System.out.println("Submarine scrubber rate is: " + scrubberRating);

        int lifeSupportRating = oxygenRating * scrubberRating;
        System.out.println("Submarine life support rate is: " + lifeSupportRating);
    }

    public int calculateOxygenRating(List<String> logs) {
        Map<Integer, Integer> positionToOnesCount = countOnes(logs);
        Map<Integer, Integer> positionToZerosCount = countZeros(logs);
        int logLength = logs.get(0).length();

        StringBuilder currentCriteria = new StringBuilder();
        List<String> matches = new ArrayList<>(logs);

        for (int i = 0; i < logLength; i++) {
            int ones = positionToOnesCount.getOrDefault(i, 0);
            int zeros = positionToZerosCount.getOrDefault(i, 0);

            currentCriteria.append(ones >= zeros ? 1 : 0);

            matches = matches.stream()
                .filter(match -> match.startsWith(currentCriteria.toString()))
                .collect(Collectors.toList());

            positionToOnesCount = countOnes(matches);
            positionToZerosCount = countZeros(matches);

            if (matches.size() == 1) {
                break;
            }
        }

        return Integer.parseInt(matches.get(0), 2);
    }

    public int calculateScrubberRating(List<String> logs) {
        Map<Integer, Integer> positionToOnesCount = countOnes(logs);
        Map<Integer, Integer> positionToZerosCount = countZeros(logs);
        int logLength = logs.get(0).length();

        StringBuilder currentCriteria = new StringBuilder();
        List<String> matches = new ArrayList<>(logs);

        for (int i = 0; i < logLength; i++) {
            int ones = positionToOnesCount.getOrDefault(i, 0);
            int zeros = positionToZerosCount.getOrDefault(i, 0);

            currentCriteria.append(ones >= zeros ? 0 : 1);

            matches = matches.stream()
                .filter(match -> match.startsWith(currentCriteria.toString()))
                .collect(Collectors.toList());

            positionToOnesCount = countOnes(matches);
            positionToZerosCount = countZeros(matches);

            if (matches.size() == 1) {
                break;
            }
        }

        return Integer.parseInt(matches.get(0), 2);
    }

    public int calculatePower(List<String> logs) {
        Map<Integer, Integer> positionToOnesCount = countOnes(logs);
        Map<Integer, Integer> positionToZerosCount = countZeros(logs);

        int logLength = logs.get(0).length();

        int gamma = solveGammaRate(positionToOnesCount, positionToZerosCount, logLength);
        int epsilon = solveEpsilonRate(positionToOnesCount, positionToZerosCount, logLength);

        System.out.println("Gamma: " + gamma);
        System.out.println("Epsilon: " + epsilon);

        return gamma * epsilon;
    }

    private int solveGammaRate(
        Map<Integer, Integer> positionToOnesCount,
        Map<Integer, Integer> positionToZerosCount,
        int logLength
    ) {
        StringBuilder binaryResult = new StringBuilder();

        for (int i = 0; i < logLength; i++) {
            int ones = positionToOnesCount.getOrDefault(i, 0);
            int zeros = positionToZerosCount.getOrDefault(i, 0);

            binaryResult.append(ones > zeros ? 1 : 0);
        }

        return Integer.parseInt(binaryResult.toString(), 2);
    }

    private int solveEpsilonRate(
        Map<Integer, Integer> positionToOnesCount,
        Map<Integer, Integer> positionToZerosCount,
        int logLength
    ) {
        StringBuilder binaryResult = new StringBuilder();

        for (int i = 0; i < logLength; i++) {
            int ones = positionToOnesCount.getOrDefault(i, 0);
            int zeros = positionToZerosCount.getOrDefault(i, 0);

            binaryResult.append(ones > zeros ? 0 : 1);
        }

        return Integer.parseInt(binaryResult.toString(), 2);
    }

    private Map<Integer, Integer> countOnes(List<String> logs) {
        return countOccurrences(logs, '1');
    }

    private Map<Integer, Integer> countZeros(List<String> logs) {
        return countOccurrences(logs, '0');
    }

    private Map<Integer, Integer> countOccurrences(List<String> logs, char character) {
        Map<Integer, Integer> positionToCharacterCount = new HashMap<>();

        for (String log : logs) {
            for (int i = 0; i < log.length(); i++) {
                char value = log.charAt(i);
                if (value == character) {
                    positionToCharacterCount.compute(i, (k, v) -> v == null ? 1 : v + 1);
                }
            }
        }

        return positionToCharacterCount;
    }
}
