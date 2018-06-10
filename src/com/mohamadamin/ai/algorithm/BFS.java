package com.mohamadamin.ai.algorithm;

import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.State;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public class BFS extends SearchAlgorithm<LinkedList<Action>, HashMap<State, Action>> {

    public BFS(boolean graphSearch) {
        super(graphSearch, true);
    }

    @Override
    protected LinkedList<Action> constructStatesToExpandSet() {
        return new LinkedList<>();
    }

    @Override
    protected HashMap<State, Action> constructExpandedStatesSet() {
        return new HashMap<>();
    }

    @Override
    protected Action extractLeaf() {
        return statesToExpand.removeFirst();
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
        return expandedStates.containsKey(lastAction.getDestinationState()) || leafsContain(lastAction);
    }

    @Override
    protected void putLeafInExpandedStatesSet(Action action) {
        expandedStates.put(action.getDestinationState(), action);
    }

    @Override
    protected void putLeafInStatesToExpandSet(Action action) {
        if (!statesToExpand.contains(action)) {
            statesToExpand.addLast(action);
        }
    }

    @Override
    protected String getName() {
        return "Breadth First Search";
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
