/** Copyright by Barry G. Becker, 2000-2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.puzzle.maze;

import com.barrybecker4.common.geometry.IntLocation;
import com.barrybecker4.common.geometry.Location;
import com.barrybecker4.common.math.MathUtil;
import com.barrybecker4.puzzle.maze.model.Direction;
import com.barrybecker4.puzzle.maze.model.GenState;
import com.barrybecker4.puzzle.maze.model.MazeCell;
import com.barrybecker4.puzzle.maze.model.MazeModel;
import com.barrybecker4.puzzle.maze.model.StateStack;
import com.barrybecker4.puzzle.maze.ui.MazePanel;

/**
 *  Program to automatically generate a Maze.
 *  Motivation: Get my son, Brian, to excel at Kumon by trying these mazes with a pencil.
 *  this is the global space containing all the cells, walls, and particles.
 *  Assumes an M*N grid of cells.
 *  X axis increases to the left.
 *  Y axis increases downwards to be consistent with java graphics.
 *
 *  @author Barry Becker
 */
public class MazeGenerator {

    private MazeModel maze;
    private MazePanel panel;
    private final StateStack stack;

    /** put the stop point at the maximum search depth. */
    private int maxDepth = 0;

    /** set this to true to get the generator to stop generating */
    private boolean interrupted;

    /** Constructor */
    public  MazeGenerator(MazePanel panel) {
        this.panel = panel;
        interrupted = false;
        maze = this.panel.getMaze();
        stack = new StateStack();
    }

    /**
     * generate the maze.
     */
    public void generate(double forwardProb, double leftProb, double rightProb) {

        maxDepth = 0;

        Direction.FORWARD.setProbability(forwardProb);
        Direction.LEFT.setProbability(leftProb);
        Direction.RIGHT.setProbability(rightProb);

        search();
        panel.repaint();
    }

    /**
     * Do a depth first search (without recursion) of the grid space to determine the graph.
     * Used to use a recursive algorithm but it was slower and would give stack overflow
     * exceptions even for moderately sized mazes.
     */
    public void search() {
        stack.clear();

        Location currentPosition = maze.getStartPosition();
        MazeCell currentCell = maze.getCell(currentPosition);
        currentCell.visited = true;

        // push the initial moves
        stack.pushMoves(currentPosition, new IntLocation(0, 1), 0);

        while ( !stack.isEmpty() && !interrupted ) {
            currentCell = findNextCell(currentCell);
        }
    }

    public void interrupt()
    {
        interrupted = true;
        if (stack != null)  {
            stack.clear();
        }
    }

    /** find the next cell to visit, given the last cell */
    private MazeCell findNextCell(MazeCell lastCell) {

        boolean moved = false;

        Location currentPosition;
        MazeCell nextCell;
        int depth;
        Location dir;

        do {
            GenState state = stack.remove(0);  // pop

            currentPosition = state.getPosition();
            dir = state.getDirection();
            depth = state.getDepth();

            if ( depth > maxDepth) {
                maxDepth = depth;
                maze.setStopPosition(currentPosition);
            }
            if ( depth > lastCell.getDepth() )  {
                lastCell.setDepth(depth);
            }

            MazeCell currentCell = maze.getCell(currentPosition);
            Location nextPosition = currentCell.getNextPosition(currentPosition, dir);
            nextCell = maze.getCell(nextPosition);

            if (nextCell.visited) {
                addWall(currentCell, dir, nextCell);
            }
            else {
                moved = true;
                nextCell.visited = true;
                currentPosition = nextPosition;
            }

            refresh();
        } while ( !moved && !stack.isEmpty() && !interrupted );

        refresh();
        // now at a new location
        if ( moved && !interrupted)  {
            stack.pushMoves(currentPosition, dir, ++depth);
        }
        return nextCell;
    }

    private void addWall(MazeCell currentCell, Location dir, MazeCell nextCell) {
        // add a wall
        if ( dir.getX() == 1 ) {         // east
            currentCell.eastWall = true;
        } else if ( dir.getY() == 1 ) {  // south
            currentCell.southWall = true;
        } else if ( dir.getX() == -1 )  { // west
            nextCell.eastWall = true;
        } else if ( dir.getY() == -1 ) {  // north
            nextCell.southWall = true;
        }
    }


    /** this can be really slow if you do a refresh every time */
    private void refresh() {
        if (MathUtil.RANDOM.nextDouble() < 4.0/(Math.pow(panel.getAnimationSpeed(), 2) + 1)) {
            panel.paintAll();
        }
    }

}
