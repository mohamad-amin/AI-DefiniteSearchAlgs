package com.mohamadamin.ai.base.algorithm;

import com.mohamadamin.ai.base.problem.Problem;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public abstract class Algorithm {

    protected final boolean graphSearch;
    protected long seenNodes, expandedNodes, maxNodesInMemory;

    public Algorithm(boolean graphSearch) {
        this.graphSearch = graphSearch;
    }

    public abstract Path findPathToGoal(Problem problem);

    public long getSeenNodes() {
        return seenNodes;
    }

    public long getExpandedNodes() {
        return expandedNodes;
    }

    public long getMaxNodesInMemory() {
        return maxNodesInMemory;
    }

    public boolean isGraphSearch() {
        return graphSearch;
    }

    protected abstract String getName();

    @Override
    public String toString() {
        return "Algorithm{" +
                "name=" + getName() +
                ", graphSearch=" + graphSearch +
                '}';
    }

}
