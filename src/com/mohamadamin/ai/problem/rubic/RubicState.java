package com.mohamadamin.ai.problem.rubic;

import com.mohamadamin.ai.base.problem.State;

import java.util.Arrays;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class RubicState extends State {

    private final char[] rubic;

    public RubicState(int depth, char[] rubic) {
        super(depth);
        this.rubic = rubic;
    }

    public char[] getRubic() {
        return rubic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RubicState)) return false;
        RubicState rubicState = (RubicState) o;
        return Arrays.equals(rubic, rubicState.rubic);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(rubic);
    }

    @Override
    public String toString() {
        return "{ " +
                "rubic=" + Arrays.toString(rubic) +
                " }";
    }

}
