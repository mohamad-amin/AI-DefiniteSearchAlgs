package com.mohamadamin.ai.base.algorithm;

import com.mohamadamin.ai.base.problem.State;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class Node<S extends State> {

    private S state;
    private S lastParent;

    public Node(S state, S parent) {
        this.lastParent = parent;
    }

    public void updateParent(S parent) {
        this.lastParent = parent;
    }

    public S getState() {
        return state;
    }

    public S getLastParent() {
        return lastParent;
    }

    @Override
    public String toString() {
        return "Node{" +
                "state=" + state +
                ", lastParent=" + lastParent +
                '}';
    }

}
