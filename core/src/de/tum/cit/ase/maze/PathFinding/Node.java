package de.tum.cit.ase.maze.Pathfinding;

public class Node {

    float g, h;
    Node parent;
    int Row, Column;
    float distance;

    Node(int row, int Column, Node parent) {
        this.Row = row;
        this.Column = Column;
        this.parent = parent;

        g = 0;
        h = 0;
        distance = Integer.MAX_VALUE;
    }

    public float getF() {
        return g + h;
    }

    public float getG() {
        return g;
    }

    public void setG(float g) {
        this.g = g;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getRow() {
        return Row;
    }

    public void setRow(int row) {
        this.Row = row;
    }

    public int getColumn() {
        return Column;
    }

    public void setColumn(int column) {
        this.Column = column;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public boolean equal(Node n){
        return n.Row == Row && n.Column == Column;
    }
};