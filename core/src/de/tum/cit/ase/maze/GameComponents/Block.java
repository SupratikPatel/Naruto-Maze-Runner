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

    /**
     * Gets the row position of the block in the maze grid.
     *
     * @return the row position of the block
     */
    public int getRow() {
        return row;
    }

    /**
     * Sets the row position of the block in the maze grid.
     *
     * @param row the new row position of the block
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Gets the column position of the block in the maze grid.
     *
     * @return the column position of the block
     */
    public int getColumn() {
        return column;
    }

    /**
     * Sets the column position of the block in the maze grid.
     *
     * @param column the new column position of the block
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Gets the type of the block (e.g., wall, path).
     *
     * @return the type of the block
     */
    public BlockType getBlocksType() {
        return blockType;
    }

    /**
     * Sets the type of the block (e.g., wall, path).
     *
     * @param blockType the new type of the block
     */
    public void setBlocksType(BlockType blockType) {
        this.blockType = blockType;
    }

    /**
     * Creates a bounding box representing the rectangle of the block.
     *
     * @return a bounding box representing the rectangle of the block
     */
    public BoundingBox rectangle(){
        return new BoundingBox(column*16,row*16,16,16);
    }
}