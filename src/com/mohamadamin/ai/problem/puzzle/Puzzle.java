package com.mohamadamin.ai.problem.puzzle;

import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.Problem;
import com.mohamadamin.ai.util.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by MohamadAmin on 6/10/18.
 */
public class Puzzle implements Problem<PuzzleState, PuzzleAction> {

    private static short[][] nums = new short[][] {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
    };
    private static PuzzleState goal = new PuzzleState(-1, nums, (short) 0, (short) 0);

    private PuzzleState initialState;

    @Override
    public PuzzleState getGoal() {
        if (Environment.PRINT_GOAL) {
            System.out.println(String.format("Goal -> %s", goal));
        }
        return goal;
    }

    @Override
    public PuzzleState getInitialState() {
        return initialState;
    }

    @Override
    public void retrieveInput() {
        Scanner in = new Scanner(System.in);
        short[][] nums = new short[3][3];
        short zeroX = -1, zeroY = -1;
        for (int i = 0; i < 9; i++) {
            short x = in.nextShort();
            if (x == 0) {
                zeroY = (short) (i/3);
                zeroX = (short) (i%3);
            }
            nums[i/3][i%3] = x;
        }
        if (zeroX == -1 || zeroY == -1) {
            throw new RuntimeException("No zero input!");
        }
        initialState = new PuzzleState(0, nums, zeroX, zeroY);
        if (Environment.PRINT_INPUT) {
            System.out.println(String.format("%sInput", Environment.DIVIDER));
            System.out.println(String.format("%s", initialState));
        }
    }

    @Override
    public boolean isGoal(PuzzleState state) {
        return getGoal().equals(state);
    }

    @Override
    public List<PuzzleAction> getActions(PuzzleState state) {

        int y = state.getZeroY(), x = state.getZeroX();
        List<PuzzleAction> result = new ArrayList<>();

        if (y != 0) {
            PuzzleState up = up(state);
            result.add(new PuzzleAction("u", state, up));
        }
        if (y != 2) {
            PuzzleState down = down(state);
            result.add(new PuzzleAction("d", state, down));
        }

        if (x != 0) {
            PuzzleState left = left(state);
            result.add(new PuzzleAction("l", state, left));
        }
        if (x != 2) {
            PuzzleState right = right(state);
            result.add(new PuzzleAction("r", state, right));
        }

        if (Environment.PRINT_ACTIONS) {
            System.out.println(String.format("%sActions from %s -> ", Environment.DIVIDER, state));
            for (Action action : result) {
                System.out.println(String.format("\t%s", action));
            }
        }

        return result;
        
    }

    // change zero with it's left-side value
    private PuzzleState left(PuzzleState state) {
        short[][] nums = copyNums(state.getNums());
        nums[state.getZeroY()][state.getZeroX()] = nums[state.getZeroY()][state.getZeroX()-1];
        nums[state.getZeroY()][state.getZeroX()-1] = 0;
        return new PuzzleState(state.getDepth()+1, nums, (short) (state.getZeroX()-1), state.getZeroY());
    }

    private PuzzleState right(PuzzleState state) {
        short[][] nums = copyNums(state.getNums());
        nums[state.getZeroY()][state.getZeroX()] = nums[state.getZeroY()][state.getZeroX()+1];
        nums[state.getZeroY()][state.getZeroX()+1] = 0;
        return new PuzzleState(state.getDepth()+1, nums, (short) (state.getZeroX()+1), state.getZeroY());
    }

    private PuzzleState up(PuzzleState state) {
        short[][] nums = copyNums(state.getNums());
        nums[state.getZeroY()][state.getZeroX()] = nums[state.getZeroY()-1][state.getZeroX()];
        nums[state.getZeroY()-1][state.getZeroX()] = 0;
        return new PuzzleState(state.getDepth()+1, nums,  state.getZeroX(), (short) (state.getZeroY()-1));
    }

    private PuzzleState down(PuzzleState state) {
        short[][] nums = copyNums(state.getNums());
        nums[state.getZeroY()][state.getZeroX()] = nums[state.getZeroY()+1][state.getZeroX()];
        nums[state.getZeroY()+1][state.getZeroX()] = 0;
        return new PuzzleState(state.getDepth()+1, nums,  state.getZeroX(), (short) (state.getZeroY()+1));
    }

    private short[][] copyNums(short[][] nums) {
        short[][] newNums = new short[3][3];
        for (int i = 0; i < 9; i++) {
            newNums[i/3][i%3] = nums[i/3][i%3];
        }
        return newNums;
    }

    @Override
    public int getHeuristic(PuzzleState state) {
        int heuristic = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int xDiff = state.getNums()[i][j] - i;
                int yDiff = state.getNums()[i][j] - j;
                heuristic += xDiff*xDiff + yDiff*yDiff;
            }
        }
        if (Environment.PRINT_HEURISTIC) {
            System.out.println(String.format("Heuristic for %s -> %s", state, heuristic));
        }
        return heuristic;
    }

}
