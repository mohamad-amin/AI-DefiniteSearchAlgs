package com.mohamadamin.ai.problem.puzzle;

import com.mohamadamin.ai.base.problem.Action;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class PuzzleAction extends Action<PuzzleState> {

    public PuzzleAction(String name, PuzzleState sourceState, PuzzleState destinationState) {
        super(1, name, sourceState, destinationState);
    }

    @Override
    public Action getReversed() {
        return new PuzzleAction(getReverseName(), getDestinationState(), getSourceState());
    }

    private String getReverseName() {
        switch (getName()) {
            case "u":
                return "d";
            case "d":
                return "u";
            case "l":
                return "r";
            case "r":
                return "l";
            default:
                return null;
        }
    }

}
