package de.tum.cit.ase.maze.GameComponents;

import java.awt.*;

public class Cell {
    public int row;
    public int column;
    public CellType cellType;

    public Cell(int row, int column, CellType cellType) {
        this.row = row;
        this.column = column;
        this.cellType = cellType;
    }
    public Rectangle rectangle(){
        return new Rectangle(column*16,row*16,16,16);
    }
}
