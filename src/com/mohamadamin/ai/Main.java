package com.mohamadamin.ai;

import com.mohamadamin.ai.algorithm.BS;
import com.mohamadamin.ai.base.algorithm.Algorithm;
import com.mohamadamin.ai.base.algorithm.Path;
import com.mohamadamin.ai.base.problem.Action;
import com.mohamadamin.ai.base.problem.Problem;
import com.mohamadamin.ai.problem.puzzle.Puzzle;
import com.mohamadamin.ai.problem.rescue.RescueRobot;
import com.mohamadamin.ai.util.Environment;

public class Main {

    public static void main(String[] args) {
        Algorithm algorithm = new BS(true);
        Problem problem = new Puzzle();
        runAlgorithmOnProblem(algorithm, problem);
    }

    private static void runAlgorithmOnProblem(Algorithm algorithm, Problem problem) {
        problem.retrieveInput();
        Path path = algorithm.findPathToGoal(problem);
        if (path == null) {
            System.out.println("Couldn't find the answer :(");
        } else {
            System.out.println(String.format("%sPath to goal with cost %d:", Environment.DIVIDER, path.getCost()));
            for (Action action : path.getActions()) {
                System.out.println(action);
            }
        }
        System.out.println(String.format("Seen Nodes -> %s", algorithm.getSeenNodes()));
        System.out.println(String.format("Expanded Nodes -> %s", algorithm.getExpandedNodes()));
        System.out.println(String.format("Max Nodes In Memory -> %s", algorithm.getMaxNodesInMemory()));
    }

}

/* Rescue Input
5 5
4
3 2 4 2
3 3 4 3
2 3 2 4
3 3 3 4
*/

/* Puzzle Input
4 5 2
1 3 7
0 6 8

2 0 1
4 3 5
6 7 8
*/

/* Rubic
y b y b g y g y w g w g b w b w r r r r o o o o
*/