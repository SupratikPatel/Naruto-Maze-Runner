package de.tum.cit.ase.maze.GameComponents;

public enum BlockType {
    WALL,
    ENTRY_POINT,
    EXIT,
    TRAP,
    ENEMY,
    KEY;

    public static BlockType blocksType(int x) {
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
