package com.mohamadamin.ai.algorithm;

import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.State;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public class UCS extends SearchAlgorithm<PriorityQueue<Action>, HashMap<State, Action>> {

    public UCS(boolean graphSearch) {
        super(graphSearch, false);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected PriorityQueue<Action> constructStatesToExpandSet() {
        return new PriorityQueue<>(Comparator.comparingInt(action -> action.getDestinationState().getTag()));
    }

    @Override
    protected HashMap<State, Action> constructExpandedStatesSet() {
        return new HashMap<>();
    }

    @Override
    protected Action extractLeaf() {
        return statesToExpand.poll();
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
    protected boolean haveSeen(Action action) {
        Action previousReachingAction = expandedStates.get(action.getDestinationState());
        return (previousReachingAction != null &&
                previousReachingAction.getDestinationState().getTag() <= action.getDestinationState().getTag())
                || leafsContain(action);
    }


    @Override
    protected void putLeafInExpandedStatesSet(Action action) {
        expandedStates.put(action.getDestinationState(), action);
    }

    @Override
    protected void putLeafInStatesToExpandSet(Action action) {
        if (!statesToExpand.contains(action)) {
            statesToExpand.add(action);
        }
    }

    @Override
    protected String getName() {
        return "Uniform Cost Search";
    }

    // NOTE: if this is true, we remove the item
    private boolean leafsContain(Action lastAction) {
        Action actionToRemove = null;
        for (Action action : statesToExpand) {
            if (action.getDestinationState().equals(lastAction.getDestinationState())) {
                if (action.getDestinationState().getTag() <= lastAction.getDestinationState().getTag()) {
                    return true;
                } else {
                    actionToRemove = action;
                    break;
                }
            }
        }
        if (actionToRemove != null) {
            statesToExpand.remove(actionToRemove);
        }
        return false;
    }

}
