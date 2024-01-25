package de.tum.cit.ase.maze.GameComponents;

import de.tum.cit.ase.maze.Utilities.BoundingBox;

public class Block {
    private int row;
    private int column;
    private BlockType blockType;

    public Block(int row, int column, BlockType blockType) {
        this.row = row;
        this.column = column;
        this.blockType = blockType;
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

    public BlockType getBlocksType() {
        return blockType;
    }

    public void setBlocksType(BlockType blockType) {
        this.blockType = blockType;
    }
    public BoundingBox rectangle(){
        return new BoundingBox(column*16,row*16,16,16);
    }
}
