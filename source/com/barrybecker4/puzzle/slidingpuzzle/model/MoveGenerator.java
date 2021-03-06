// Copyright by Barry G. Becker, 2013. Licensed under MIT License: http://www.opensource.org/licenses/MIT
package com.barrybecker4.puzzle.slidingpuzzle.model;

import com.barrybecker4.common.geometry.ByteLocation;
import com.barrybecker4.common.geometry.Location;

import java.util.LinkedList;
import java.util.List;

/**
 * Sliding Puzzle move generator. Generates valid next moves.
 *
 * @author Barry Becker
 */
public class MoveGenerator  {

    Slider board;

    public static final ByteLocation[] OFFSETS = {
        new ByteLocation(-1, 0),
        new ByteLocation(1, 0),
        new ByteLocation(0, -1),
        new ByteLocation(0, 1)
    };

    /**
     * Constructor
     */
    public MoveGenerator(Slider board) {
        this.board = board;
    }

    /**
     * Next moves are all the tiles that can slide into the current empty position.
     * @return List of all valid tile slides
     */
    public List<SlideMove> generateMoves() {
        List<SlideMove> moves = new LinkedList<>();

        Location blankLocation = board.getEmptyLocation();

        for (ByteLocation loc : OFFSETS) {
            int row = blankLocation.getRow() + loc.getRow();
            int col = blankLocation.getCol() + loc.getCol();
            ByteLocation newLoc = new ByteLocation(row, col);
            if (board.isValidPosition(newLoc)) {
                moves.add(new SlideMove(newLoc, blankLocation)) ;
            }
        }
        return moves;
    }
}
