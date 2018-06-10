package com.mohamadamin.ai.base.problem;

/**
 * Created by MohamadAmin on 6/8/18.
 * Todo: don't check tag on comparing
 */
public abstract class State {

    private int tag;
    private final int depth;

    public State(int depth) {
        this.depth = depth;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getDepth() {
        return depth;
    }

    @Override
    public String toString() {
        return "State{" +
                "tag=" + tag +
                ", depth=" + depth +
                '}';
    }

}
