package aoc4;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Board {

    private List<Cell> cells;
    private final int scale;

    public Board(List<String> rows) {
        scale = rows.size();
        cells = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            final String[] row = rows.get(i).trim().split("\\s+");
            for (int j = 0; j < row.length; j++) {
                cells.add(Cell.create(i, j, Integer.parseInt(row[j])));
            }
        }
    }

    public void matchNumber(int value) {
        cells = cells.stream()
            .map(cell -> cell.value() == value ? Cell.mark(cell) : cell)
            .collect(Collectors.toList());
    }

    public boolean checkBingo() {
        final boolean bingoInRow = cells.stream()
            .collect(Collectors.groupingBy(Cell::posX))
            .values()
            .stream()
            .map(row -> row.stream().filter(Cell::isMatched).count())
            .anyMatch(matches -> matches == scale);

        final boolean bingoInColumn = cells.stream()
            .collect(Collectors.groupingBy(Cell::posY))
            .values()
            .stream()
            .map(row -> row.stream().filter(Cell::isMatched).count())
            .anyMatch(matches -> matches == scale);

        return bingoInRow || bingoInColumn;
    }

    public Integer unmarkedSum() {
        return cells.stream()
            .filter(cell -> !cell.isMatched())
            .map(Cell::value)
            .reduce(0, Integer::sum);
    }
}

record Cell(int posX, int posY, int value, boolean match) {
    static Cell mark(Cell cell) {
        return new Cell(cell.posX, cell.posY, cell.value, true);
    }

    static Cell create(int posX, int posY, int value) {
        return new Cell(posX, posY, value, false);
    }

    public boolean isMatched() {
        return match;
    }
}
