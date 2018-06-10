package com.mohamadamin.ai.problem.rescue;

import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.problem.puzzle.PuzzleAction;

/**
 * Created by MohamadAmin on 6/9/18.
 */
public class RescueAction extends Action<RescueState> {

    public RescueAction(String name, RescueState sourceState, RescueState destinationState) {
        super(1, name, sourceState, destinationState);
    }

    @Override
    public Action getReversed() {
        return new RescueAction(getReverseName(), getDestinationState(), getSourceState());
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
