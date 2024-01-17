package de.tum.cit.ase.maze.GameComponents;

public enum BlocksType {
    WALL,
    ENTRY_POINT,
    EXIT,
    TRAP,
    ENEMY,
    KEY;

    public static BlocksType fromId(int x) {
        switch(x) {
            case 0:
                return WALL;
            case 1:
                return ENTRY_POINT;
            case 2:
                return EXIT;
            case 3:
                return TRAP;
            case 4:
                return ENEMY;
            case 5:
                return KEY;
        }
        return null;
    }
}
