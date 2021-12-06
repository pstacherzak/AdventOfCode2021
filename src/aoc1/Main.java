package aoc1;

import utils.FileReader;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Integer> input = FileReader.read("src/aoc1/input.txt")
            .stream().mapToInt(Integer::parseInt).boxed().toList();

        //part 1
        int totalLarger = calculateTotalLarger(input);
        System.out.println(totalLarger);

        //part 2
        List<Integer> sumOfThrees = new ArrayList<>();
        for (int i = 0; i < input.size() - 2; i++) {
            sumOfThrees.add(input.get(i) + input.get(i + 1) + input.get(i + 2));
        }

        int totalLargerInThrees = calculateTotalLarger(sumOfThrees);
        System.out.println(totalLargerInThrees);
    }

    private static int calculateTotalLarger(List<Integer> input) {
        int prev = 0;
        int totalLarger = 0;

        for (int i = 1; i < input.size(); i++) {
            if (input.get(i) > prev) {
                totalLarger++;
            }

            prev = input.get(i);
        }
        return totalLarger;
    }
}
