package com.mohamadamin.ai.problem.rubic;

import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.Problem;
import com.mohamadamin.ai.util.Environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class Rubic implements Problem<RubicState, RubicAction> {

    public static final String[] MOVES = new String[]{"T", "TC", "F", "FC", "R", "RC"};

    private RubicState initialState;

    @Override
    public RubicState getGoal() {
        return null;
    }

    @Override
    public RubicState getInitialState() {
        return initialState;
    }

    @Override
    public void retrieveInput() {
        char[] rubic = new char[24];
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 24; i++) {
            rubic[i] = in.next().charAt(0);
        }
        initialState = new RubicState(0, rubic);
        if (Environment.PRINT_INITIAL) {
            System.out.println(String.format("Initial State -> %s", initialState));
        }
    }

    @Override
    public boolean isGoal(RubicState state) {
        for (int i = 0; i < 6; i++) {
            if (!(state.getRubic()[i*4] == state.getRubic()[1 + i*4]
                    && state.getRubic()[i*4] == state.getRubic()[2 + i*4])) {
                return false;
            }
            if (!(state.getRubic()[i * 4] == state.getRubic()[3 + i * 4])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<RubicAction> getActions(RubicState state) {
        
        List<RubicAction> actions = new ArrayList<>();
        
        for (String move: MOVES) {
            
            char[] rubic = Arrays.copyOf(state.getRubic(), 24);
            
            if ("RC".equals(move)) {
                rubic[1] = state.getRubic()[13];
                rubic[3] = state.getRubic()[15];
                rubic[5] = state.getRubic()[1];
                rubic[7] = state.getRubic()[3];
                rubic[9] = state.getRubic()[5];
                rubic[11] = state.getRubic()[7];
                rubic[13] = state.getRubic()[9];
                rubic[15] = state.getRubic()[11];

                rubic[20] = state.getRubic()[22];
                rubic[21] = state.getRubic()[20];
                rubic[22] = state.getRubic()[23];
                rubic[23] = state.getRubic()[21];
            }
            
            if ("R".equals(move)) {
                rubic[1] = state.getRubic()[5];
                rubic[3] = state.getRubic()[7];
                rubic[5] = state.getRubic()[9];
                rubic[7] = state.getRubic()[11];
                rubic[9] = state.getRubic()[13];
                rubic[11] = state.getRubic()[15];
                rubic[13] = state.getRubic()[1];
                rubic[15] = state.getRubic()[3];

                rubic[20] = state.getRubic()[21];
                rubic[21] = state.getRubic()[23];
                rubic[22] = state.getRubic()[20];
                rubic[23] = state.getRubic()[22];
            }

            if ("T".equals(move)) {
                rubic[16] = state.getRubic()[4];
                rubic[17] = state.getRubic()[5];
                rubic[14] = state.getRubic()[16];
                rubic[15] = state.getRubic()[17];
                rubic[21] = state.getRubic()[14];
                rubic[20] = state.getRubic()[15];
                rubic[4] = state.getRubic()[20];
                rubic[5] = state.getRubic()[21];

                rubic[0] = state.getRubic()[2];
                rubic[1] = state.getRubic()[0];
                rubic[2] = state.getRubic()[3];
                rubic[3] = state.getRubic()[1];
            }

            if ("TC".equals(move)) {
                rubic[20] = state.getRubic()[4];
                rubic[21] = state.getRubic()[5];
                rubic[5] = state.getRubic()[17];
                rubic[4] = state.getRubic()[16];
                rubic[17] = state.getRubic()[14];
                rubic[16] = state.getRubic()[15];
                rubic[15] = state.getRubic()[20];
                rubic[14] = state.getRubic()[21];
                rubic[0] = state.getRubic()[1];
                rubic[1] = state.getRubic()[3];
                rubic[2] = state.getRubic()[0];
                rubic[3] = state.getRubic()[2];
            }
            
            if ("F".equals(move)) {
                rubic[2] = state.getRubic()[19];
                rubic[3] = state.getRubic()[17];
                rubic[20] = state.getRubic()[2];
                rubic[22] = state.getRubic()[3];
                rubic[9] = state.getRubic()[20];
                rubic[8] = state.getRubic()[22];
                rubic[19] = state.getRubic()[9];
                rubic[17] = state.getRubic()[8];
                rubic[4] = state.getRubic()[6];
                rubic[5] = state.getRubic()[4];
                rubic[6] = state.getRubic()[7];
                rubic[7] = state.getRubic()[5];
            }
            
            if ("FC".equals(move)) {
                rubic[2] = state.getRubic()[20];
                rubic[3] = state.getRubic()[22];
                rubic[20] = state.getRubic()[9];
                rubic[22] = state.getRubic()[8];
                rubic[9] = state.getRubic()[19];
                rubic[8] = state.getRubic()[17];
                rubic[19] = state.getRubic()[3];
                rubic[17] = state.getRubic()[3];
                rubic[4] = state.getRubic()[5];
                rubic[5] = state.getRubic()[7];
                rubic[6] = state.getRubic()[4];
                rubic[7] = state.getRubic()[6];   
            }
            
            actions.add(new RubicAction(move, state, new RubicState(state.getDepth()+1, rubic)));
            
        }

        if (Environment.PRINT_ACTIONS) {
            System.out.println(String.format("%sActions from %s -> ", Environment.DIVIDER, state));
            for (Action action : actions) {
                System.out.println(String.format("\t%s", action));
            }
        }
        
        return actions;

    }

    @Override
    public int getHeuristic(RubicState state) {
        return -1;
    }

}
