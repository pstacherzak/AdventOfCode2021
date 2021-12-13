package aoc12;

import utils.FileReader;

import java.util.*;

public class CavesSolver {

    public static void main(String[] args) {
        List<String> input = FileReader.read("src/aoc12/input.txt");

        long paths = new CavesSolver().countPaths(input);
        System.out.println("Paths: " + paths);

        long pathsSmallTwice = new CavesSolver().countPathsSmallTwice(input);
        System.out.println("Paths small twice: " + pathsSmallTwice);
    }

    public long countPaths(List<String> input) {
        Map<String, Cave> system = buildCavesSystem(input);

        Set<String> routes = new HashSet<>();
        Cave start = system.get("start");
        getRoutes(routes, "", start);

        System.out.println(routes);

        return routes.stream().filter(route -> route.endsWith("end")).count();
    }

    public long countPathsSmallTwice(List<String> input) {
        Map<String, Cave> system = buildCavesSystem(input);

        Set<String> routes = new HashSet<>();
        Cave start = system.get("start");
        getRoutesSmallTwice(system, routes, "", start);

        System.out.println(routes);

        return routes.stream().filter(route -> route.endsWith("end")).count();
    }

    private Map<String, Cave> buildCavesSystem(List<String> input) {
        Map<String, Cave> system = new HashMap<>();

        for (String path : input) {
            String[] edges = path.split("-");
            Cave first = system.containsKey(edges[0]) ? system.get(edges[0]) : new Cave(edges[0]);
            Cave second = system.containsKey(edges[1]) ? system.get(edges[1]) : new Cave(edges[1]);

            first.addNeightbour(second);
            second.addNeightbour(first);

            system.putIfAbsent(edges[0], first);
            system.putIfAbsent(edges[1], second);
        }
        return system;
    }

    void getRoutes(Set<String> routes, String path, Cave current) {
        routes.remove(path);

        String newPath = path.isEmpty() ? current.id : path + "-" + current.id;
        routes.add(newPath);

        if (current.isEnd()) {
            return;
        }

        for (Cave cave : current.neightbours) {
            if (!cave.isStart() && (cave.isBig() || (cave.isSmall() && !newPath.contains(cave.id)))) {
                getRoutes(routes, newPath, cave);
            }
        }
    }

    void getRoutesSmallTwice(Map<String, Cave> system, Set<String> routes, String path, Cave current) {
        routes.remove(path);

        String newPath = path.isEmpty() ? current.id : path + "-" + current.id;
        routes.add(newPath);

        if (current.isEnd()) {
            return;
        }

        for (Cave cave : current.neightbours) {
            List<String> paths = Arrays.stream(newPath.split("-")).toList();
            boolean isAnySmallTwiceAlready = system.keySet().stream()
                .filter(id -> Character.isLowerCase(id.charAt(0)))
                .anyMatch(id -> paths.stream().filter(p -> p.equals(id)).toList().size() > 1);

            if (!cave.isStart() &&
                ( cave.isBig() || (cave.isSmall() && (!newPath.contains(cave.id) || !isAnySmallTwiceAlready )) )
            ) {
                getRoutesSmallTwice(system, routes, newPath, cave);
            }
        }
    }
}

class Cave {
    final String id;
    final Set<Cave> neightbours;

    public Cave(String id) {
        this.id = id;
        this.neightbours = new HashSet<>();
    }

    public void addNeightbour(Cave cave) {
        if (!cave.isStart()) {
            neightbours.add(cave);
        }
    }

    public boolean isEnd() {
        return "end".equalsIgnoreCase(id);
    }

    public boolean isStart() {
        return "start".equalsIgnoreCase(id);
    }

    public boolean isSmall() {
        return Character.isLowerCase(id.charAt(0));
    }

    public boolean isBig() {
        return Character.isUpperCase(id.charAt(0));
    }
}
