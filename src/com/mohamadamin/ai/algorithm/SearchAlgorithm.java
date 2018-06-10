package com.mohamadamin.ai.algorithm;

import com.mohamadamin.ai.base.algorithm.Algorithm;
import com.mohamadamin.ai.base.algorithm.Path;
import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.InitialAction;
import com.mohamadamin.ai.base.problem.Problem;
import com.mohamadamin.ai.base.problem.State;
import com.mohamadamin.ai.util.Environment;

import java.util.*;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public abstract class SearchAlgorithm<F extends Collection, E> extends Algorithm {

    protected long pathCost;
    protected Problem problem;
    protected final F statesToExpand;
    protected final E expandedStates;
    protected final Stack<Action> pathToGoal;
    private final boolean checkGoalOnCreation;

    public SearchAlgorithm(boolean graphSearch, boolean checkGoalOnCreation) {
        super(graphSearch);
        this.pathToGoal = new Stack<>();
        statesToExpand = constructStatesToExpandSet();
        expandedStates = constructExpandedStatesSet();
        this.checkGoalOnCreation = checkGoalOnCreation;
    }

    protected abstract F constructStatesToExpandSet();

    protected abstract E constructExpandedStatesSet();

    protected abstract Action extractLeaf();

    protected abstract boolean noLeafToExpand();

    protected abstract long getExpandedStatesCount();

    protected abstract Action findSeenAction(State state);

    protected abstract boolean haveSeen(Action lastAction);

    protected abstract boolean reachedGoal(Action lastAction);

    protected abstract void putLeafInExpandedStatesSet(Action action);

    protected abstract void putLeafInStatesToExpandSet(Action action);

    protected void putLeafInPathToGoal(Action action) {
        if (Environment.PRINT_PATH_DETAILS) {
            System.out.println(String.format("%sChanging path:", Environment.DIVIDER));
        }
        while (!pathToGoal.isEmpty() && !pathToGoal.peek().getDestinationState().equals(action.getSourceState())) {
            if (Environment.PRINT_PATH_DETAILS) {
                System.out.println(String.format("\tPopping %s", pathToGoal.peek()));
            }
            pathCost -= pathToGoal.pop().getCost();
        }
        if (Environment.PRINT_PATH_DETAILS) {
            System.out.println(String.format("\tPushing %s%s", action, Environment.DIVIDER));
        }
        pathCost += action.getCost();
        pathToGoal.push(action);
    }

    protected Path getPath(Action lastAction) {
        if (graphSearch) {
            if (Environment.PRINT_ALL_EXTRACTED_LEAFS) {
                if (expandedStates instanceof Map) {
                    System.out.println(String.format("%sExpanded states:", Environment.DIVIDER));
                    for (Object entry : ((Map) expandedStates).entrySet()) {
                        if (entry instanceof Map.Entry) {
                            System.out.println(String.format("\t%s -> %s",
                                    ((Map.Entry) entry).getKey(), ((Map.Entry) entry).getValue()));
                        }
                    }
                }
            }
            long cost = 0;
            List<Action> path = new ArrayList<>();
            while (lastAction != null) {
                path.add(lastAction);
                cost += lastAction.getCost();
                lastAction = findSeenAction(lastAction.getSourceState());
            }
            Collections.reverse(path);
            return new Path(cost, path);
        } else {
            List<Action> path = new ArrayList<>();
            Collections.reverse(pathToGoal);
            for (Action action : pathToGoal) {
                if (action.getSourceState() != null) {
                    path.add(action);
                } else {
                    break;
                }
            }
            Collections.reverse(path);
            pathCost += lastAction.getCost();
            path.add(lastAction);
            return new Path(pathCost, path);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Path findPathToGoal(Problem problem) {

        this.problem = problem;
        Action goalReachingAction = null;
        Action initialAction = InitialAction.from(problem.getInitialState());
        putLeafInStatesToExpandSet(initialAction);
        seenNodes++;
        maxNodesInMemory = Math.max(maxNodesInMemory, 1);

        if (checkGoalOnCreation && reachedGoal(initialAction)) {
            goalReachingAction = initialAction;
        }

        while (!noLeafToExpand()) {

            if (goalReachingAction != null) {
                break;
            }

            Action action = extractLeaf();
            if (graphSearch) {
                putLeafInExpandedStatesSet(action);
                maxNodesInMemory = Math.max(statesToExpand.size() + getExpandedStatesCount(), maxNodesInMemory);
            } else {
                putLeafInPathToGoal(action);
                maxNodesInMemory = Math.max(statesToExpand.size() + pathToGoal.size(), maxNodesInMemory);
            }
            if (Environment.PRINT_EXTRACTED_LEAF) {
                System.out.println(String.format("Extracted leaf -> %s", action));
            }

            if (!checkGoalOnCreation && reachedGoal(action)) {
                goalReachingAction = action;
                break;
            }

            expandedNodes++;

            List<Action> actions = problem.getActions(action.getDestinationState());
            for (Object actionObject : actions) {

                Action newAction = (Action) actionObject;
                if (action.getSourceState() != null) {
                    newAction.getDestinationState().setTag(action.getCost() + action.getSourceState().getTag());
                }

                if (newAction.getDestinationState().equals(action.getSourceState())) {
                    continue;
                }

                if (graphSearch) {
                    if (haveSeen(newAction)) {
                        if (Environment.PRINT_SEEN_CHECK) {
                            System.out.println(String.format("\tHave seen %s", newAction));
                        }
                        continue;
                    } else {
                        if (Environment.PRINT_SEEN_CHECK) {
                            System.out.println(String.format("\tHave NOT seen %s", newAction));
                        }
                    }
                }
                seenNodes++;

                if (checkGoalOnCreation && reachedGoal(newAction)) {
                    goalReachingAction = newAction;
                    break;
                }

                if (Environment.PRINT_LEAF_CREATION) {
                    System.out.println(String.format("\tCreating leaf -> %s", newAction));
                }

                putLeafInStatesToExpandSet(newAction);
                if (graphSearch) {
                    maxNodesInMemory = Math.max(statesToExpand.size() + getExpandedStatesCount(), maxNodesInMemory);
                } else {
                    maxNodesInMemory = Math.max(statesToExpand.size() + pathToGoal.size(), maxNodesInMemory);
                }

            }

            if (goalReachingAction != null) {
                break;
            }

        }

        return goalReachingAction == null ? null : getPath(goalReachingAction);

    }

}