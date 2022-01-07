package com.company;

public class P extends Cell{
    public int id;
    public boolean Received = false;
    public P(int id) {
        this.id = id;
        this.canMoveOn = true;
        this.type = CellType.P;
    }
    public P(Cell p){
        this.id = ((P)p).id;
        this.Received = ((P)p).Received;
        this.canMoveOn = true;
        this.type = CellType.P;
    }
}
