package com.company;

public class D extends Cell {
    public int id;
    public boolean Delivered = false;
    public D(int id) {
        this.id = id;
        this.canMoveOn = true;
        this.type = CellType.D;
    }
    public D(Cell d){
        this.id = ((D)d).id;
        this.Delivered = ((D)d).Delivered;
        this.canMoveOn = true;
        this.type = CellType.D;
    }
}
