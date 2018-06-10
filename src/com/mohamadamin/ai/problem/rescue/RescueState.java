package com.mohamadamin.ai.problem.rescue;

import com.mohamadamin.ai.base.problem.State;

/**
 * Created by MohamadAmin on 6/9/18.
 */
public class RescueState extends State {

    private final int x, y;

    public RescueState(int y, int x) {
        super(-1);
        this.x = x;
        this.y = y;
    }

    public RescueState(int depth, int y, int x) {
        super(depth);
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RescueState)) return false;
        RescueState that = (RescueState) o;
        if (x != that.x) return false;
        return y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "x=" + x +
                ", y=" + y +
                ", d=" + getDepth() +
                "}";
    }

}
