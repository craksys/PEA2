package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Double.MAX_VALUE;

public class TabuSearch {
    private Graph graph;
    Random rand = new Random();
    public static long millisActualTime;
    public static long executionTime;
    public TabuSearch(Graph graph){
        this.graph = graph;
        bestPath = new int[graph.matrix.length-1];
        currentPath = new int[graph.matrix.length-1];
        nextPath = new int[graph.matrix.length-1];
        tabuMatrix = new int[graph.matrix.length][graph.matrix.length];
        iterations = 10 * graph.matrix.length;
    }

    int[] bestPath;
    int[] currentPath;
    int[] nextPath;

    double bestCost = MAX_VALUE;
    double currentCost;
    double nextCost;

    int[][] tabuMatrix;

    int swap_i, swap_j;

    int iterations;

    void solve() {
        millisActualTime = System.currentTimeMillis();
        while (true) {
            currentPath = generateRandomPath();
            nextPath = currentPath;

            for (int iteration = 0; iteration < iterations; iteration++) {
                nextCost = MAX_VALUE;
                swap_i = 0;
                swap_j = 0;

                for (int i = 0; i < graph.matrix.length; i++) {
                    for (int j = i + 1; j < graph.matrix.length; j++) {
                        swap(i, j, currentPath);
                        currentCost = calculatePathCost(currentPath);

                        if (currentCost < bestCost && tabuMatrix[i][j] == 0) {
                            bestPath = currentPath;
                            bestCost = currentCost;
                        }

                        if (currentCost < nextCost && tabuMatrix[i][j] < iteration) {
                            nextCost = currentCost;
                            nextPath = currentPath;
                            swap_i = i;
                            swap_j = j;
                        }
                    }
                }
                currentPath = nextPath;
                tabuMatrix[swap_i][swap_j] += graph.matrix.length; //update tabu

                for (int a = 0; a < graph.matrix.length; a++) {
                    for (int b = 0; b < graph.matrix.length; b++) {
                        if (tabuMatrix[a][b] > 0)
                            tabuMatrix[a][b] -= 1;
                    }
                }

                executionTime = System.currentTimeMillis() - millisActualTime;
                if (executionTime > 30000) { //minuta przerwy
                    System.out.println(bestCost);
                    return;
                }
            }
        }
    }


    int[] generateRandomPath() {
        int[] randomPath = new int[graph.matrix.length];
        for (int i = 0; i < graph.matrix.length; i++) {
            randomPath[i] = i;
        }
        for (int i = 0; i < randomPath.length; i++) {//funkcja losująca kolejność
            int randomIndexToSwap = rand.nextInt(randomPath.length);
            int temp = randomPath[randomIndexToSwap];
            randomPath[randomIndexToSwap] = randomPath[i];
            randomPath[i] = temp;
        }

        return randomPath;
    }

    double calculatePathCost(int[] path) {
        double cost = 0;
        for(int i = 0; i < path.length-1; i++){
            cost += graph.matrix[path[i]][path[i+1]];
        }
        cost += graph.matrix[(path[(path.length-1)])][path[0]];
        return cost;
    }

    void swap(int i, int j, int[] path){
        int temp = path[i];
        path[i] = path[j];
        path[j] = temp;
    }
}
