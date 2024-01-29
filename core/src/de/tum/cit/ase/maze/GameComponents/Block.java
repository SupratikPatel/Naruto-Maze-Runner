package de.tum.cit.ase.maze.GameComponents;

import de.tum.cit.ase.maze.Utilities.BoundingBox;

/**
 * Represents a single block within a maze in a maze runner game.
 * A block is a fundamental unit that can have different types, such as wall, path, etc.
 */
public class Block {
    private int row; // The row position of the block in the maze grid
    private int column; // The column position of the block in the maze grid
    private BlockType blockType; // The type of the block (e.g., wall, path)

    /**
     * Constructs a new Block with specified row, column, and type.
     *
     * @param row        the row position of the block in the maze
     * @param column     the column position of the block in the maze
     * @param blockType  the type of the block (e.g., wall, path)
     */
    public Block(int row, int column, BlockType blockType) {
        this.row = row;
        this.column = column;
        this.blockType = blockType;
    }
    //Getter and Setters
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
