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
        Cell current = maze.getEntry();
        List<Cell> way = new ArrayList<>();
        Deque<Cell> edges = new ArrayDeque<>();
        Set<Cell> tried = new HashSet<>();
        edges.add(current);

        while (!edges.isEmpty() && !current.equals(maze.getExit())) {
            boolean isDeadEnd = true;
            isDeadEnd &= addIfTraversable(current.up(), maze, edges, tried);
            isDeadEnd &= addIfTraversable(current.down(), maze, edges, tried);
            isDeadEnd &= addIfTraversable(current.right(), maze, edges, tried);
            isDeadEnd &= addIfTraversable(current.left(), maze, edges, tried);

            if (isDeadEnd) {
                way.remove(current);
            }
            current = edges.pop();
            way.add(current);
        }

        return current.equals(maze.getExit()) ? Optional.of(way) : Optional.empty();
    }

    private boolean addIfTraversable(Cell cell, Maze maze, Deque<Cell> ways, Set<Cell> tried ) {
        if (maze.canTraverse(cell) && tried.add(cell)) {
            ways.add(cell);
            return true;
        }
        return false;
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

