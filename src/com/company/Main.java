package com.company;

public class Main {

    public static void main(String[] args) {
	Graph graph = new Graph();
    graph.copyFromFile("ftv47.atsp");
    TabuSearch tabuSearch = new TabuSearch(graph);
    //int[] path = {0,10,3,5,7,9,1,8,4,6,11,2};
    //double xd = tabuSearch.calculatePathCost(path);
    //System.out.println(xd);
    tabuSearch.solve();
    }
}
