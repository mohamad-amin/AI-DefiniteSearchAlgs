package com.mohamadamin.ai.base.algorithm;

import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.State;

import java.util.List;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class Path {

    private final long cost;
    private final List<Action> actions;

    public Path(long cost, List<Action> actions) {
        this.cost = cost;
        this.actions = actions;
    }

    public long getCost() {
        return cost;
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "Path{" +
                "cost=" + cost +
                ", actions=" + actions +
                '}';
    }

}
