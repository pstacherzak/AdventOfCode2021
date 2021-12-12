package aoc10;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

class SyntaxScoringTest {

    @Test
    void scoreCorrupted() {
        String inputStr = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
            """;

        List<String> input = List.of(inputStr.split("\n"));
        int score = new SyntaxScoring().scoreCorrupted(input);
        assertEquals(26397, score);
    }

    @Test
    void scoreCompletion() {
        String inputStr = """
            [({(<(())[]>[[{[]{<()<>>
            [(()[<>])]({[<{<<[]>>(
            {([(<{}[<>[]}>{[]{[(<()>
            (((({<>}<{<{<>}{[]{[]{}
            [[<[([]))<([[{}[[()]]]
            [{[{({}]{}}([{[{{{}}([]
            {<[[]]>}<{[{[{[]{()[[[]
            [<(<(<(<{}))><([]([]()
            <{([([[(<>()){}]>(<<{{
            <{([{{}}[<[[[<>{}]]]>[]]
            """;

        List<String> input = List.of(inputStr.split("\n"));
        long score = new SyntaxScoring().scoreMiddleCompletion(input);
        assertEquals(288957L, score);
    }

    @Test
    void sanitize() {
        var syntax = new SyntaxScoring();
        assertEquals("", syntax.sanitize("<()<>>"));
        assertEquals(">", syntax.sanitize("<()<>>>"));
        assertEquals("", syntax.sanitize("<<()<>>>"));
        assertEquals("", syntax.sanitize("[][[][]][]<<()<>>>"));
        assertEquals("", syntax.sanitize("()(())[][[][]][]<<()<>>>"));
        assertEquals("", syntax.sanitize("{{}}{}()(())[]{}[[][]][]<<()<>>>"));
        assertEquals("((((<{<{{", syntax.sanitize("(((({<>}<{<{<>}{[]{[]{}"));
    }
}