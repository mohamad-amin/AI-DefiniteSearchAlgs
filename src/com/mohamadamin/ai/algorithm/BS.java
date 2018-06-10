package com.mohamadamin.ai.algorithm;

import com.mohamadamin.ai.base.algorithm.Path;
import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.InitialAction;
import com.mohamadamin.ai.base.problem.State;
import com.mohamadamin.ai.util.Environment;
import org.omg.PortableInterceptor.ACTIVE;

import java.util.*;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public class BS extends SearchAlgorithm<List<LinkedList<Action>>, List<HashMap<State, Action>>> {

    private boolean addedGoal = false;
    private boolean turnForGoal = false;

    public BS(boolean graphSearch) {
        super(graphSearch, true);
    }

    @Override
    protected List<LinkedList<Action>> constructStatesToExpandSet() {
        List<LinkedList<Action>> lists = new ArrayList<>();
        lists.add(new LinkedList<>());
        lists.add(new LinkedList<>());
        return lists;
    }

    @Override
    protected List<HashMap<State, Action>> constructExpandedStatesSet() {
        List<HashMap<State, Action>> sets = new ArrayList<>();
        sets.add(new HashMap<>());
        sets.add(new HashMap<>());
        return sets;
    }

    @Override
    protected Action extractLeaf() {
        turnForGoal = !turnForGoal;
        if (Environment.PRINT_EXTRACTED_LEAF) {
            System.out.println(String.format("Turn for %s", turnForGoal ? "Goal" : "Initial"));
        }
        return statesToExpand.get(getIndexOfCurrentCollection()).removeFirst();
    }

    @Override
    protected boolean noLeafToExpand() {
        return statesToExpand.get(0).isEmpty() && statesToExpand.get(1).isEmpty();
    }

    @Override
    protected long getExpandedStatesCount() {
        return expandedStates.get(0).size() + expandedStates.get(1).size();
    }

    @Override
    protected Action findSeenAction(State state) {
        return expandedStates.get(getIndexOfCurrentCollection()).get(state);
    }

    @Override
    protected boolean haveSeen(Action action) {
        return expandedStates.get(getIndexOfCurrentCollection()).containsKey(action.getDestinationState())
                || leafsContain(action);
    }

    @Override
    protected boolean reachedGoal(Action lastAction) {
        return expandedStates.get(1 - getIndexOfCurrentCollection()).containsKey(lastAction.getDestinationState());
    }

    @Override
    protected void putLeafInExpandedStatesSet(Action action) {
        expandedStates.get(getIndexOfCurrentCollection()).put(action.getDestinationState(), action);
        if (graphSearch) {
            maxNodesInMemory = Math.max(statesToExpand.size() + getExpandedStatesCount(), maxNodesInMemory);
        } else {
            maxNodesInMemory = Math.max(statesToExpand.get(0).size() + statesToExpand.get(1).size(), maxNodesInMemory);
        }
    }

    @Override
    protected void putLeafInStatesToExpandSet(Action action) {
        if (!statesToExpand.get(getIndexOfCurrentCollection()).contains(action)) {
            statesToExpand.get(getIndexOfCurrentCollection()).addLast(action);
        }
        if (!addedGoal) {
            statesToExpand.get(1).add(InitialAction.from(problem.getGoal()));
            addedGoal = true;
        }
        if (graphSearch) {
            maxNodesInMemory = Math.max(statesToExpand.size() + getExpandedStatesCount(), maxNodesInMemory);
        } else {
            maxNodesInMemory = Math.max(statesToExpand.get(0).size() + statesToExpand.get(1).size(), maxNodesInMemory);
        }
    }

    @Override
    protected Path getPath(Action lastAction) {
        Path firstPath = findPathFromInitial(lastAction);
        Path secondPath = findPathFromGoal(lastAction);
        List<Action> path = new ArrayList<>();
        long cost = firstPath.getCost();
        path.addAll(firstPath.getActions());
        if (secondPath.getActions().get(0).getDestinationState().equals(path.get(path.size()-1).getDestinationState())) {
            cost -= secondPath.getActions().get(0).getCost();
            secondPath.getActions().remove(0);
        }
        path.addAll(secondPath.getActions());
        cost += secondPath.getCost();
        return new Path(cost, path);
    }

    @Override
    protected String getName() {
        return "Bidirectional Search";
    }

    private boolean leafsContain(Action lastAction) {
        for (Action action : statesToExpand.get(getIndexOfCurrentCollection())) {
            if (action.getDestinationState().equals(lastAction.getDestinationState())) {
                return true;
            }
        }
        return false;
    }

    private Path findPathFromGoal(Action lastAction) {
        long cost = 0;
        List<Action> path = new ArrayList<>();
        while (lastAction != null) {
            path.add(lastAction.getReversed());
            cost += lastAction.getCost();
            lastAction = expandedStates.get(1).get(lastAction.getSourceState());
        }
        path.remove(path.size()-1);
        return new Path(cost, path);
    }

    private Path findPathFromInitial(Action lastAction) {
        lastAction = turnForGoal ? lastAction.getReversed() : lastAction;
        long cost = 0;
        List<Action> path = new ArrayList<>();
        while (lastAction != null) {
            path.add(lastAction);
            cost += lastAction.getCost();
            lastAction = expandedStates.get(0).get(lastAction.getSourceState());
        }
        Collections.reverse(path);
        return new Path(cost, path);
    }

    private int getIndexOfCurrentCollection() {
        return turnForGoal ? 1 : 0;
    }

}
