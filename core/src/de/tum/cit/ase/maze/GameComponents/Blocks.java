package de.tum.cit.ase.maze.GameComponents;

import de.tum.cit.ase.maze.Utilities.BoundingBox;

public class Blocks {
    private int row;
    private int column;
    private BlocksType blocksType;

    public Blocks(int row, int column, BlocksType blocksType) {
        this.row = row;
        this.column = column;
        this.blocksType = blocksType;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public BlocksType getBlocksType() {
        return blocksType;
    }

    public void setBlocksType(BlocksType blocksType) {
        this.blocksType = blocksType;
    }
    public BoundingBox rectangle(){
        return new BoundingBox(column*16,row*16,16,16);
    }
}
