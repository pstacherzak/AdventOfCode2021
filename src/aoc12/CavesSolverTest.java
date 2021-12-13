package aoc12;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class CavesSolverTest {

    @Test
    void countSmallSystemPaths() {
        String inputStr = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long paths = new CavesSolver().countPaths(input);
        assertEquals(10, paths);
    }

    @Test
    void countMiddleSystemPaths() {
        String inputStr = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long paths = new CavesSolver().countPaths(input);
        assertEquals(19, paths);
    }

    @Test
    void countLargeSystemPaths() {
        String inputStr = """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long paths = new CavesSolver().countPaths(input);
        assertEquals(226, paths);
    }

    @Test
    void countSmallSystemPaths_part2() {
        String inputStr = """
            start-A
            start-b
            A-c
            A-b
            b-d
            A-end
            b-end
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long paths = new CavesSolver().countPathsSmallTwice(input);
        assertEquals(36, paths);
    }

    @Test
    void countMiddleSystemPaths_part2() {
        String inputStr = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long paths = new CavesSolver().countPathsSmallTwice(input);
        assertEquals(103, paths);
    }

    @Test
    void countLargeSystemPaths_part2() {
        String inputStr = """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW
            """;

        List<String> input = List.of(inputStr.split("\n"));

        long paths = new CavesSolver().countPathsSmallTwice(input);
        assertEquals(3509, paths);
    }
}