package com.mohamadamin.ai.problem.puzzle;

import com.mohamadamin.ai.base.problem.State;

import java.util.Arrays;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class PuzzleState extends State {

    private final short nums[][];
    private final short zeroX, zeroY;

    public PuzzleState(int depth, short[][] nums, short zeroX, short zeroY) {
        super(depth);
        this.nums = nums;
        this.zeroX = zeroX;
        this.zeroY = zeroY;
    }

    public short[][] getNums() {
        return nums;
    }

    public short getZeroX() {
        return zeroX;
    }

    public short getZeroY() {
        return zeroY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleState)) return false;
        PuzzleState that = (PuzzleState) o;
        if (zeroX != that.zeroX) return false;
        if (zeroY != that.zeroY) return false;
        return Arrays.deepEquals(nums, that.nums);
    }

    @Override
    public int hashCode() {
        int result = Arrays.deepHashCode(nums);
        result = 31 * result + (int) zeroX;
        result = 31 * result + (int) zeroY;
        return result;
    }

    @Override
    public String toString() {
        return "{ " +
                "nums=" + numsToString() +
                " }";
    }

    private String numsToString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            builder.append(Arrays.toString(nums[i]));
            if (i != 2) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

}
