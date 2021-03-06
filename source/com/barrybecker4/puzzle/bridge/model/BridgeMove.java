/** Copyright by Barry G. Becker, 20013. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.puzzle.bridge.model;


import com.barrybecker4.puzzle.common.model.Move;

import java.util.List;

/**
 * Represents the act of one or two people crossing the bride.
 * At most two people can cross at once or the bridge may crash.
 * The light/torch is always transferred with the people that are crossing.
 * Immutable.
 * @author Barry Becker
 */

public final class BridgeMove implements Move, Comparable<BridgeMove> {

    /** if true then crossing */
    private boolean direction;

    /** the speeds of the person or people that are crossing */
    private List<Integer> people;

    private int cost;

    /**
     * create a move object representing a transition on the board.
     */
    BridgeMove(List<Integer> people, boolean direction) {
        this.people = people;
        this.direction = direction;
        cost = determineCost();
    }

    public List<Integer> getPeople() {
        return people;
    }
    public boolean getDirection() {
        return direction;
    }

    private int determineCost() {
        if (people.size() == 1) {
            return people.get(0);
        }
        return Math.max(people.get(0), people.get(1));
    }

    public int getCost() {
        return cost;
    }


    /** @return the from and to positions */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("people: ").append(people)
                .append(direction ? " -> " : " <- ");
        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BridgeMove that = (BridgeMove) o;
        return direction == that.direction && people.equals(that.people);
    }

    @Override
    public int hashCode() {
        int result = (direction ? 1 : 0);
        result = 31 * result + people.hashCode();
        return result;
    }

    @Override
    public int compareTo(BridgeMove m) {
        return getCost() - m.getCost();
    }
}

