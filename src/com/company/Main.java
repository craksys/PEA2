package com.company;

import java.util.Scanner;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";

    public static void main(String[] args) {
        System.out.println("Podaj czas: ");
        Scanner in = new Scanner(System.in);
        int timeLimit = 120000;
        int neighbor = 1;
        Graph graph = new Graph();
        TabuSearch tabuSearch = new TabuSearch(graph, timeLimit, neighbor);
        int menu = -1;
        while (menu != 0) {
            printOptions();
            menu = in.nextInt();
            switch (menu) {
                case 1 -> {
                    graph = new Graph();
                    System.out.println("Podaj nazwę pliku: ");
                    String filename = in.next();
                    graph.copyFromFile(filename);
                    System.out.println(ANSI_RED + "Wczytano!" + ANSI_RESET);
                }
                case 2 -> {
                    System.out.println("Podaj limit czasowy w ms: ");
                    timeLimit = in.nextInt();
                }
                case 3 -> {
                    System.out.println("Wybierz sąsiedztwo: ");
                    System.out.println("1. Swap()");
                    System.out.println("2. Insert()");
                    System.out.println("3. Inverse()");
                    neighbor = in.nextInt();
                    if(neighbor < 1 || neighbor > 3){
                        neighbor = 1;
                    }
                }
                case 4 -> {
                    tabuSearch = new TabuSearch(graph,timeLimit,neighbor);
                    tabuSearch.solve();
                }
                default -> {
                    return;
                }
            }

        }
    }
    public static void printOptions(){
        System.out.println("Wybierz opcję programu: ");
        System.out.println("1. Wczytaj graf z pliku");
        System.out.println("2. Kryterium stopu");
        System.out.println("3. Wybór sąsiedzta");
        System.out.println("4. Uruchom TabuSearch");
        System.out.println("5. Wyjdź");
    }
}
