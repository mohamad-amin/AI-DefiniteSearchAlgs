package com.mohamadamin.ai.base.problem;

/**
 * Created by MohamadAmin on 6/8/18.
 */
public class InitialAction<S extends State> extends Action<S> {

    public static <S extends State> InitialAction<S> from(S state) {
        return new InitialAction<>(state);
    }

    private InitialAction(S state) {
        super(0, "initial", null, state);
    }

    @Override
    public Action getReversed() {
        return null;
    }

}
