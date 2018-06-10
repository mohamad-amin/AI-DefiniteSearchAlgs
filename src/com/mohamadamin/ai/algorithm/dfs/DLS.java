package com.mohamadamin.ai.algorithm.dfs;

import com.mohamadamin.ai.algorithm.SearchAlgorithm;
import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.State;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public class DLS extends SearchAlgorithm<Stack<Action>, HashMap<State, Action<State>>> {

    private final int maxDepth;

    public DLS(int maxDepth, boolean graphSearch) {
        super(graphSearch, true);
        this.maxDepth = maxDepth;
    }

    @Override
    protected Stack<Action> constructStatesToExpandSet() {
        return new Stack<>();
    }

    @Override
    protected HashMap<State, Action<State>> constructExpandedStatesSet() {
        return new HashMap<>();
    }

    @Override
    protected Action extractLeaf() {
        return statesToExpand.pop();
    }

    @Override
    protected boolean noLeafToExpand() {
        return statesToExpand.isEmpty();
    }

    @Override
    protected long getExpandedStatesCount() {
        return expandedStates.size();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected boolean reachedGoal(Action lastAction) {
        return problem.isGoal(lastAction.getDestinationState());
    }

    @Override
    protected Action findSeenAction(State state) {
        return expandedStates.get(state);
    }

    @Override
    protected boolean haveSeen(Action lastAction) {
        return expandedStates.containsKey(lastAction.getDestinationState())
                || leafsContain(lastAction) || lastAction.getDestinationState().getDepth() > maxDepth;
    }

    @Override
    protected void putLeafInStatesToExpandSet(Action lastAction) {
        if (lastAction.getDestinationState().getDepth() <= maxDepth) {
            statesToExpand.push(lastAction);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void putLeafInExpandedStatesSet(Action lastAction) {
        expandedStates.put(lastAction.getDestinationState(), lastAction);
    }

    @Override
    protected String getName() {
        return "Depth Limited Search";
    }

    private boolean leafsContain(Action lastAction) {
        for (Action action : statesToExpand) {
            if (action.getDestinationState().equals(lastAction.getDestinationState())) {
                return true;
            }
        }
        return false;
    }

}