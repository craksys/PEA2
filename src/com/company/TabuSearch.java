package com.company;

import java.util.Random;

public class TabuSearch {
    private Graph graph;
    Random rand = new Random();

    public static long millisActualTime;
    public static long executionTime;

    public TabuSearch(Graph graph){
        this.graph = graph;
    }

    int[] bestPath;
    int bestCost = Integer.MAX_VALUE;


    public void solve() {
        int[] currentPath = generateRandomPath();
        int[] nextPath = currentPath;
        int[][] tabuMatrix = new int[graph.matrix.length][graph.matrix.length];
        int saveI, saveJ;
        int iterations = 10 * graph.matrix.length; //liczba iteracji to 10 krotnosc liczby miast
        int nextCost;
        int currentCost;


        millisActualTime = System.currentTimeMillis();
        while (true) {
            for (int iteration = 0; iteration < iterations; iteration++) {
                nextCost = Integer.MAX_VALUE;
                currentPath = nextPath.clone();
                for (int i = 1; i < graph.matrix.length; i++) {
                    for (int j = i + 1; j < graph.matrix.length; j++) {
                        //swap(i, j, currentPath);
                        //makeReverse(i,j,currentPath);
                        insert(i,j,currentPath);
                        currentCost = calculatePathCost(currentPath);

                        if ((currentCost < bestCost) && (tabuMatrix[i][j] == 0)) {
                            bestPath = currentPath.clone();
                            bestCost = currentCost;
                            nextCost = currentCost;
                            nextPath = currentPath.clone();
                        }
                        if ((currentCost < nextCost) && (tabuMatrix[i][j] < iteration)) {
                            nextCost = currentCost;
                            nextPath = currentPath.clone();
                            tabuMatrix[i][j] += graph.matrix.length;
                        }
                    }
                }
                //if(saveI != 0 && saveJ != 0) {
                //    tabuMatrix[saveI][saveJ] += graph.matrix.length; //dodanie do macierzy tabu
               // }
                for (int x = 0; x < graph.matrix.length; x++) { //dekrementacja o 1
                    for (int y = 0; y < graph.matrix.length; y++) {
                        if (tabuMatrix[x][y] > 0) {
                            tabuMatrix[x][y] -= 1;
                        }
                    }
                }

                executionTime = System.currentTimeMillis() - millisActualTime;
                if (executionTime > 20000) { //minuta przerwy
                    System.out.println(bestCost);
                    for(int i = 0; i < bestPath.length; i++){
                        System.out.print(bestPath[i] + " ");
                    }
                    System.out.print("0");
                    return;
                }
            }
        }
    }


    private int[] generateRandomPath() {
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

    private int calculatePathCost(int[] path) {
        int cost = 0;
        for(int i = 0; i < path.length-1; i++){
            cost += graph.matrix[path[i]][path[i+1]];
        }
        cost += graph.matrix[(path[(path.length-1)])][path[0]];
        return cost;
    }

    private void swap(int i, int j, int[] path){
        int temp = path[i];
        path[i] = path[j];
        path[j] = temp;
    }

    private void makeReverse(int i, int j, int[] path) {
        while (i < j) {
            int temp = path[i];
            path[i++] = path[j];
            path[j--] = temp;
        }
    }

    private void insert(int i, int j, int[] path){ //przesniesienie j elementu na pozycję i'tą
        int temp = path[i];
        path[i] = path[j];

        while(j < path.length-1){
            path[j] = path[j+1];
            j++;
        }
        path[(path.length-1)] = temp;
    }

}
