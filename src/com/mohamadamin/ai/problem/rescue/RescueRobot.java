package com.mohamadamin.ai.problem.rescue;

import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.Problem;
import com.mohamadamin.ai.util.Environment;

import java.util.*;

/**
 * Created by MohamadAmin on 6/9/18.
 */
public class RescueRobot implements Problem<RescueState, RescueAction> {

    private int n, m;
    private HashMap<RescueState, RescueState> walls;

    @Override
    public void retrieveInput() {
        Scanner in = new Scanner(System.in);
        n = in.nextInt();
        m = in.nextInt();
        walls = new HashMap<>();
        int wallsCount = in.nextInt();
        for (int i = 0; i < wallsCount; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1, c = in.nextInt() - 1, d = in.nextInt() - 1;
            RescueState first =  new RescueState(a, b);
            RescueState second =  new RescueState(c, d);
            walls.put(first, second);
            walls.put(second, first);
        }
        if (Environment.PRINT_INPUT) {
            System.out.println(String.format("%sInput", Environment.DIVIDER));
            System.out.println(String.format("(n,m) = %s, %s", n, m));
            for (Map.Entry<RescueState, RescueState> wall : walls.entrySet()) {
                System.out.println(String.format("Wall: %s -> %s", wall.getKey(), wall.getValue()));
            }
        }
    }

    @Override
    public RescueState getGoal() {
        RescueState goal = new RescueState(n-1, m-1);
        if (Environment.PRINT_GOAL) {
            System.out.println(String.format("Goal -> %s", goal));
        }
        return goal;
    }

    @Override
    public RescueState getInitialState() {
        RescueState initial = new RescueState(0, 0, 0);
        if (Environment.PRINT_INITIAL) {
            System.out.println(String.format("Initial -> %s", initial));
        }
        return initial;
    }

    @Override
    public boolean isGoal(RescueState state) {
        return state.equals(getGoal());
    }

    @Override
    public List<RescueAction> getActions(RescueState state) {

        int y = state.getY(), x = state.getX();
        List<RescueAction> result = new ArrayList<>();

        if (y != 0) {
            RescueState up = up(state);
            if (!up.equals(walls.get(state))) {
                result.add(new RescueAction("u", state, up));
            }
        }
        if (y != n-1) {
            RescueState down = down(state);
            if (!down.equals(walls.get(state))) {
                result.add(new RescueAction("d", state, down));
            }
        }

        if (x != 0) {
            RescueState left = left(state);
            if (!left.equals(walls.get(state))) {
                result.add(new RescueAction("l", state, left));
            }
        }
        if (x != n-1) {
            RescueState right = right(state);
            if (!right.equals(walls.get(state))) {
                result.add(new RescueAction("r", state, right));
            }
        }

        if (Environment.PRINT_ACTIONS) {
            System.out.println(String.format("%sActions from %s -> ", Environment.DIVIDER, state));
            for (Action action : result) {
                System.out.println(String.format("\t%s", action));
            }
        }

        return result;

    }

    private RescueState left(RescueState state) {
        return new RescueState(state.getDepth()+1, state.getY(), state.getX()-1);
    }

    private RescueState right(RescueState state) {
        return new RescueState(state.getDepth()+1, state.getY(), state.getX()+1);
    }

    private RescueState up(RescueState state) {
        return new RescueState(state.getDepth()+1, state.getY()-1, state.getX());
    }

    private RescueState down(RescueState state) {
        return new RescueState(state.getDepth()+1, state.getY()+1, state.getX());
    }

    @Override
    public int getHeuristic(RescueState state) {
        RescueState goal = getGoal();
        int difX = goal.getX() - state.getX();
        int difY = goal.getY() - state.getY();
        int heuristic = difX*difX + difY*difY;
        if (Environment.PRINT_HEURISTIC) {
            System.out.println(String.format("Heuristic for %s -> %s", state, heuristic));
        }
        return heuristic;
    }

}
