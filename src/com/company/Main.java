package com.company;

public class Main {

    public static void main(String[] args) {
	Graph graph = new Graph();
    graph.copyFromFile("ftv47.atsp");
    TabuSearch tabuSearch = new TabuSearch(graph);
    int[] path = tabuSearch.generateRandomPath();
    tabuSearch.solve();
    }
}
