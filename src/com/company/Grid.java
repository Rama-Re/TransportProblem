package com.company;

import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class Grid {
    public int n, m;
    public Cell[][] cells;
    public HashMap<Integer, Pair<Integer, Integer>> PMap = new HashMap();
    public HashMap<Integer, Pair<Integer, Integer>> DMap = new HashMap();

    public Truck truck;
    public int startI;
    public int startJ;

    public Grid(String fileName) {
        try {
            File file = new File(fileName);
            Scanner myReader = new Scanner(file);
            String data = myReader.nextLine();
            String[] st = data.split("\\s+");
            int lines = 1;
            while (myReader.hasNextLine()) {
                myReader.nextLine();
                lines++;
            }
            myReader.close();
            this.n = lines;
            this.m = st.length;
            cells = new Cell[n][m];
            File file1 = new File(fileName);
            Scanner myReader1 = new Scanner(file1);
            int j = 0;
            while (myReader1.hasNextLine()) {
                String data1 = myReader1.nextLine();
                String[] st1 = data1.split("\\s+");
                for (int i = 0; i < st1.length; i++) {
                    if (st1[i].equals(".")) {
                        cells[j][i] = new Path();
                    } else if (st1[i].equals("#")) {
                        cells[j][i] = new Building();
                    } else if (st1[i].charAt(0) == 'P') {
                        PMap.put(Character.getNumericValue(st1[i].charAt(1)), new Pair<Integer, Integer>(j, i));
                        cells[j][i] = new P(Character.getNumericValue(st1[i].charAt(1)));
                    } else if (st1[i].charAt(0) == 'D') {
                        DMap.put(Character.getNumericValue(st1[i].charAt(1)), new Pair<Integer, Integer>(j, i));
                        cells[j][i] = new D(Character.getNumericValue(st1[i].charAt(1)));
                    } else if (st1[i].equals("T")) {
                        cells[j][i] = new T();
                        truck = new Truck(j, i);
                        startI = j;
                        startJ = i;
                    }
                }
                j++;
            }
            myReader1.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public Grid(Grid grid) {
        this.n = grid.n;
        this.m = grid.m;
        this.startI = grid.startI;
        this.startJ = grid.startJ;
        this.truck = new Truck(grid.truck);
        this.cells = new Cell[n][m];
        this.PMap = new HashMap<>(grid.PMap);
        this.DMap = new HashMap<>(grid.DMap);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid.cells[i][j].type == CellType.path) {
                    this.cells[i][j] = new Path();
                }
                if (grid.cells[i][j].type == CellType.build) {
                    this.cells[i][j] = new Building();
                }
                if (grid.cells[i][j].type == CellType.T) {
                    this.cells[i][j] = new T();
                }
                if (grid.cells[i][j].type == CellType.P) {
                    this.cells[i][j] = new P(grid.cells[i][j]);
                }
                if (grid.cells[i][j].type == CellType.D) {
                    this.cells[i][j] = new D(grid.cells[i][j]);
                }
            }
        }
    }

    public boolean goalGrid() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (cells[i][j].type == CellType.T)
                    if (truck.i != i || truck.j != j)
                        return false;
                if (cells[i][j].type == CellType.P && !((P) cells[i][j]).Received)
                    return false;
                if (cells[i][j].type == CellType.D && !((D) cells[i][j]).Delivered)
                    return false;
            }
        }
        return true;
    }

    public boolean equals(Grid grid) {
        if (this.truck.i != grid.truck.i || this.truck.j != grid.truck.j)
            return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (cells[i][j].type == CellType.P && ((P) cells[i][j]).Received != ((P) grid.cells[i][j]).Received)
                    return false;
                if (cells[i][j].type == CellType.D && ((D) cells[i][j]).Delivered != ((D) grid.cells[i][j]).Delivered)
                    return false;
            }
        }
        return true;
    }

    public boolean moveUp() {
        if (testMovement(truck.i - 1, truck.j)) {
            truck.moveTo(truck.i - 1, truck.j);
            return true;
        }
        return false;
    }

    public boolean moveRight() {
        if (testMovement(truck.i, truck.j + 1)) {
            truck.moveTo(truck.i, truck.j + 1);
            return true;
        }
        return false;
    }

    public boolean moveLift() {
        if (testMovement(truck.i, truck.j - 1)) {
            truck.moveTo(truck.i, truck.j - 1);
            return true;
        }
        return false;
    }

    public boolean moveDown() {
        if (testMovement(truck.i + 1, truck.j)) {
            truck.moveTo(truck.i + 1, truck.j);
            return true;
        }
        return false;
    }

    public boolean testMovement(int i, int j) {
        if (i >= n || j >= m) return false;
        if (i < 0 || j < 0) return false;
        if (!cells[i][j].canMoveOn) return false;
        return true;
    }

    public boolean ReceiveParcel() {
        if (cells[truck.i][truck.j].type == CellType.P) {
            if (!((P) cells[truck.i][truck.j]).Received) {
                ((P) cells[truck.i][truck.j]).Received = true;
                truck.addParcel(((P) cells[truck.i][truck.j]).id);
                return true;
            }
        }
        return false;
    }

    public boolean DeliverParcel() {
        if (cells[truck.i][truck.j].type == CellType.D) {
            if (!((D) cells[truck.i][truck.j]).Delivered) {
                if (!truck.parcels.contains(((D) cells[truck.i][truck.j]).id))
                    return false;
                ((D) cells[truck.i][truck.j]).Delivered = true;
                truck.dropParcel(((D) cells[truck.i][truck.j]).id);
                return true;
            }
        }
        return false;
    }

    public void append(ArrayList<Grid> grids, ArrayList<Grid> grids2) {
        for (Grid game : grids2) {
            grids.add(new Grid(game));
        }
    }

    public ArrayList<Grid> nextStates() {
        ArrayList<Grid> grids = new ArrayList<>();
        Grid grid = new Grid(this);
        if (grid.moveUp()) {
            grids.add(new Grid(grid));
            if (grid.ReceiveParcel()) grids.add(new Grid(grid));
            if (grid.DeliverParcel()) grids.add(new Grid(grid));
        }
        grid = new Grid(this);
        if (grid.moveDown()) {
            grids.add(new Grid(grid));
            if (grid.ReceiveParcel()) grids.add(new Grid(grid));
            if (grid.DeliverParcel()) grids.add(new Grid(grid));
        }
        grid = new Grid(this);
        if (grid.moveRight()) {
            grids.add(new Grid(grid));
            if (grid.ReceiveParcel()) grids.add(new Grid(grid));
            if (grid.DeliverParcel()) grids.add(new Grid(grid));
        }
        grid = new Grid(this);
        if (grid.moveLift()) {
            grids.add(new Grid(grid));
            if (grid.ReceiveParcel()) grids.add(new Grid(grid));
            if (grid.DeliverParcel()) grids.add(new Grid(grid));
        }
        return grids;
    }

    public ArrayList<Grid> ucs() {
        ArrayList<Pair<Integer, ArrayList<Grid>>> queue = new ArrayList<Pair<Integer, ArrayList<Grid>>>();
        ArrayList<Grid> vis = new ArrayList<Grid>();
        ArrayList<Grid> temp = new ArrayList<Grid>();
        temp.add(new Grid(this));
        Pair pair = new Pair(truck.getCost(), temp);
        queue.add(pair);
        while (!queue.isEmpty()) {
            ArrayList<Grid> path = new ArrayList<>();
            Integer cost = queue.get(0).getKey();
            path = (queue.get(0).getValue());
            queue.remove(0);
            Grid vertex = new Grid(path.get(path.size() - 1));
            if (vertex.goalGrid()) {
                System.out.println("It It visited " + vis.size() + " node to reach the goal using UCS, where number of steps is: " + (path.size() - 1));
                return path;
            }
            boolean error = false;
            for (Grid grid : vis) {
                if (grid.equals(vertex)) {
                    error = true;
                    break;
                }
            }
            if (!error) {
                vis.add(vertex);
                ArrayList<Grid> states = vertex.nextStates();
                for (Grid state : states) {
                    Grid neighbour = new Grid(state);
                    boolean error2 = false;
                    for (Grid game : vis) {
                        if (game.equals(neighbour)) {
                            error2 = true;
                            break;
                        }
                    }
                    if (!error2) {
                        Integer total_cost = neighbour.truck.getCost();
                        ArrayList<Grid> newPath = new ArrayList<>();
                        append(newPath, path);
                        newPath.add(new Grid(neighbour));
                        Pair pair3 = new Pair(total_cost, newPath);
                        queue.add(pair3);
                        Comparator<Pair<Integer, ArrayList<Grid>>> c = Comparator.comparing(Pair::getKey);
                        Collections.sort(queue, c);
                    }
                }
            }
        }

        return null;
    }

    public int heuristic1() {
        return (Math.abs(this.startI - this.truck.i) + Math.abs(this.startJ - this.truck.j));
    }

    public ParcelState stateOfParcel(int id) {
        Pair<Integer, Integer> p = PMap.get(id);
        if (((P) cells[p.getKey()][p.getValue()]).Received) {
            Pair<Integer, Integer> d = DMap.get(id);
            if (((D) cells[d.getKey()][d.getValue()]).Delivered) {
                return ParcelState.Delivered;
            }
            return ParcelState.Received;
        }
        return ParcelState.notReceived;
    }

    public int heuristic22(int id) {
        ParcelState state = stateOfParcel(id);
        Pair<Integer, Integer> p = PMap.get(id);
        Pair<Integer, Integer> d = DMap.get(id);
        int d1 = Math.abs(p.getKey() - this.truck.i) + Math.abs(p.getValue() - this.truck.j);
        int d2 = Math.abs(p.getKey() - d.getKey()) + Math.abs(p.getValue() - d.getValue());
        int d3 = Math.abs(this.startI - d.getKey()) + Math.abs(this.startJ - d.getValue());
        int d4 = Math.abs(d.getKey() - this.truck.i) + Math.abs(d.getValue() - this.truck.j);
        int d5 = Math.abs(this.startI - this.truck.i) + Math.abs(this.startJ - this.truck.j);
        if (state == ParcelState.notReceived)
            return this.truck.weight * d1 + (this.truck.weight + 1) * d2 + this.truck.weight * d3;
        if (state == ParcelState.Received)
            return (this.truck.weight + 1) * d4 + this.truck.weight * d3;
        if (state == ParcelState.Delivered)
            return this.truck.weight * d5;
        return 0;
    }

    public int heuristic2(int id) {
        ParcelState state = stateOfParcel(id);
        Pair<Integer, Integer> p = PMap.get(id);
        Pair<Integer, Integer> d = DMap.get(id);
        int d1 = Math.abs(p.getKey() - this.truck.i) + Math.abs(p.getValue() - this.truck.j);
        int d2 = Math.abs(p.getKey() - d.getKey()) + Math.abs(p.getValue() - d.getValue());
        int d3 = Math.abs(this.startI - d.getKey()) + Math.abs(this.startJ - d.getValue());
        int d4 = Math.abs(d.getKey() - this.truck.i) + Math.abs(d.getValue() - this.truck.j);
        int d5 = Math.abs(this.startI - this.truck.i) + Math.abs(this.startJ - this.truck.j);
        if (state == ParcelState.notReceived)
            return d1 + 2 * d2 + d3;
        if (state == ParcelState.Received)
            return 2 * d4 + d3;
        if (state == ParcelState.Delivered)
            return d5;
        return 0;
    }

    public int heuristic3() {
        int max = 0;
        for (Integer id : PMap.keySet()) {
            int h = heuristic2(id);
            if (h > max) max = h;
        }
        return max;
    }


    public ArrayList<Grid> aStar(int heuristic) {
        ArrayList<Pair<Integer, ArrayList<Grid>>> queue = new ArrayList<Pair<Integer, ArrayList<Grid>>>();
        ArrayList<Grid> vis = new ArrayList<Grid>();
        ArrayList<Grid> temp = new ArrayList<Grid>();
        temp.add(new Grid(this));
        Pair pair = new Pair(truck.getCost(), temp);
        queue.add(pair);
        while (!queue.isEmpty()) {
            ArrayList<Grid> path = new ArrayList<>();
            Integer cost = queue.get(0).getKey();
            path = (queue.get(0).getValue());
            queue.remove(0);
            Grid vertex = new Grid(path.get(path.size() - 1));
            if (vertex.goalGrid()) {
                System.out.println("It visited " + vis.size() + " node to reach the goal using aStar with heuristic" + heuristic + ", where number of steps is: " + (path.size() - 1));
                return path;
            }
            boolean error = false;
            for (Grid grid : vis) {
                if (grid.equals(vertex)) {
                    error = true;
                    break;
                }
            }
            if (!error) {
                vis.add(vertex);
                ArrayList<Grid> states = vertex.nextStates();
                for (Grid state : states) {
                    Grid neighbour = new Grid(state);
                    boolean error2 = false;
                    for (Grid game : vis) {
                        if (game.equals(neighbour)) {
                            error2 = true;
                            break;
                        }
                    }
                    if (!error2) {
                        int h = 0;
                        if (heuristic == 1)
                            h = neighbour.heuristic1();
                        if (heuristic == 2)
                            h = neighbour.heuristic2(0);
                        if (heuristic == 3)
                            h = neighbour.heuristic3();

                        Integer total_cost = neighbour.truck.getCost() + h;
                        ArrayList<Grid> newPath = new ArrayList<>();
                        append(newPath, path);
                        newPath.add(new Grid(neighbour));
                        Pair pair3 = new Pair(total_cost, newPath);
                        queue.add(pair3);
                        Comparator<Pair<Integer, ArrayList<Grid>>> c = Comparator.comparing(Pair::getKey);
                        Collections.sort(queue, c);
                    }
                }
            }
        }

        return null;
    }
}
