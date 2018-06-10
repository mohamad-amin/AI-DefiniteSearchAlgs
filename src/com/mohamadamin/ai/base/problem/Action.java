package com.mohamadamin.ai.base.problem;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public abstract class Action<S extends State> {

    private final int cost;
    private final String name;
    private final S sourceState;
    private final S destinationState;

    public Action(int cost, String name, S sourceState, S destinationState) {
        this.cost = cost;
        this.name = name;
        this.sourceState = sourceState;
        this.destinationState = destinationState;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public S getSourceState() {
        return sourceState;
    }

    public S getDestinationState() {
        return destinationState;
    }

    public abstract Action getReversed();

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Action && destinationState.equals(((Action) obj).getDestinationState());
    }

    @Override
    public String toString() {
        return "{ " +
                "name=" + name +
                ", source=" + sourceState +
                ", dest=" + destinationState +
                ", cost=" + cost +
                ", tag=" + destinationState.getTag() +
                " }";
    }

}
