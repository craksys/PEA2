package com.company;

import java.util.Random;

public class TabuSearch {
    private Graph graph;
    Random rand = new Random();
    public static long millisActualTime;
    public static long executionTime;
    public TabuSearch(Graph graph){
        this.graph = graph;
        bestPath = new int[graph.matrix.length];
        currentPath = new int[graph.matrix.length];
        nextPath = new int[graph.matrix.length];
        tabuMatrix = new int[graph.matrix.length][graph.matrix.length];
        iterations = 10 * graph.matrix.length;
    }

    int[] bestPath;
    int[] currentPath;
    int[] nextPath;

    double bestCost = 999999.9;
    double currentCost;
    double nextCost;

    int[][] tabuMatrix;

    int saveI, saveJ;

    int iterations;

    void solve() {
        millisActualTime = System.currentTimeMillis();
        while (true) {
            currentPath = generateRandomPath();
            nextPath = currentPath;

            for (int iteration = 0; iteration < iterations; iteration++) {
                nextCost = 999999.9;
                saveI = 0;
                saveJ = 0;

                for (int i = 1; i < graph.matrix.length; i++) {
                    for (int j = i + 1; j < graph.matrix.length; j++) {
                        swap(i, j, currentPath);
                        currentCost = calculatePathCost(currentPath);

                        if ((currentCost < bestCost) && (tabuMatrix[i][j] == 0)) {
                            bestPath = currentPath.clone();
                            bestCost = currentCost;
                        }

                        if ((currentCost < nextCost) && (tabuMatrix[i][j] < iteration)) {
                            nextCost = currentCost;
                            nextPath = currentPath.clone();
                            saveI = i;
                            saveJ = j;
                        }
                    }
                }
                currentPath = nextPath.clone();
                if(saveI != 0 && saveJ != 0) {
                    tabuMatrix[saveI][saveJ] += graph.matrix.length; //dodanie do macierzy tabu
                }
                for (int x = 0; x < graph.matrix.length; x++) { //dekrementacja o 1
                    for (int y = 0; y < graph.matrix.length; y++) {
                        if (tabuMatrix[x][y] > 0) {
                            tabuMatrix[x][y] -= 1;
                        }
                    }
                }

                executionTime = System.currentTimeMillis() - millisActualTime;
                if (executionTime > 30000) { //minuta przerwy
                    System.out.println(bestCost);
                    for(int i = 0; i < bestPath.length; i++){
                        System.out.print(bestPath[i] + " ");
                    }
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
        for (int i = 1; i < randomPath.length; i++) {//funkcja losująca kolejność
            int randomIndexToSwap = rand.nextInt(randomPath.length-1)+1;//wszystkie oprocz 0
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
