package com.company;

import java.util.Random;

public class TabuSearch {
    private Graph graph;
    Random rand = new Random();

    public static long millisActualTime;
    public static long executionTime;
    public static long bestSolutionTime;

    public TabuSearch(Graph graph){
        this.graph = graph;
    }

    int[] bestPath;
    int bestCost = Integer.MAX_VALUE;


    public void solve() {
        int[] currentPath;
        int[] nextPath;
        int[][] tabuMatrix = new int[graph.matrix.length][graph.matrix.length];
        int saveI = 0;
        int saveJ = 0;
        int iterations = 15 * graph.matrix.length; //liczba iteracji to 10 krotnosc liczby miast
        int nextCost;
        int currentCost;
        int savePath[];

        millisActualTime = System.currentTimeMillis();
        while (true) {
            currentPath = generateRandomPath();
            nextPath = currentPath;
            currentCost = calculatePathCost(currentPath);
            for (int a = 0; a < iterations; a++) {
                currentPath = nextPath.clone();
                nextCost = currentCost;
                savePath = currentPath.clone();//zachowanie ścieżki
                for (int i = 1; i < graph.matrix.length; i++) {
                    for (int j = i + 1; j < graph.matrix.length; j++) {
                        swap(i, j, currentPath);
                        //makeReverse(i,j,currentPath);
                        //insert(i,j,currentPath);
                        currentCost = calculatePathCost(currentPath);

                        if ((currentCost < bestCost) /*&& (tabuMatrix[i][j] == 0)*/) {
                            bestPath = currentPath.clone();
                            bestCost = currentCost;
                            bestSolutionTime = System.currentTimeMillis() - millisActualTime;
                            if((tabuMatrix[i][j] == 0)) {
                                nextCost = currentCost;
                                nextPath = currentPath.clone();
                            }
                        }
                        if ((currentCost < nextCost) && (tabuMatrix[i][j] < a)) {
                            nextCost = currentCost;
                            nextPath = currentPath.clone();
                            tabuMatrix[i][j] += graph.matrix.length;
                        }
                        currentPath = savePath.clone(); // przywrocenie sciezki
                    }
                }

                for (int x = 0; x < graph.matrix.length; x++) { //dekrementacja o 1
                    for (int y = 0; y < graph.matrix.length; y++) {
                        if (tabuMatrix[x][y] > 0) {
                            tabuMatrix[x][y] -= 1;
                        }
                    }
                }

                executionTime = System.currentTimeMillis() - millisActualTime;
                if (executionTime > 120000) { //minuta przerwy
                    System.out.println(bestCost);
                    for(int i = 0; i < bestPath.length; i++){
                        System.out.print(bestPath[i] + " ");
                    }
                    System.out.println("0");
                    System.out.println("Najlepsze rozwiązanie znaleziono w: "+ bestSolutionTime +" ms");
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
