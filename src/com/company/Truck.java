package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Truck {
    public int i, j;
    public int weight;
    public int cost;
    public HashSet<Integer> parcels;

    public Truck(int i, int j) {
        this.i = i;
        this.j = j;
        this.parcels = new HashSet<Integer>();
        this.weight = 0;
        this.cost = 0;
    }

    public Truck(Truck truck) {
        this.cost = truck.getCost();
        this.parcels = new HashSet<Integer>();
        this.weight = 0;

        this.i = truck.i;
        this.j = truck.j;

        for (Integer n : truck.parcels) {
            this.parcels.add(n);
        }
    }

    public int getWeight() {
        this.weight = parcels.size();
        return weight;
    }

    public void moveTo(int i, int j) {
        this.i = i;
        this.j = j;
        this.cost += getWeight() + 1;
    }

    public int getCost() {
        return cost;
    }
    public void addParcel(int id) {
        parcels.add(id);
    }
    public void dropParcel(int id) {
        parcels.remove(id);
    }


}
