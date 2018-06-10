package com.mohamadamin.ai.base.problem;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public interface Problem<S extends State, A extends Action> {

    @Nullable
    S getGoal();

    @NotNull
    S getInitialState();

    void retrieveInput();

    boolean isGoal(S state);

    @NotNull
    List<A> getActions(S state);

    int getHeuristic(S state);



}
