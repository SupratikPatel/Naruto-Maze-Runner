package de.tum.cit.ase.maze.GameComponents;

/**
 * Enum representing the different types of blocks in a maze runner game.
 * Each enum constant represents a distinct type of block within the game.
 */
public enum BlockType {
    /**
     Represents a wall block that players cannot pass through.
     */
    WALL,
    /**
     Represents the starting point of the maze.
     */
    ENTRY_POINT,
   /**
     Represents the exit point of the maze.
    */
    EXIT,
    /**
     Represents a trap block that may hinder the player.
    */
     TRAP,


    /**
    * Represents a block containing an enemy.
    */
     ENEMY,
    /**
     * Represents a block containing a key to unlock parts of the maze.
     */
    KEY;

    /**
     * Returns the corresponding BlockType based on an integer value.
     * This method is typically used for mapping numeric data (e.g., from a file) to block types.
     *
     * @param x The integer value representing a block type.
     * @return The corresponding BlockType, or null if the integer does not match any block type.
     */
    public static BlockType blockCaseType(int x) {
        return switch (x) {
            case 0 -> WALL;
            case 1 -> ENTRY_POINT;
            case 2 -> EXIT;
            case 3 -> TRAP;
            case 4 -> ENEMY;
            case 5 -> KEY;
            default -> null;
        };
    }
}