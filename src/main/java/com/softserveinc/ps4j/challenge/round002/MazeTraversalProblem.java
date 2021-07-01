package com.softserveinc.ps4j.challenge.round002;

import java.util.*;

/**
 * Given a two-dimensional {@link Maze}, return a path
 * from the {@link Maze#getEntry()} to the {@link Maze#getExit()},
 * or {@link Optional#empty()} if it doesn't exist.
 *
 * <p>The maze consists of {@link Cell} objects representing 2d coordinates.
 * <p>To check if the coordinate is traversable, use the {@link Maze#canTraverse(Cell)} method.
 *
 * <p>In this problem, a maze can have AT MOST ONE valid path,
 * but it can also have multiple dead-ends.
 */
class MazeTraversalProblem {

    Optional<List<Cell>> solve(Maze maze) {
        Step current = new Step(maze.getEntry(), 0);
        List<Cell> way = new ArrayList<>();
        Deque<Step> edges = new ArrayDeque<>();
        Set<Cell> tried = new HashSet<>();
        int previousStep = current.stepNumber();
        edges.add(current);
        tried.add(current.cell());

        while (!edges.isEmpty() && !current.equals(maze.getExit())) {
            current = edges.pop();

            boolean isDeadEnd = addIfTraversable(current.cell().up(), maze, edges, tried, current) &
                    addIfTraversable(current.cell().down(), maze, edges, tried, current) &
                    addIfTraversable(current.cell().right(), maze, edges, tried, current) &
                    addIfTraversable(current.cell().left(), maze, edges, tried, current);

            if (isDeadEnd && !current.equals(maze.getExit())) {
                int deleteSize = current.stepNumber() - previousStep - 1;
                if (deleteSize > 0) {
                    way = way.subList(0, way.size() - deleteSize);
                }
            } else {
                way.add(current.cell());
                previousStep = current.stepNumber();
            }
        }
        way.add(maze.getExit());

        return current.cell().equals(maze.getExit()) ? Optional.of(way) : Optional.empty();
    }

    private boolean addIfTraversable(Cell cell, Maze maze, Deque<Step> edges, Set<Cell> tried, Step current) {
        if (maze.canTraverse(cell) && tried.add(cell)) {
            edges.add(new Step(cell, current.stepNumber() + 1));
            return false;
        }
        return true;
    }
}

record Cell(int x, int y) {

    Cell up() {
        return new Cell(x - 1, y);
    }

    Cell down() {
        return new Cell(x + 1, y);
    }

    Cell left() {
        return new Cell(x, y - 1);
    }

    Cell right() {
        return new Cell(x, y + 1);
    }
}

record Step(Cell cell, int stepNumber) {
}

final class Maze {

    private final Cell entry;

    private final Cell exit;

    private final Set<Cell> traversableCells;

    Maze(Cell entry, Cell exit, Collection<Cell> traversableCells) {
        this.entry = Objects.requireNonNull(entry);
        this.exit = Objects.requireNonNull(exit);
        this.traversableCells = new HashSet<>(traversableCells.size() + 2);
        this.traversableCells.add(entry);
        this.traversableCells.add(exit);
        this.traversableCells.addAll(traversableCells);
    }

    boolean canTraverse(Cell cell) {
        return traversableCells.contains(cell);
    }

    Cell getEntry() {
        return entry;
    }

    Cell getExit() {
        return exit;
    }

}

