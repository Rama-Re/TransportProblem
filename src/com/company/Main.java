package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static public void printGrid(String fileName) {
        try {
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                System.out.println(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    static void print(String file, String algo, int heuristic){
        long startTime = System.nanoTime();
        Grid grid = new Grid(file);
        ArrayList<Grid> path;
        if (algo.equals("ucs")) {
            path = grid.ucs();
            if (path == null) System.out.println("ucs is failed!");
        }
        else if (algo.equals("aStar")){
            path = grid.aStar(heuristic);
            if (path == null) System.out.println("aStar is failed!");
        }
        else path = null;
        if (path != null) {
            for (int i=0;i< path.size();i++){
                System.out.print(path.get(i).truck.i + "," + path.get(i).truck.j);
                if(i!=path.size()-1)
                    System.out.print(" -> ");
                if (i % 10 == 0 && i!=0)
                    System.out.println();
            }
            System.out.println();
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.println("It takes time: " + totalTime * 1.0/1000000000);
        }
        System.out.println("-------------------------------------------------------------------------\n");
    }
    public static void main(String[] args) {
        //ex1
        printGrid("file.txt");
        print("file.txt","ucs",0);
        print("file.txt","aStar",1);
        print("file.txt","aStar",2);
        print("file.txt","aStar",3);
        //ex2
        printGrid("file2.txt");
        print("file2.txt","ucs",0);
        print("file2.txt","aStar",1);
        print("file2.txt","aStar",2);
        print("file2.txt","aStar",3);
        //ex3
        printGrid("file3.txt");
        print("file3.txt","ucs",0);
        print("file3.txt","aStar",1);
        print("file3.txt","aStar",2);
        print("file3.txt","aStar",3);
    }
}
