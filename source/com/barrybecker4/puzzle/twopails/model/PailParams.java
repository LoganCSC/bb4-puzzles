package com.barrybecker4.puzzle.twopails.model;

/**
 * Defines the two parameters for the two pail problem.
 * Immutable
 * @author Barry Becker
 */
public class PailParams {

    /** the maximum capacity of any pail */
    public static final int MAX_CAPACITY = 20;

    private byte pail1Size;
    private byte pail2Size;
    private byte targetMeasureSize;

    public PailParams(int pail1Size, int pail2Size, int targetMeasureSize) {
        this.pail1Size = (byte) pail1Size;
        this.pail2Size = (byte) pail2Size;
        this.targetMeasureSize = (byte) targetMeasureSize;
    }

    public byte getPail1Size() {
        return pail1Size;
    }

    public byte getPail2Size() {
        return pail2Size;
    }

    public byte getTargetMeasureSize() {
        return targetMeasureSize;
    }

    public byte getBiggest() {
        return (byte) Math.max(pail1Size, pail2Size);
    }
}