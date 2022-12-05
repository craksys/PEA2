package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Graph {
    public int size;
    double[][] matrix;
    Random rand = new Random();

    public void createGraph(int size) {
        this.size = size;
        matrix = new double[size][size];
    }

    public void generateGraph() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    matrix[i][j] = rand.nextInt(50) + 1;
                } else {
                    matrix[i][j] = -1;
                }
            }
        }
    }

    public void printAll() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix[i][j] < 10 && matrix[i][j] != -1) {
                    System.out.print("   ");
                } else if (matrix[i][j] < 100) {
                    System.out.print("  ");
                } else {
                    System.out.print(" ");
                }
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }


    public void copyFromFile(String filename) {
        try {
            File myObj = new File("src/com/company/" + filename);
            Scanner myReader = new Scanner(myObj);
            String dimension = "DIMENSION:";
            String edgeStartPattern = "EDGE_WEIGHT_SECTION";
            String helper = null;
            double number;
            int length, get;


            while (!dimension.equals(helper)) { //przejscie do linijki z rozmiarem problemu
                helper = myReader.next();
            }
            size = myReader.nextInt(); //odczytujemy liczbę miast
            matrix = new double[size][size];

            while (!edgeStartPattern.equals(helper)) {
                helper = myReader.next();
            }

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    get = myReader.nextInt();
                    //length = String.valueOf(get).length();
                    number = get;
                    //number = get / Math.pow(10,length-1);
                    matrix[i][j] = number;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}